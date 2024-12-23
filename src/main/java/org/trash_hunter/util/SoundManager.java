package org.trash_hunter.util;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundManager {

    private static Logger LOGGER =Logger.getLogger(SoundManager.class.getName());
    private static InputStream getResource(String resource) {
        InputStream in;
        try {
            URL url =SoundManager.class.getClassLoader().getResource(resource);
            LOGGER.info(url.toString());
            return url.openStream();
        } catch(IOException ex) {
            return ClassLoader.getSystemResourceAsStream(resource);
        }
    }

    public static void playSound(double[] echantillons, double frequenceEchantillonnage,float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(convertInputStreamToAudio(echantillons, frequenceEchantillonnage));
            // Réglage du volume du clip
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume); // mis au volume voulu

            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (LineUnavailableException | InterruptedException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static AudioFormat readWavFile(String fichier) {
        AudioInputStream stream = null;
        try {
            InputStream bufferedIn = new BufferedInputStream(getResource(fichier));
            stream = AudioSystem.getAudioInputStream(bufferedIn);
            return stream.getFormat();
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if(stream != null){
                    stream.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

    }
    public static double[] readWAVFileSample(String fichier) {
        byte[] byteArr = new byte[0];
        AudioInputStream stream = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream bufferedIn = new BufferedInputStream(getResource(fichier));
            stream = AudioSystem.getAudioInputStream(bufferedIn);

            if (stream.getFormat().getChannels()!=1) {
                throw new UnsupportedAudioFileException("Le fichier audio ne doit comporter qu'un seul canal.");
            }
            if (stream.getFormat().getEncoding()!=AudioFormat.Encoding.PCM_SIGNED || stream.getFormat().getSampleSizeInBits()!=16 || stream.getFormat().getFrameSize()!=2 || stream.getFormat().isBigEndian()) {
                throw new UnsupportedAudioFileException("Le fichier audio doit être encodé en PCM_SIGNED, 16 bits, mono, 2 bytes/frame, little-endian.");
            }

            int numOfBytes;
            byte[] buffer = new byte[1024];

            while ((numOfBytes = stream.read(buffer)) > 0) {
                out.write(buffer, 0, numOfBytes);
            }
            out.flush();
            byteArr = out.toByteArray();
        } catch (UnsupportedAudioFileException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (stream != null)
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        short[] shortArr = new short[byteArr.length/2]; // new a supprimer !!!
        ByteBuffer.wrap(byteArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shortArr);

        double[] echantillons = new double[shortArr.length];
        for (int i = 0; i < echantillons.length; i++) {
            echantillons[i] = ((double)shortArr[i])/0x8000;
        }

        return echantillons;
    }
    public static AudioInputStream convertInputStreamToAudio(double[] echantillons, double frequenceEchantillonnage) {

        short[] shortArr = new short[echantillons.length];
        for (int i = 0; i < echantillons.length; i++) {
            shortArr[i] = (short)(echantillons[i] * 0x8000);
        }

        byte[] byteArr = new byte[echantillons.length * 2];
        ByteBuffer.wrap(byteArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shortArr);

        return new AudioInputStream(new ByteArrayInputStream(byteArr),
                new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float)frequenceEchantillonnage, 16, 1, 2, (float)frequenceEchantillonnage, false),
                byteArr.length);
    }
}

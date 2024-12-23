package util;

import org.junit.Test;
import org.trash_hunter.util.SoundManager;

import javax.sound.sampled.AudioFormat;

public class SoundManagerTest {

    @Test
    public void shouldPlayATrashSound(){
        AudioFormat format = SoundManager.readWavFile("src/main/resources/sounds/trash_sound.wav");
        System.out.println(format);

        double[] echantillons = SoundManager.readWAVFileSample("src/main/resources/sounds/trash_sound.wav");
        SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
    }
    @Test
    public void shouldPlayAmbientMusic(){
        AudioFormat format = SoundManager.readWavFile("src/main/resources/sounds/Ambient_music.wav");
        System.out.println(format);

        double[] echantillons = SoundManager.readWAVFileSample("src/main/resources/sounds/Ambient_music.wav");
        SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
    }
}

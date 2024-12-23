package util;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.util.SoundManager;

import javax.sound.sampled.AudioFormat;
import java.util.Objects;

public class SoundManagerTest {

    @Test
    public void shouldPlayATrashSound(){
        AudioFormat format = SoundManager.readWavFile(ClassLoader.getSystemResourceAsStream("sounds/trash_sound.wav"));
        System.out.println(format);
        double[] echantillons = SoundManager.readWAVFileSample(ClassLoader.getSystemResourceAsStream("sounds/trash_sound.wav"));
        //SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
        Assert.assertNotNull(format);
    }
    @Test
    public void shouldPlayAmbientMusic(){
        AudioFormat format = SoundManager.readWavFile(ClassLoader.getSystemResourceAsStream("sounds/Ambient_music.wav"));
        System.out.println(format);
        double[] echantillons = SoundManager.readWAVFileSample(ClassLoader.getSystemResourceAsStream("sounds/Ambient_music.wav"));
        //SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
        Assert.assertNotNull(format);
    }
}

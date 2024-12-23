package util;

import org.junit.Assert;
import org.junit.Test;
import org.trash_hunter.util.SoundManager;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundManagerTest {

    @Test
    public void shouldPlayATrashSound(){
        AudioFormat format = SoundManager.readWavFile("sounds/trash_sound.wav");
        System.out.println(format);
        double[] echantillons = SoundManager.readWAVFileSample("sounds/trash_sound.wav");
        //SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
        Assert.assertNotNull(format);
    }
    @Test
    public void shouldPlayAmbientMusic(){
        AudioFormat format = SoundManager.readWavFile("sounds/Ambient_music.wav");
        System.out.println(format);
        double[] echantillons = SoundManager.readWAVFileSample("sounds/Ambient_music.wav");
        //SoundManager.playSound(echantillons, format.getSampleRate(),-15.0f);
        Assert.assertNotNull(format);
    }
}

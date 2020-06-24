package Engine.Audio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer
{
    Long currentFrame;
    Clip clip;
    String status = "";

    AudioInputStream audioInputStream;
    String filePath;
    public AudioPlayer(String filePath,boolean looped)
        throws UnsupportedAudioFileException,
        IOException, LineUnavailableException
    {
    	this.filePath = filePath;
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        if(looped) {
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void Play()
    {
        clip.start();

        status = "play";
    }
    public void Pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("Audio is already paused");
            return;
        }
        this.currentFrame =
        this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
    public void ResumeAudio() throws UnsupportedAudioFileException,
                                IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+
            "being played");
            return;
        }
        clip.close();
        ResetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.Play();
    }
    public void Restart() throws IOException, LineUnavailableException,
                                            UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        ResetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.Play();
    }
    public void Stop() throws UnsupportedAudioFileException,
    IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }
    public void Jump(long c) throws UnsupportedAudioFileException, IOException,
                                                        LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            ResetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.Play();
        }
    }
    public void ResetAudioStream() throws UnsupportedAudioFileException, IOException,
                                            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(
        new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
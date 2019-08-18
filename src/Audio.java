import javafx.scene.media.AudioClip;

import javax.sound.sampled.FloatControl;

/*
 * Classe pour la gestion des audios
 */

public class Audio {
    // Liste des sons utilisables dans le jeu :
    public static AudioClip sonSaut = new AudioClip(Audio.class.getResource("audio/saut.wav").toString());
    public static AudioClip sonNiveau = new AudioClip(Audio.class.getResource("audio/OST1.mp3").toString());
    public static AudioClip sonBlessure = new AudioClip(Audio.class.getResource("audio/blessure.wav").toString());
    public static AudioClip sonLaser = new AudioClip(Audio.class.getResource("audio/laser.mp3").toString());
    public static AudioClip sonMange = new AudioClip(Audio.class.getResource("audio/mange.wav").toString());
    public static AudioClip sonEnnemiMeurt = new AudioClip(Audio.class.getResource("audio/mange.wav").toString());
    public static AudioClip sonOver = new AudioClip(Audio.class.getResource("audio/gameover.wav").toString());
    public static AudioClip sonTramp = new AudioClip(Audio.class.getResource("Audio/sautTramp.wav").toString());

    public static void initiateSounds() {
        sonTramp.setVolume(0.1);
        sonSaut.setVolume(0.02);
        sonBlessure.setVolume(0.1);
        sonLaser.setVolume(0.02);
        sonEnnemiMeurt.setVolume(0.02);
        sonOver.setVolume(0.02);
        sonMange.setVolume(0.02);
        sonNiveau.setVolume(0.5);
        sonNiveau.setCycleCount(10);
    }

    public static AudioClip[] listeSon = {sonSaut, sonBlessure, sonLaser, sonMange, sonEnnemiMeurt, sonOver};
}




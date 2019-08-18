import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
 * Classe qui gère le chronomêtre
 */

public class Chrono {

    private int time;
    private AnimationTimerCustom timerTime;

    public Chrono (Group root){
        Text chrono = new Text(590, 35, "");
        chrono.setFill(Color.WHITE);
        Font.loadFont(Niveau.class.getResource("./font/PressStart2P.ttf").toExternalForm(), 20);
        chrono.setFont(new Font("Press Start 2P", 20));

        root.getChildren().add(chrono);
        this.time = SaveSystem.getChrono() - 1;
        this.timerTime = new AnimationTimerCustom(1000) {
            @Override
            public void handle() {
                time++;
                int sec = time % 60;
                int min = (time - sec) / 60;
                String time;
                if (min < 10) {
                    time = "0" + min;
                } else {
                    time = Integer.toString(min);
                }
                time += ":";
                if (sec < 10) {
                    time += "0" + sec;
                } else {
                    time += Integer.toString(sec);
                }
                chrono.setText(time);
                SaveSystem.saveScore(SaveSystem.getScore() + 1);
                SaveSystem.saveChrono(getTime());
            }
        };
        timerTime.start();
    }

    public AnimationTimerCustom getTimer() {
        return timerTime;
    }

    public int getTime() {
        return time;
    }
}

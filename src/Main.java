import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * La classe qui contient le main et va lancer le programme.
 */

public class Main extends Application {

    public static ArrayList<Niveau> levels;
    public static Stage primaryStage;
    public static AvatarMdl avatar;

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        startGame();
    }

    public static void main (String [] args){
        Application.launch(Main.class, args);
    }

    public static void startGame() {
        SaveSystem.initiateSave(false);
        //Audio.initiateSounds();
        Main.avatar = new AvatarMdl();
        Main.levels = new ArrayList<>();
        Main.levels.add(new Niveau(1));
        Main.levels.add(new Niveau(2));
        Main.levels.add(new Niveau(3));
        Main.levels.add(new Niveau(4));
        Main.levels.add(new Niveau(5));
        Menu menu = new Menu();
    }
}

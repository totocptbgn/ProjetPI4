import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

/*
 * Menu en début de jeu
 */

public class Menu {

    private Stage stage;
    private Scene scene;
    private Group root;

    private boolean putDot;
    private int colorCount;
    private int easterEgg;

    public Menu() {
        // Appel du constructeur de Scene
        Group root = new Group();
        this.root = root;
        this.scene = new Scene(root);
        this.putDot = true;
        this.stage = Main.primaryStage;
        stage.setWidth(1280);
        stage.setHeight(720);

        // Affichage et Création du background animé
        AnimatedBackgroud ab = new AnimatedBackgroud("img/fondBleu.png");
        ab.start();
        root.getChildren().add(ab.getBackground());

        // Creation du Titre
        Font.loadFont(Niveau.class.getResource("./font/PressStart2P.ttf").toExternalForm(), 20);
        Text title = new Text("RetroJump");
        title.fontProperty().setValue(Font.font("Press Start 2P", 110));
        title.setFill(Color.WHITE);
        root.getChildren().add(title);
        title.setTranslateX(150);
        title.setTranslateY(300);

        AnimationTimerCustom timerTitle = new AnimationTimerCustom(1000) {
            boolean on = false;
            @Override
            public void handle() {
                if (on){
                    title.setText("RetroJump_");
                    on = false;
                } else {
                    title.setText("RetroJump");
                    on = true;
                }
            }
        };
        timerTitle.start();

        // Création du bouton Play
        Text play = new Text(">  Play");
        play.fontProperty().setValue(Font.font("Press Start 2P", 60));
        play.setFill(Color.WHITE);
        root.getChildren().add(play);
        play.setTranslateX(280);
        play.setTranslateY(480);
        play.setOnMouseClicked(e -> Main.levels.get(SaveSystem.getLevelFromSave() - 1).launch());
        play.setOnMouseEntered(event -> {
            play.setText(" > Play");
            this.scene.setCursor(Cursor.HAND);
        });

        play.setOnMouseExited(event -> {
            play.setText(">  Play");
            this.scene.setCursor(Cursor.DEFAULT);
        });

        // Création du bouton Delete Save
        Text rmSave = new Text(">  Delete save");
        rmSave.fontProperty().setValue(Font.font("Press Start 2P", 30));
        rmSave.setFill(Color.WHITE);
        root.getChildren().add(rmSave);
        rmSave.setTranslateX(374);
        rmSave.setTranslateY(540);

        rmSave.setOnMouseEntered(event -> {
            rmSave.setText(" > Delete save");
            this.scene.setCursor(Cursor.HAND);
        });

        rmSave.setOnMouseExited(event -> {
            rmSave.setText(">  Delete save");
            this.scene.setCursor(Cursor.DEFAULT);
        });

        rmSave.setOnMouseClicked(event -> {

            SaveSystem.initiateSave(true);
            rmSave.setText("   Done!");
            rmSave.setFill(Color.RED);
            rmSave.setOnMouseExited(null);
            rmSave.setOnMouseEntered(null);
            scene.setCursor(Cursor.DEFAULT);

            AnimationTimerCustom timerSave = new AnimationTimerCustom(1000) {
                int t = 0;

                @Override
                public void handle() {
                    t++;
                    if (t == 3){
                        rmSave.setFill(Color.WHITE);
                        rmSave.setText(">  Delete save");

                        rmSave.setOnMouseEntered(event -> {
                            rmSave.setText(" > Delete save");
                            scene.setCursor(Cursor.HAND);
                        });

                        rmSave.setOnMouseExited(event -> {
                            rmSave.setText(">  Delete save");
                            scene.setCursor(Cursor.DEFAULT);
                        });
                    }
                }
            };
            timerSave.start();
        });

        // Création du bouton Quit
        Text quit = new Text(">  Quit");
        quit.fontProperty().setValue(Font.font("Press Start 2P", 30));
        quit.setFill(Color.WHITE);
        root.getChildren().add(quit);
        quit.setTranslateX(374);
        quit.setTranslateY(600);
        quit.setOnMouseClicked(e -> stage.close());

        quit.setOnMouseEntered(event -> {
            quit.setText(" > Quit");
            this.scene.setCursor(Cursor.HAND);
        });

        quit.setOnMouseExited(event -> {
            quit.setText(">  Quit");
            this.scene.setCursor(Cursor.DEFAULT);
        });

        // Création de la trainée easter egg
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE){
                easterEgg++;
            }

            if (easterEgg == 4){
                System.out.println("Easter egg activated ! :P");
            }

            if (event.getCode() == KeyCode.ESCAPE){
                stage.hide();
            }
        });

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.PURPLE);
        colors.add(Color.RED);
        this.colorCount = 0;

        root.setOnMouseMoved(event -> {
            if (putDot && easterEgg > 3) {
                new Trail(event.getX(), event.getY(), colors.get(colorCount));
                this.colorCount++;
                if (colorCount == colors.size()){
                    colorCount = 0;
                }
                putDot = false;
            }
        });

        AnimationTimerCustom timerDot = new AnimationTimerCustom(20) {
            @Override
            public void handle() {
                if (!putDot){
                    putDot = true;
                }
            }
        };
        timerDot.start();

        // Affichage de la fenêtre
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void menuFinJeu(){
        Stage stage = Main.primaryStage;
        Group overRoot = new Group();
        Scene over = new Scene(overRoot);

        AnimatedBackgroud bg = new AnimatedBackgroud("img/fondVert.png");
        bg.start();
        Group background = bg.getBackground();

        Text title = new Text("You won !");
        title.fontProperty().setValue(Font.font("Press Start 2P", 100));
        title.setFill(Color.WHITE);
        title.setTranslateX(200);
        title.setTranslateY(300);

        Text score = new Text("Score : " + SaveSystem.getScore());
        score.fontProperty().setValue(Font.font("Press Start 2P", 30));
        score.setFill(Color.WHITE);
        score.setTranslateX(700);
        score.setTranslateY(380);

        Text restart = new Text(">  Restart");
        restart.fontProperty().setValue(Font.font("Press Start 2P", 50));
        restart.setFill(Color.WHITE);

        restart.setTranslateX(200);
        restart.setTranslateY(500);

        restart.setOnMouseEntered(event -> {
            restart.setText(" > Restart");
            over.setCursor(Cursor.HAND);
        });

        restart.setOnMouseExited(event -> {
            restart.setText(">  Restart");
            over.setCursor(Cursor.DEFAULT);
        });

        restart.setOnMouseClicked(event -> {
            SaveSystem.initiateSave(true);
            Main.startGame();
        });

        Text quit = new Text(">  Quit");
        quit.fontProperty().setValue(Font.font("Press Start 2P", 50));
        quit.setFill(Color.WHITE);
        quit.setTranslateX(200);
        quit.setTranslateY(600);
        quit.setOnMouseClicked(e -> stage.close());

        quit.setOnMouseEntered(event -> {
            quit.setText(" > Quit");
            over.setCursor(Cursor.HAND);
        });

        quit.setOnMouseExited(event -> {
            quit.setText(">  Quit");
            over.setCursor(Cursor.DEFAULT);
        });

        overRoot.getChildren().addAll(background, title, score,  restart, quit);
        stage.setScene(over);
    }

    public static void menuGameOver() {
        Stage stage = Main.primaryStage;
        Group overRoot = new Group();
        Scene over = new Scene(overRoot);

        AnimatedBackgroud bg = new AnimatedBackgroud("img/fondRouge.png");
        bg.start();
        Group background = bg.getBackground();

        Text title = new Text("Game Over.");
        title.fontProperty().setValue(Font.font("Press Start 2P", 100));
        title.setFill(Color.WHITE);
        title.setTranslateX(200);
        title.setTranslateY(300);

        Text restart = new Text(">  Restart");
        restart.fontProperty().setValue(Font.font("Press Start 2P", 50));
        restart.setFill(Color.WHITE);

        restart.setTranslateX(200);
        restart.setTranslateY(500);

        restart.setOnMouseEntered(event -> {
            restart.setText(" > Restart");
            over.setCursor(Cursor.HAND);
        });

        restart.setOnMouseExited(event -> {
            restart.setText(">  Restart");
            over.setCursor(Cursor.DEFAULT);
        });

        restart.setOnMouseClicked(event -> Main.startGame());

        Text quit = new Text(">  Quit");
        quit.fontProperty().setValue(Font.font("Press Start 2P", 50));
        quit.setFill(Color.WHITE);
        quit.setTranslateX(200);
        quit.setTranslateY(600);
        quit.setOnMouseClicked(e -> stage.close());

        quit.setOnMouseEntered(event -> {
            quit.setText(" > Quit");
            over.setCursor(Cursor.HAND);
        });

        quit.setOnMouseExited(event -> {
            quit.setText(">  Quit");
            over.setCursor(Cursor.DEFAULT);
        });

        overRoot.getChildren().addAll(background, title, restart, quit);
        stage.setScene(over);
    }

    public static Group getGameInfos(){
        Group info = new Group();
        info.setOpacity(0);

        Rectangle r = new Rectangle(1280, 720);
        r.setFill(Color.BLACK);
        r.setOpacity(0.75);

        ImageView infoImg = new ImageView(new Image("img/InfosMenu.png"));

        Text infoScore = new Text();
        infoScore.fontProperty().setValue(Font.font("Press Start 2P", 30));
        infoScore.setFill(Color.WHITE);
        infoScore.setX(983);
        infoScore.setY(107);

        ImageView infoAvatar = new ImageView(new Image("img/avatar.gif"));
        infoAvatar.setX(80);
        infoAvatar.setY(35);
        infoAvatar.setFitWidth(30);
        infoAvatar.setFitHeight(30);

        ImageView infoPortal = new ImageView(new Image("img/blocNextLevel.gif"));
        infoPortal.setX(65);
        infoPortal.setY(105);
        infoPortal.setFitWidth(45);
        infoPortal.setFitHeight(45);

        ImageView infoCheckPoint = new ImageView(new Image("img/checkpoint.gif"));
        infoCheckPoint.setX(70);
        infoCheckPoint.setY(190);
        infoCheckPoint.setFitWidth(50);
        infoCheckPoint.setFitHeight(50);

        ImageView infoMonster = new ImageView(new Image("img/monstre.gif"));
        infoMonster.setX(70);
        infoMonster.setY(270);
        infoMonster.setFitWidth(50);
        infoMonster.setFitHeight(50);

        ImageView infoLives = new ImageView(new Image("img/threeHeart.png"));
        infoLives.setX(45);
        infoLives.setY(365);

        ImageView infoWeapon = new ImageView(new Image("img/arme.png"));
        infoWeapon.setX(80);
        infoWeapon.setY(450);
        infoWeapon.setFitWidth(30);
        infoWeapon.setFitHeight(30);

        AnimationTimerCustom timerScoreInfo = new AnimationTimerCustom(1) {
            @Override
            public void handle() {
                infoScore.setText(String.valueOf(SaveSystem.getScore()));
            }
        };
        timerScoreInfo.start();

        info.getChildren().addAll(r, infoImg, infoScore, infoAvatar, infoPortal, infoCheckPoint, infoMonster, infoLives, infoWeapon);
        return info;
    }

    // Classe interne pour les éléments de la trainée
    private class Trail {
        Rectangle point;

        private Trail(double x, double y, Color color){
            this.point = new Rectangle(x, y,20, 20);
            this.point.setFill(color);

            AnimationTimerCustom timer = new AnimationTimerCustom(5) {
                int t = 0;
                @Override
                public void handle() {
                    t++;
                    if (t % 10 == 0){
                        double opa = point.getOpacity();
                        point.setOpacity(opa - 0.1);
                    }
                    if (t == 100) {
                        root.getChildren().remove(point);
                        this.stop();
                    }
                }
            };

            root.getChildren().add(point);
            timer.start();
        }
    }
}
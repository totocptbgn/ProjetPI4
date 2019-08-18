import com.sun.javafx.geom.Point2D;
import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

/**
 * Classe créant et gérant les comportements des niveaux...
 */

public class Niveau {
    private int id;

    private HashMap<KeyCode, Boolean> keys;
    private ArrayList<ObstacleView> obstacle;
    private ArrayList<AnimationTimer> timers;
    private ArrayList<ProjectileView> projectiles;
    private ArrayList<ProjectileView> projectilesEnnemis;

    private AvatarView avatar;
    private AvatarMdl avatarMdl;

    private Stage stage;
    private Scene scene;
    private Group root;
    private Group gameRoot;

    private ImageView heart;
    private Chrono chrono;
    private ObstacleNextLevel nextLevel;
    private Ammunition ammo;
    private Group ath;

    private boolean infoOn;
    private Group info;

    private float gravityForce;
    private boolean jumpSpam;

    public Niveau(int id) {
        this.root = new Group();
        this.gameRoot = new Group();
        this.stage = Main.primaryStage;
        this.id = id;
        this.keys = new HashMap<>();
        this.avatarMdl = Main.avatar;
        this.nextLevel = null;
        this.infoOn = false;
        this.info = new Group();
        this.ath = new Group();
        this.gravityForce = 50;
        this.jumpSpam = true;
    }

    // Fonction à appeler pour lancer le niveau (donc quand il est "actif")
    public void launch() {
        // On cache le niveau le temps qu'il se construise
        stage.hide();
        // On mets en place les listes
        obstacle = new ArrayList<>();
        timers = new ArrayList<>();
        projectiles = new ArrayList<>();
        projectilesEnnemis = new ArrayList<>();

        // On va chercher la map et on la construit
        try {
            Map.MapData mapData = Map.maps[id - 1];
            Map.buildMap(mapData.getTileMap(), this);
        } catch (IndexOutOfBoundsException e){
            // Cas où on avait déjà fini le jeu et que l'on relance aprèst
            finJeu();
            return;
        }
        SaveSystem.savePosition(Map.maps[id - 1].getPosX(), Map.maps[id - 1].getPosX());

        // On construit le niveau
        avatar = new AvatarView(avatarMdl);
        Map.construct(this);
        Camera cam = new Camera(avatar, gameRoot, Map.maps[id-1].getTileMap()[0].length() * 40, Map.maps[id-1].getTileMap().length * 40);
        cam.setup();
        cam.setParallax((Group)root.getChildren().get(0));

        // Affichage du niveau
        scene = new Scene(root);
        stage.setTitle("Niveau " + id);
        stage.setScene(scene);
        stage.setResizable(false);

        // Gestion du curseur
        scene.setOnMouseEntered(event -> scene.setCursor(Cursor.NONE));
        scene.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));

        // Gestion des touches
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        // Création de la barre de vie
        heart = new ImageView();
        switch (avatar.getAvatar().getPv()) {
            case 1:
                heart = new ImageView(new Image("./img/oneHeart.png"));
                break;
            case 2:
                heart = new ImageView(new Image("./img/twoHeart.png"));
                break;
            case 3:
                heart = new ImageView(new Image("./img/threeHeart.png"));
                break;
        }
        heart.setLayoutX(591);
        heart.setLayoutY(40);
        ath.getChildren().add(heart);

        // Création du chrono
        chrono = new Chrono(ath);
        timers.add(chrono.getTimer());

        // Création du compteur de munitions
        ammo = new Ammunition(ath);
        avatarMdl.setAmmoAff(ammo);

        // Ajout de l'ATH
        root.getChildren().add(ath);

        // Mise en place de l'écran d'info
        info = Menu.getGameInfos();
        root.getChildren().add(info);

        // Mise en place du timer qui update la vue en fonction du modèle
        AnimationTimerCustom timer = new AnimationTimerCustom(10) {
            @Override
            public void handle() {
                update();
            }
        };
        timers.add(timer);
        timer.start();

        AnimationTimerCustom timerTir = new AnimationTimerCustom(800) {
            @Override

            public void handle() {
                for (ObstacleView ob : obstacle) {
                    if (ob instanceof ObstacleEnnemitirView) {
                        if (avatarMdl.getPositionX() > ob.getObstacle().getPositionX()) {
                            if (avatarMdl.getPositionX() - ob.getObstacle().getPositionX() < 400 && avatarMdl.getPositionY() - ob.getObstacle().getPositionY() < 30 ) {
                                ProjectileView a = new ProjectileView(new Projectile((float) (ob.getObstacle().getPositionX() + ob.getObstacle().getLargeur()), (float) (ob.getObstacle().getPositionY() + (ob.getObstacle().getHauteur() / 2)), -1, true),2);
                                projectilesEnnemis.add(a);
                                gameRoot.getChildren().add(a.getView());
                                Audio.sonLaser.play();
                            }
                        }
                        if (avatarMdl.getPositionX() < ob.getObstacle().getPositionX() && avatarMdl.getPositionY() - ob.getObstacle().getPositionY() < 30) {
                            if (ob.getObstacle().getPositionX() - avatarMdl.getPositionX() < 400) {
                                ProjectileView a = new ProjectileView(new Projectile((ob.getObstacle().getPositionX() -20), (float) (ob.getObstacle().getPositionY() + (ob.getObstacle().getHauteur() / 2)), 1, true),2);
                                projectilesEnnemis.add(a);
                                gameRoot.getChildren().add(a.getView());
                                Audio.sonLaser.play();
                            }
                        }
                    }
                }
            }
        };
        timers.add(timerTir);
        timerTir.start();
        stage.show();
    }

    public boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void toucheObstacleQuiBouge() {
        boolean ind = false;
        for (ObstacleView ob : obstacle) {
            if (ob instanceof ObstacleNonStableView ) {
                if (!((ObstacleNonStable) ob.getModele()).getVertOuHor()) {
                    if (avatar.getView().getBoundsInParent().intersects(ob.getView().getBoundsInParent()) ) {
                        if (avatar.getAvatar().getPositionY()>=ob.getObstacle().getPositionY()){
                            avatar.getAvatar().setToucheObstacleQuiBouge(true);
                            if (avatar.getAvatar().getPositionX()>=ob.getObstacle().getPositionX()) {
                                avatar.getAvatar().avancerH(20);
                                avatar.updateImage();
                            }
                            else{
                                avatar.getAvatar().avancerH(-3);
                                avatar.updateImage();
                            }
                        }
                        else {
                            avatarMdl.setPositionY(ob.getObstacle().getPositionY() - (float) ob.getObstacle().getHauteur());
                            avatar.updateImage();
                        }
                    }
                }
                else {
                    if (avatar.getAvatar().contactBottom(ob.getObstacle())){
                        if (!isPressed(KeyCode.D) && !isPressed(KeyCode.A) && !isPressed(KeyCode.Q)) {
                            avatar.getAvatar().setPositionX(5+(int) ob.getModele().getPositionX());
                            avatar.updateImage();
                        }
                    }
                    else {
                        if (avatar.getView().getBoundsInParent().intersects(ob.getView().getBoundsInParent()) && avatarMdl.getPositionX() < ob.getObstacle().getPositionX()&& avatarMdl.getPositionY() >= ob.getObstacle().getPositionY()) {
                            avatar.getAvatar().setToucheObstacleQuiBouge(true);
                            avatarMdl.getVelocity().setLocation(avatarMdl.getVelocity().x - 2, avatarMdl.getVelocity().y);
                            ind = true;
                        } else if (avatar.getView().getBoundsInParent().intersects(ob.getView().getBoundsInParent()) && avatarMdl.getPositionX() > ob.getObstacle().getPositionX()&& avatarMdl.getPositionY() >=ob.getObstacle().getPositionY()) {
                            avatar.getAvatar().setToucheObstacleQuiBouge(true);
                            avatarMdl.getVelocity().setLocation(avatarMdl.getVelocity().x + 2, avatarMdl.getVelocity().y);
                            ind = true;
                        }
                    }
                }

            }
        }
        if(!ind){ avatar.getAvatar().setToucheObstacleQuiBouge(false);}

    }
    private void avancerObstacle(ObstacleNonStable2View ob){
        for (ObstacleView o:obstacle){
           if( (ob.getObstacle().contactLeft(o.getObstacle())||ob.getObstacle().contactRight(o.getObstacle()))&&ob!=o){
               ob.setSens(-ob.getSens());
           }
        }
        ob.getObstacle().avancerH(2*ob.getSens());
        ob.updateImage();
    }
    private void update() {
        ObstacleMdl.movePlatforms(obstacle);
        checkPlayerInteractions();
        avatar.updateSprite(this);
        updateColisionAvatarBlocSpe();
        toucheObstacleQuiBouge();
        avatar.updateMovementByVelocityX(this);
        avatar.updateMovementByVelocityY(this);
    }

    private void checkPlayerInteractions(){
        if (!Audio.sonNiveau.isPlaying()){
            Audio.sonNiveau.play();
        }
        if ((isPressed(KeyCode.D) || isPressed(KeyCode.RIGHT)) && !avatar.getAvatar().getToucheObstacleQuiBouge()) {
            avatarMdl.getVelocity().setLocation(7, avatarMdl.getVelocity().y);
        }

        if ((isPressed(KeyCode.Q) || isPressed(KeyCode.A) || isPressed(KeyCode.LEFT)) && !avatar.getAvatar().getToucheObstacleQuiBouge()) {
            avatarMdl.getVelocity().setLocation(-7, avatarMdl.getVelocity().y);
        }

        if (isPressed(KeyCode.SPACE) && avatarMdl.canJump() && jumpSpam) {
            boolean ind = true;
            for (ObstacleView obstacleView : obstacle) {
                if (avatarMdl.contactTop(obstacleView.getModele())) {
                    ind = false;
                }
            }
            if (ind) {
                avatarMdl.getVelocity().setLocation(avatarMdl.getVelocity().x, avatarMdl.getVelocity().y - 15 - (avatarMdl.getJumpForce()));
                avatarMdl.setJump(false);
                avatar.updateImage();
                if(!Audio.sonSaut.isPlaying()){

                    Audio.sonSaut.play();

                }

            }
            jumpSpam = false;
        }
        if (!isPressed(KeyCode.SPACE)){
            jumpSpam = true;
        }

        // =======================================

        if (isPressed(KeyCode.SHIFT)){
            if (avatarMdl.getAmmo() != 0 && avatarMdl.isTrigger() && avatar.getAvatar().getArme() &&avatarMdl.isTrigger()){
                if (avatarMdl.getSens()) {
                    ProjectileView a = new ProjectileView(new Projectile(avatar.getAvatar().getPositionX() + (float)(avatarMdl.getLargeur()/2), avatar.getAvatar().getPositionY() + (float)(avatarMdl.getHauteur()/2), -1, true),1);
                    projectiles.add(a);
                    gameRoot.getChildren().add(a.getView());
                    Audio.sonLaser.play();
                }
                else {
                    ProjectileView a = new ProjectileView(new Projectile(avatar.getAvatar().getPositionX() + (float)(avatarMdl.getLargeur()/2), avatar.getAvatar().getPositionY() + (float)(avatarMdl.getHauteur()/2), 1, true),1);
                    projectiles.add(a);
                    gameRoot.getChildren().add(a.getView());
                    Audio.sonLaser.play();
                }
                avatarMdl.useAmmo();
                avatarMdl.setTrigger(false);
            }
        } else {
            if (!avatarMdl.isTrigger()){
                avatarMdl.setTrigger(true);
            }
        }

        if (isPressed(KeyCode.TAB)){
            if (!infoOn) {
                infoOn = true;
                info.setOpacity(1);
                ath.setOpacity(0);
            }
        } else {
            if (infoOn){
                infoOn = false;
                info.setOpacity(0);
                ath.setOpacity(1);
            }
        }

        if (isPressed(KeyCode.T)){ // Totalement à refaire... En attendant de faire mieux :/
            for (int i = 0; i < Audio.listeSon.length; i++) {
                Audio.listeSon[i].stop();
            }
        }

        if (isPressed(KeyCode.ESCAPE)) {
            stage.close();
        }
    }

    private void updateColisionAvatarBlocSpe() {
        Projectile.checkProjectileToucheObstacle(this);
        Projectile.checkProjectileEnnemiToucheObstacle(this);
        if (nextLevel != null) checkNextLevel();
        try {
            for (ProjectileView tire:projectilesEnnemis) {
                if (avatar.getView().getBoundsInParent().intersects(tire.getView().getBoundsInParent())) {
                    avatarMort();
                    gameRoot.getChildren().remove(tire.getView());
                    projectilesEnnemis.remove(tire);
                }
            }
            for (ObstacleView obstcl : obstacle) {
                if (obstcl instanceof ObstacleCheckpointView) {
                    if (avatar.getView().getBoundsInParent().intersects(obstcl.getView().getBoundsInParent())) {
                        SaveSystem.savePosition(avatarMdl.getPositionX(), avatarMdl.getPositionY());
                        gameRoot.getChildren().remove(obstcl.getView());
                        obstacle.remove(obstcl);
                        Audio.sonMange.play();
                    }
                }
                if (obstcl instanceof ArmeView){
                    if (avatar.getView().getBoundsInParent().intersects(obstcl.getView().getBoundsInParent())) {
                        if (!avatar.getAvatar().getArme()){
                            avatar.getAvatar().setArme(true, ammo);
                        }
                        else {
                            avatarMdl.ajoutAmmo(5);
                        }

                        gameRoot.getChildren().remove(obstcl.getView());
                        obstacle.remove(obstcl);
                        Audio.sonMange.play();
                    }
                }
                if ((obstcl instanceof ObstaclePiegeView)||(obstcl instanceof ObstacleEnnemiMobileView)) {
                    if (avatar.getView().getBoundsInParent().intersects(obstcl.getView().getBoundsInParent())) {
                        avatarMort();
                    }
                }
                if ((obstcl instanceof ObstacleTrampolineView)) {
                    if (avatarMdl.contactBottom(obstcl.getModele())) {
                        avatarMdl.getVelocity().setLocation(avatarMdl.getVelocity().x, avatarMdl.getVelocity().y - 20 - (avatarMdl.getJumpForce()));
                        avatarMdl.setJump(false);
                        avatar.updateImage();
                        avatar.setTrail(gameRoot, "tramp");
                        Audio.sonTramp.play();
                    }
                }
                if (obstcl instanceof ObstacleNonStable2View){
                    avancerObstacle((ObstacleNonStable2View)obstcl);
                }
                if (obstcl instanceof ObstacleBonusView){
                    int score = SaveSystem.getScore();
                    score = score - 200;
                    if (score < 0) score = 0;
                    gameRoot.getChildren().remove(obstcl.getView());
                    obstacle.remove(obstcl);
                    Audio.sonMange.play();
                }
            }
        } catch (ConcurrentModificationException ignored) {}
        if (avatarMdl.getPositionY() > 720) {
            avatarMort();
        }
    }

    private void checkNextLevel() {
        if (avatar.getView().getBoundsInParent().intersects(nextLevel.getView().getBoundsInParent())) {
            try {
                goNiveauSuivant();
            } catch (IndexOutOfBoundsException e) {
                finJeu();
            }
        }
    }

    private void goNiveauSuivant() throws IndexOutOfBoundsException{
        int idNextLevel = id + 1;
        Niveau nextniv = Main.levels.get(idNextLevel - 1);
        for (AnimationTimer timer : timers) {
            timer.stop();
        }
        avatarMdl.setJump(false);
        SaveSystem.saveLevel(idNextLevel);
        SaveSystem.savePosition(Map.maps[idNextLevel-1].getPosX(), Map.maps[idNextLevel-1].getPosY());
        SaveSystem.saveChrono(SaveSystem.getChrono() + chrono.getTime());
        nextniv.launch();
    }

    private void avatarMort(){
        avatar.getAvatar().perdsUneVie();
        if (!avatarMdl.estVivant()) {
            gameOver();
        }


        switch (avatar.getAvatar().getPv()) {
            case 1:
                heart.setImage(new Image("./img/oneHeart.png"));
                break;
            case 2:
                heart.setImage(new Image("./img/twoHeart.png"));
                break;
            case 3:
                heart.setImage(new Image("./img/threeHeart.png"));
                break;
        }


        ImageView avatarGhost = new ImageView(new Image("img/avatarGhost.png"));
        avatarGhost.setOpacity(0.5);
        avatarGhost.setFitHeight(avatarMdl.getHauteur());
        avatarGhost.setFitWidth(avatarMdl.getLargeur());
        avatarGhost.setX(avatarMdl.getPositionX());
        avatarGhost.setY(avatarMdl.getPositionY());
        gameRoot.getChildren().add(avatarGhost);
        avatar.getAvatar().setPoint(new Point2D(SaveSystem.getPosXFromSave(), SaveSystem.getPosYFromSave()));
        avatar.updateImage();

        Group background = (Group) root.getChildren().get(0);
        if (avatarMdl.getPositionX() > 640){
            gameRoot.setLayoutX(-(avatarMdl.getPositionX() - 640));
            background.setLayoutX((-(avatarMdl.getPositionX() - 640) / 2));
        } else {
            gameRoot.setLayoutX(0);
            background.setLayoutX(0);
        }

        Audio.sonBlessure.play();
        avatarMdl.getVelocity().setLocation(0, 0);

        SaveSystem.saveScore(SaveSystem.getScore() + 100);
    }

    private void finJeu() {
        stage.hide();
        Audio.sonNiveau.stop();

        for (AnimationTimer timer : timers) {
            timer.stop();
        }
        Menu.menuFinJeu();
        stage.show();
    }

    private void gameOver() {
        Audio.sonOver.play();
        Audio.sonNiveau.stop();

        stage.hide();
        for (AnimationTimer timer : timers) {
            timer.stop();
        }

        Menu.menuGameOver();
        stage.show();
    }

    // Setter - Getter


    public int getId() {
        return id;
    }

    public AvatarView getAvatar() {
        return avatar;
    }

    public Group getRoot() {
        return root;
    }

    public ArrayList<AnimationTimer> getTimers() {
        return timers;
    }

    public ArrayList<ObstacleView> getObstacle() {
        return obstacle;
    }

    public void setObstacle(ArrayList<ObstacleView> obstacle) {
        this.obstacle = obstacle;
    }

    public ArrayList<ProjectileView> getProjectiles() {
        return projectiles;
    }

    public ArrayList<ProjectileView> getProjectilesEnnemis() {
        return projectilesEnnemis;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Group getGameRoot() {
        return gameRoot;
    }

    public ObstacleNextLevel getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(ObstacleNextLevel nextLevel) {
        this.nextLevel = nextLevel;
    }

    public float getGravityForce() {
        return gravityForce;
    }
}

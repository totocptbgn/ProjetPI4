import com.sun.javafx.geom.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/*
 * Classe Vue représentant le personnage controllé par le joueur (l'avatar)
 */

public class AvatarView {

    private AvatarMdl avatar;
    private Node view;

    private boolean putDot;
    private boolean lastSpriteChanged;

    public AvatarView(AvatarMdl av, Node img) {
        this.avatar = av;
        this.view = img;
        this.view.setTranslateX(av.getPositionX());
        this.view.setTranslateY(av.getPositionY());
        this.avatar.setHauteur((int) view.getLayoutBounds().getHeight());
        this.avatar.setLargeur((int) view.getLayoutBounds().getWidth());
    }

    public AvatarView(AvatarMdl av){
        this.avatar = av;
    }

    public void updateImage() {
        this.view.setTranslateX(avatar.getPositionX());
        this.view.setTranslateY(avatar.getPositionY());
    }

    public AvatarMdl getAvatar() {
        return avatar;
    }

    public Node getView() {
        return view;
    }

    public void setView(Node a){
        this.view = a;
        this.view.setTranslateX(this.avatar.getPositionX());
        this.view.setTranslateY(this.avatar.getPositionY());
        this.avatar.setHauteur((int) view.getLayoutBounds().getHeight());
        this.avatar.setLargeur((int) view.getLayoutBounds().getWidth());
    }

    public void setTrail(Group root, String type){
        long freq;

        switch(type){
            case "tramp":
                freq = 75;
                break;
            default:
                freq = 1;
                break;
        }

        this.putDot = true;
        AnimationTimerCustom timerDot = new AnimationTimerCustom(freq) {
            int t = 0;
            @Override
            public void handle() {
                t++;
                if (!putDot){
                    putDot = true;
                }

                if (type.equals("tramp") && avatar.getVelocity().y > -10){
                    stop();
                }
            }
        };
        timerDot.start();

        view.translateXProperty().addListener((obs, old, newValue) -> {
            if (putDot){
                new Trail(root, type);
                view.toFront();
                putDot = false;
            }
        });

        view.translateYProperty().addListener((obs, old, newValue) -> {
            if (putDot){
                new Trail(root, type);
                view.toFront();
                putDot = false;
            }
        });

    }

    // Classe interne pour les éléments de la trainée
    public class Trail {
        Node point;

        public Trail(Group root, String type) {
            double x = view.getBoundsInParent().getMinX();
            double y = view.getBoundsInParent().getMinY();

            switch (type){
                case "tramp":
                    ImageView r = new ImageView(new Image("./img/jump_trail.png"));
                    r.setTranslateX(x);
                    r.setTranslateY(y);
                    this.point = r;
                    break;
                default:
                    ImageView a = new ImageView(new Image("img/arme.png"));
                    a.setTranslateX(x);
                    a.setTranslateY(y);
                    a.setFitHeight(30);
                    a.setFitWidth(30);
                    this.point = a;
                    break;
            }

            AnimationTimerCustom timer = new AnimationTimerCustom(10) {
                int t = 0;

                @Override
                public void handle() {
                    t++;
                    if (t > 30){
                        double opa = point.getOpacity();
                        point.setOpacity(opa - 0.1);
                        if (t == 100) {
                            root.getChildren().remove(point);
                            this.stop();
                        }
                    }
                }
            };

            root.getChildren().add(point);
            timer.start();
        }
    }

    public void updateMovementByVelocityX(Niveau n){
        Point2D velocity = avatar.getVelocity();

        // Déplacement de l'avatar en fonction de sa vélocité
        if (velocity.x < 0){ // Déplacement vers la gauche
            for (int i = 0; i < -(velocity.x); i++) {
                boolean ind = true;
                for (ObstacleView obstacleView : n.getObstacle()) {
                    if (avatar.contactLeft(obstacleView.getModele())) {
                        ind = false;
                    }
                }
                if (ind) {
                    avatar.avancerH(-1);
                    this.updateImage();
                    avatar.vaGauche();
                }
            }
            avatar.getVelocity().setLocation(avatar.getVelocity().x + 1, avatar.getVelocity().y);
            if (velocity.x > -1 && velocity.x < 0) velocity.x = 0;
        } else if (velocity.x > 0) { // Déplacement vers la droite
            for (int i = 0; i < velocity.x; i++) {
                boolean ind = true;
                for (ObstacleView obstacleView : n.getObstacle()) {
                    if (avatar.contactRight(obstacleView.getModele())) {
                        ind = false;
                    }
                }
                if (ind) {
                    avatar.avancerH(1);
                    this.updateImage();
                    avatar.vaDroite();
                }
            }
            avatar.getVelocity().setLocation(avatar.getVelocity().x -1, avatar.getVelocity().y);
            if (velocity.x < 1 && velocity.x > 0) velocity.x = 0;
        }
    }

    public void updateMovementByVelocityY(Niveau n){
        Point2D velocity = avatar.getVelocity();

        // Application gravité
        boolean contactBottom = false;
        for (ObstacleView obstacleView : n.getObstacle()) {
            if (avatar.contactBottom(obstacleView.getModele())) {
                contactBottom = true;
            }
        }
        if (!contactBottom) {
            if (velocity.y < n.getGravityForce()) {
                velocity.y += 1;
            }
        }

        // Déplacement de l'avatar en fonction de sa vélocité
        if (velocity.y < 0){ // Déplacement vers le haut
            for (int i = 0; i < -(velocity.y); i++) {
                boolean contactTop = false;
                for (ObstacleView obstacleView : n.getObstacle()) {
                    if (avatar.contactTop(obstacleView.getModele())) {
                        contactTop = true;
                    }
                }
                if (!contactTop) {
                    avatar.avancerV(-1);
                    this.updateImage();
                } else {
                    avatar.getVelocity().setLocation(avatar.getVelocity().x, 0);
                }
            }
        } else { // Déplacement vers le bas
            for (int i = 0; i < velocity.y; i++) {
                boolean ind = true;
                for (ObstacleView obstacleView : n.getObstacle()) {
                    if (avatar.contactBottom(obstacleView.getModele())) {
                        ind = false;
                    }
                }
                if (ind) {
                    avatar.avancerV(1);
                    this.updateImage();
                } else {
                    if (!avatar.canJump()){
                        avatar.getVelocity().setLocation(avatar.getVelocity().x, 0);
                        avatar.setJump(true);
                    }
                }
            }
        }

        // Debug pour que l'on puisse pas sauter dans l'air et que l'on puisse re-sauter après avoir respawn
        contactBottom = false;
        for (ObstacleView obstacleView : n.getObstacle()) {
            if (avatar.contactBottom(obstacleView.getModele())) {
                contactBottom = true;
            }
        }
        if (contactBottom) {
            if (!avatar.canJump()){
                avatar.getVelocity().setLocation(avatar.getVelocity().x, 0);
                avatar.setJump(true);
            }
        } else {
            avatar.setJump(false);
        }
    }

    public void updateSprite(Niveau n) {
        if (n.isPressed(KeyCode.Q) || n.isPressed(KeyCode.D) || n.isPressed(KeyCode.A) || n.isPressed(KeyCode.LEFT) || n.isPressed(KeyCode.RIGHT)) {
            boolean sens = avatar.getSens();
            if (sens !=  lastSpriteChanged){
                if (sens) {
                    ((ImageView) view).setImage(new Image("./img/avatar.gif"));
                }
                else {
                    ((ImageView) view).setImage(new Image("./img/avatar_reverse.gif"));
                }
                lastSpriteChanged = avatar.getSens();
            }
        }
    }
}
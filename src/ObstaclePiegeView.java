import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Obstacle représentant des pièges (piège mortel pour l'avatar si colision)
 */

public class ObstaclePiegeView extends ObstacleView {
    public ObstaclePiegeView(ObstacleMdl av, int choix) {
        super(av,1);
        switch (choix){
            case 1:
                this.view = new ImageView(new Image("img/pike.png"));
                break;
            case 2:
                this.view = new ImageView(new Image("img/pike_left.png"));
                break;
            case 3:
                this.view = new ImageView(new Image("img/pike_right.png"));
                break;
            case 4:
                this.view = new ImageView(new Image("img/pike_up.png"));
                break;
            case 5:
                this.view = new ImageView(new Image("img/ennemi_mv.gif"));
                break;
        }
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

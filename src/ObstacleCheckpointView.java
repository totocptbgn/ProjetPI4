import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Obstacle représentant un checkpoint (point de sauvegarde)
 */

public class ObstacleCheckpointView extends ObstacleView {

    public ObstacleCheckpointView(ObstacleMdl av) {
        super(av,1);
        this.view = new ImageView(new Image("./img/checkpoint.gif"));
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

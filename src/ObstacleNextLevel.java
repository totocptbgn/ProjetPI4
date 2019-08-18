import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * Obstacle représentant un checkpoint (point de sauvegarde)
 */

public class ObstacleNextLevel extends ObstacleView {
    public ObstacleNextLevel(ObstacleMdl av) {
        super(av,1);
        this.view = new ImageView(new Image("./img/blocNextLevel.gif"));
        ((ImageView)this.view).setFitHeight(80);
        ((ImageView)this.view).setFitWidth(30);
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

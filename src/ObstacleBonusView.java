import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObstacleBonusView extends ObstacleView {

    public ObstacleBonusView(ObstacleMdl av) {
        super(av, 0);
        this.view = new ImageView(new Image("./img/coin.gif"));
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObstacleTrampolineView extends ObstacleView{

    public ObstacleTrampolineView(ObstacleMdl av) {
        super(av, 0);
        this.view = new ImageView(new Image("img/jump_up.gif"));
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

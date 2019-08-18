import javafx.scene.image.ImageView;

import javafx.scene.image.Image;



public class ObstacleTimer extends ObstacleView {

    public ObstacleTimer(ObstacleMdl av){
        super(av,1);
        this.view = new ImageView(new Image("./img/chrono.gif"));
        ((ImageView)this.view).setFitHeight(60);
        ((ImageView)this.view).setFitWidth(60);
        view.setTranslateY(obstacle.getPositionY());
        view.setTranslateX(obstacle.getPositionY())
        ;
    }



}

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

/**
 * Classe vue pour les obstacles qui peuvent se déplacer
 */

public class ObstacleNonStableView extends ObstacleView {

    private Timeline timeline;

    public ObstacleNonStableView(ObstacleNonStable ob) {
        super(ob,2);
        timeline = new Timeline();
    }

    public void avancerH() {
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2000);
        KeyValue kv = new KeyValue(this.getView().translateXProperty(), this.getObstacle().getPositionX()-((ObstacleNonStable)this.getModele()).getDistance());
        KeyFrame kf = new KeyFrame(javafx.util.Duration.millis(((ObstacleNonStable)this.getModele()).getTemps()), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void avancerV() {
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2000);
        KeyValue kv = new KeyValue(this.getView().translateYProperty(),this.getObstacle().getPositionY()-((ObstacleNonStable)this.getModele()).getDistance());
        this.getModele().setPositionY((float)this.getView().getTranslateY());
        KeyFrame kf = new KeyFrame(javafx.util.Duration.millis(((ObstacleNonStable)this.getModele()).getTemps()), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public Timeline getTimeline(){
        return timeline;
    }
}

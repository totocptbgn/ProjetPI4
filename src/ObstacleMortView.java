import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ObstacleMortView extends ObstacleEnnemiMobileView {
    public ObstacleMortView(ObstacleNonStable ob, int choix) {
        super(ob);
        switch (choix){
            case 1:
                this.view = new ImageView(new Image("img/monstre.gif"));
                ((ImageView)this.view).setFitHeight(38);
                ((ImageView)this.view).setFitWidth(38);
                break;
            case 2:
                this.view = new ImageView(new Image("img/monstre clone.gif"));
                ((ImageView)this.view).setFitHeight(38);
                ((ImageView)this.view).setFitWidth(38);
                break;
        }
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
    }
}

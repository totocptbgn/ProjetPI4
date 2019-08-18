import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe vue pour les projectiles
 */

public class ProjectileView  extends ObstacleNonStableView{
    public ProjectileView(Projectile p,int choix){
        super(p);
        switch (choix){
            case (1):
                this.view = new ImageView(new Image("img/arme.png"));
                break;
            case (2):
                this.view = new ImageView(new Image("img/avatarGhost.png"));
                break;
        }
        ((ImageView)this.view).setFitWidth(18);
        ((ImageView)this.view).setFitHeight(6);
        view.setTranslateX(obstacle.getPositionX());
        view.setTranslateY(obstacle.getPositionY());
        this.getTimeline().setCycleCount(1);
        this.avancerH();
    }
}

import javafx.scene.Group;
import java.util.ArrayList;

/**
 * Classe modèle pour les projectiles
 */

public class Projectile extends ObstacleNonStable {
    public Projectile(float posX, float posY, int sens, boolean direction){
        super(5,5, posX, posY, sens * 10000, 20000, direction);
    }

    public static void checkProjectileToucheObstacle(Niveau n){
        ArrayList<ProjectileView> projectiles = n.getProjectiles();
        ArrayList<ObstacleView> obstacle = n.getObstacle();
        Group gameRoot = n.getGameRoot();
        try {
            for (int i = 0; i < projectiles.size(); i++) {
                for (int j = 0; j < obstacle.size(); j++) {
                    if (projectiles.get(i).getView().getBoundsInParent().intersects(obstacle.get(j).getView().getBoundsInParent())) {
                        if (obstacle.get(j) instanceof ObstacleMortView) {
                            gameRoot.getChildren().remove(obstacle.get(j).getView());
                            obstacle.remove(j);
                            Audio.sonEnnemiMeurt.play();
                        }
                        gameRoot.getChildren().remove(projectiles.get(i).getView());
                        projectiles.remove(i);
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored){}
    }
    public static void checkProjectileEnnemiToucheObstacle(Niveau n){
        ArrayList<ProjectileView> projectiles = n.getProjectilesEnnemis();
        ArrayList<ObstacleView> obstacle = n.getObstacle();
        Group gameRoot = n.getGameRoot();
        try {
            for (int i = 0; i < projectiles.size(); i++) {
                for (int j = 0; j < obstacle.size(); j++) {
                    if (projectiles.get(i).getView().getBoundsInParent().intersects(obstacle.get(j).getView().getBoundsInParent())||(projectiles.get(i).getView().getTranslateX()<0)) {
                        gameRoot.getChildren().remove(projectiles.get(i).getView());
                        projectiles.remove(i);
                    }

                }
            }
        } catch (IndexOutOfBoundsException ignored){}
    }
}

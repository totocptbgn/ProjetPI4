import com.sun.javafx.geom.Point2D;

import java.util.ArrayList;

/**
 * Obstacle représentant un checkpoint (point de sauvegarde)
 */

public class ObstacleMdl extends EntiteMdl {

    public ObstacleMdl(int l, int h, Point2D c){
        super(l, h, c);
    }

    public ObstacleMdl(double l, double h, float x, float y){
        super(l, h, x, y);
    }

    public static void movePlatforms(ArrayList<ObstacleView> obstacle) {
        for (ObstacleView ob : obstacle) {
            if (ob instanceof ObstacleNonStableView) {
                ob.getModele().setPositionX((int)ob.getView().getTranslateX());
                ob.getModele().setPositionY((int)ob.getView().getTranslateY());
            }
        }
    }
}

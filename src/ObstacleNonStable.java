import com.sun.javafx.geom.Point2D;

/*
 * Classe modèle pour les obstacles qui peuvent se déplacer
 */

public class ObstacleNonStable extends ObstacleMdl {
    private double distance;
    private double temps;
    private boolean vertOuHor; // True pour un obstacle qui bouge horizontalement et false pour verticalement.

    public ObstacleNonStable(int l, int h, Point2D c, double dist, double tps,boolean direction){
        super(l, h, c);
        distance = dist;
        temps = tps;
        vertOuHor = direction;
    }

    public ObstacleNonStable(double l, double h, float x, float y, double dist, double tps,boolean direction){
        super(l, h, x, y);
        distance = dist;
        temps = tps;
        vertOuHor = direction;
    }

    public boolean getVertOuHor(){
        return vertOuHor;
    }

    public double getDistance(){
        return distance;
    }

    public double getTemps(){
        return temps;
    }
}

import com.sun.javafx.geom.Point2D;

/*
 * Classe réprésentant une entité du jeu (bloc, ennemi, avatar...)
 */

public class EntiteMdl {
    private double largeur;
    private double hauteur;
    private Point2D point;
    private Point2D velocity;
    private boolean toucheObstacleQuiBouge;
    private boolean canJump;
    private float jumpForce;

    // Constructeurs

    public EntiteMdl(int l, int h, Point2D c){
        this.largeur = l;
        this.hauteur = h;
        this.point = c;
        this.velocity = new Point2D(0, 0);
        canJump = false;
        jumpForce = 1;
        toucheObstacleQuiBouge = false;
    }

    public void setToucheObstacleQuiBouge(boolean a){
        toucheObstacleQuiBouge=a;
    }

    public boolean getToucheObstacleQuiBouge(){
        return toucheObstacleQuiBouge;
    }

    public EntiteMdl(){
        this.largeur = 0;
        this.hauteur = 0;
        this.point = new Point2D();
        this.velocity = new Point2D(0, 0);
        canJump = false;
        jumpForce = 1;
    }

    public EntiteMdl(double l, double h, float x, float y){
        this.largeur = l;
        this.hauteur = h;
        this.point = new Point2D(x, y);
        this.velocity = new Point2D(0, 0);
        canJump = false;
        jumpForce = 1;
    }

    // Fonction test Colisions

    public boolean contactRight(ObstacleMdl obj){
        return ((this.point.x + this.largeur == (obj.getPositionX())) &&
               ((this.point.y + (this.getHauteur())) > obj.getPositionY()) &&
               (this.point.y < obj.getPositionY() + (obj.getHauteur())));
    }

    public boolean contactLeft(ObstacleMdl obj){
        return ((this.point.x == (obj.getPositionX() + obj.getLargeur())) &&
               (this.point.y + (this.getHauteur()) > obj.getPositionY()) &&
               (this.point.y < obj.getPositionY() + (obj.getHauteur())));
    }

    public boolean contactTop(ObstacleMdl obj) {
        return ((this.point.x + (this.getHauteur()) > obj.getPositionX()) &&
               (this.point.x < obj.getPositionX() + (obj.getLargeur())) &&
               (this.getPositionY() - obj.getHauteur() == obj.getPositionY()));
    }

    public boolean contactBottom(ObstacleMdl ob){
        return  ((this.getPositionY() + this.getHauteur() == ob.getPositionY()) &&
                ((this.getPositionX()) < (ob.getPositionX()) + ob.getLargeur()) &&
                ((this.getPositionX() + (this.getLargeur())) > ob.getPositionX()));
    }

    // Fonction Déplacement

    public void avancerH(float dist) {
        this.point.x += dist;
    }

    public void avancerV(float dist) {
        this.point.y += dist;
    }


    public Point2D getVelocity() {
        return velocity;
    }

    public float getPositionX() {
        return point.x;
    }

    public void setPositionX(float positionX) {
        this.point.x = positionX;
    }

    public float getPositionY() {
        return point.y;
    }

    public void setPositionY(float positionY) {
        this.point.y = positionY;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setHauteur(int h) {
        hauteur = h;
    }

    public void setLargeur(int l){
        largeur = l;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public boolean canJump() {
        return canJump;
    }

    public void setJump(boolean canJump) {
        this.canJump = canJump;
    }

    public float getJumpForce() {
        return jumpForce;
    }
}

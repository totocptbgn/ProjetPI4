public class ObstacleNonStable2View extends ObstacleMortView {

    private int sens;

    public ObstacleNonStable2View(ObstacleNonStable ob) {
        super(ob,1);
        sens=1;
        this.getTimeline().stop();

    }

    public int getSens(){
        return sens;
    }

    public void setSens(int a ){
        sens = a;
    }

    public void updateImage() {
        this.view.setTranslateX(this.getObstacle().getPositionX());
        this.view.setTranslateY(this.getObstacle().getPositionY());
    }
}


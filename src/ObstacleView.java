import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Obstacle de base (représantant un mur)
 */

public class ObstacleView {
    ObstacleMdl obstacle;
    Node view;

    public ObstacleView(ObstacleMdl av, int choix) {
        this.obstacle = av;
        switch (choix) {
            case 0:
                this.view = new ImageView(new Image("img/bloc_full.png"));
                break;
            case 1:
                this.view = new ImageView(new Image("img/bloc_invfull.png"));
                break;
            case 2:
                this.view = new ImageView(new Image("img/bloc_up.png"));
                break;
            case 3:
                this.view = new ImageView(new Image("img/bloc_down.png"));
                break;
            case 4:
                this.view = new ImageView(new Image("img/bloc_left.png"));
                break;
            case 5:
                this.view = new ImageView(new Image("img/bloc_right.png"));
                break;
            case 6:
                this.view = new ImageView(new Image("img/bloc_upleft.png"));
                break;
            case 7:
                this.view = new ImageView(new Image("img/bloc_upright.png"));
                break;
            case 8:
                this.view = new ImageView(new Image("img/bloc_downright.png"));
                break;
            case 9:
                this.view = new ImageView(new Image("img/bloc_downleft.png"));
                break;
            case 10:
                this.view = new ImageView(new Image("img/bloc_leftright.png"));
                break;
            case 11:
                this.view = new ImageView(new Image("img/bloc_updown.png"));
                break;
            case 12:
                this.view = new ImageView(new Image("img/bloc_invright.png"));
                break;
            case 13:
                this.view = new ImageView(new Image("img/bloc_invup.png"));
                break;
            case 14:
                this.view = new ImageView(new Image("img/bloc_invleft.png"));
                break;
            case 15:
                this.view = new ImageView(new Image("img/bloc_invdown.png"));
                break;
            case 16:
                this.view = new ImageView();
                view.setOpacity(0);
                break;
            case 17:
                this.view = new ImageView(new Image("./img/arme.png"));
                break;
            case 18:
                this.view = new ImageView(new Image("img/chrono.png"));
                break;

        }
        if (view != null) {
            view.setTranslateX(obstacle.getPositionX());
        }
        if (view != null) {
            view.setTranslateY(obstacle.getPositionY());
        }
    }

    public void setView(Node a) {
        this.view = a;
    }

    public ObstacleMdl getModele() {
        return obstacle;
    }

    public ObstacleMdl getObstacle() {
        return obstacle;
    }

    public Node getView() {
        return view;
    }
}

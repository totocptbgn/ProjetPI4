import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe qui produit un fond qui défile
 */

public class AnimatedBackgroud {
    private Group background;
    private ImageView toMoveRight;
    private ImageView toMoveLeft;
    private AnimationTimerCustom timer;

    public AnimatedBackgroud(String imageUrl){
        this.background = new Group();

        Image image = new Image(imageUrl);
        double w =  image.getWidth();

        ImageView img0 = new ImageView(image);
        img0.setX(-(w/2));

        ImageView img1 = new ImageView(image);
        img1.setX(w/2);

        background.getChildren().addAll(img0, img1);

        this.toMoveRight = img1;
        this.toMoveLeft = img0;

        background.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > oldValue.doubleValue()){
                if (background.getLayoutX() % w - w/2 == 0){
                    toMoveRight.setLayoutX(toMoveRight.getLayoutX() - w * 2);
                    if (toMoveRight == img0){
                        toMoveRight = img1;
                        toMoveLeft = img0;
                    } else {
                        toMoveRight = img0;
                        toMoveLeft = img1;
                    }
                }
            } else {
                if (background.getLayoutX() % w + w/2 == 0){
                    toMoveLeft.setLayoutX(toMoveLeft.getLayoutX() + w * 2);
                    if (toMoveLeft == img0){
                        toMoveLeft = img1;
                        toMoveRight = img0;
                    } else {
                        toMoveLeft = img0;
                        toMoveRight = img1;
                    }
                }
            }
        });

        this.timer = new AnimationTimerCustom(1) {
            @Override
            public void handle() {
                background.setLayoutX(background.getLayoutX() + 1);
            }
        };
    }

    public Group getBackground() {
        return background;
    }

    public void start(){
        this.timer.start();
    }

    public void stop(){
        this.timer.stop();
    }
}

import javafx.scene.Group;

public class Camera {

    private AvatarView avatar;
    private Group gameRoot;
    private int levelWidth;
    private int levelHeight;

    public Camera(AvatarView avatar, Group gameRoot, int levelWidth, int levelHeight) {
        this.avatar = avatar;
        this.gameRoot = gameRoot;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    public void setup(){
        // Caméra horizontal
        avatar.getView().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });
        /*
        // Caméra vertical
        avatar.getView().translateYProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 300 && offset < levelHeight - 395) {
                gameRoot.setLayoutY(-(offset - 300));
            }
        });
        */
        this.avatar.updateImage();
    }

    public void setParallax(Group background){
        avatar.getView().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                background.setLayoutX((float)(-(offset - 640) / 2));
            }
        });
    }
}

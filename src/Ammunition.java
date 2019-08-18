import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
 * Classe qui gère l'affichage des munitions
 */

public class Ammunition {

    private Text text;
    private ImageView ammo;

    public Ammunition(Group root){
        text = new Text(20, 40, "");
        text.setFill(Color.WHITE);
        text.setFont(new Font("Press Start 2P", 20));
        this.ammo = new ImageView(new Image("./img/arme.png"));
        (this.ammo).setFitWidth(30);
        (this.ammo).setFitHeight(30);
        root.getChildren().addAll(text, ammo);
        this.hide();
    }

    public void update(int ammo){
        text.setText(Integer.toString(ammo));
        if (ammo == 0){
            text.setFill(Color.RED);
        } else {
            text.setFill(Color.WHITE);
        }

        if (ammo >= 100){
            this.ammo.setLayoutX(83);
            this.ammo.setLayoutY(13);
        } else {
            this.ammo.setLayoutX(65);
            this.ammo.setLayoutY(13);
        }
    }

    public void hide(){
        this.text.setOpacity(0);
        this.ammo.setOpacity(0);
    }

    public void show(){
        this.text.setOpacity(1);
        this.ammo.setOpacity(1);
    }

}

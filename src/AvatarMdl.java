import com.sun.javafx.geom.Point2D;

/*
 * Classe modèle du personnage contrôlable par le joueur.
 */

public class AvatarMdl extends EntiteMdl {

    private int pv;
    private boolean sens; // True pour droite, False pour gauche
    private Integer ammo;
    private boolean trigger;
    private boolean arme;
    private Ammunition ammoAff;

    public AvatarMdl(Point2D c){
        super(0,0, c);
        pv = 3;
        sens = true;
        ammo = 0;
        arme =false;
    }

    public AvatarMdl(){
        super();
        pv = 3;
        sens = true;
        ammo = 0;
    }

    public boolean getArme(){
        return arme;
    }

    public void setArme(boolean a, Ammunition ammo){
        arme = a;
        if (arme){
            ammo.show();
        } else {
            ammo.hide();
        }
    }

    public boolean estVivant(){
        return pv > 0;
    }

    public void perdsUneVie(){
        this.pv--;
    }

    public int getPv() {
        return pv;
    }

    public boolean getSens() {
        return sens;
    }

    public void vaDroite(){
        this.sens = true;
    }

    public void vaGauche(){
        this.sens = false;
    }

    public void useAmmo() {
        ammo--;
        this.ammoAff.update(ammo);
    }

    public void ajoutAmmo(int a){
        ammo+=a;
        this.ammoAff.update(ammo);
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
        if (ammoAff != null) this.ammoAff.update(ammo);
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public void setAmmoAff(Ammunition ammoAff) {
        this.ammoAff = ammoAff;
        this.ammoAff.update(ammo);
    }
}
import javafx.animation.AnimationTimer;

/**
 * Classe abstraite qui produit un timer donc la fréquence est fixée et réglable
 */

public abstract class AnimationTimerCustom extends AnimationTimer {
    private long sleepNs;
    private long prevTime;

    public AnimationTimerCustom(long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
        this.prevTime = 0;
    }

    @Override
    public void handle(long now) {
        if ((now - prevTime) < sleepNs) {
            return;
        }
        prevTime = now;
        handle();
    }

    public abstract void handle();
}
package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.screan.IZobraz;

import javax.swing.*;
import java.awt.*;

/**
 * Táto trieda má za úlohu vykresľovať pozadie hry.
 *
 * @author Tobias
 */
public class Pozadie implements IZobraz {
    private boolean jeViditelne;
    private Image image;

    public Pozadie() {
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/pozadie2.jpg").getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.jeViditelne) {
            this.vykreslovacPozadia(grafika);
        }

    }

    private void vykreslovacPozadia(Graphics grafika) {
        grafika.drawImage(this.image, -15, -15, null);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

        this.jeViditelne = viditelne;
    }
}

package sk.falloutshelter.fri.screan;

import javax.swing.*;
import java.awt.*;

public class UvodnaObrazovka implements IZobraz {
    private boolean zobrazene;
    private Image image;

    public UvodnaObrazovka() {
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/boot screan.png").getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.zobrazene) {
            grafika.drawImage(this.image, 0, 0, null);
        }

    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.zobrazene = viditelne;
    }
}

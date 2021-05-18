package sk.falloutshelter.fri.screan;

import sk.falloutshelter.fri.Hra;

import javax.swing.*;
import java.awt.*;

/**
 * Tato trieda plní zatiaľ len jednú funkciu a to je stavanie nových miestnosti.
 *
 * @author Tobias
 */
public class Tlacitka implements IZobraz, IKlik{
    private final int x;
    private final int y;
    private final Hra hra;
    private final String cestaObrazka;
    private final Image image;
    private boolean viditelne;

    public Tlacitka(int x, int y, Hra hra, String cestaObrazka) {
        this.x = x;
        this.y = y;
        this.hra = hra;
        this.cestaObrazka = cestaObrazka;
        this.image = new ImageIcon(cestaObrazka).getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.viditelne) {
            grafika.drawImage(this.image, this.x, this.y, null);
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.viditelne = viditelne;
    }

    @Override
    public void klik(int x, int y) {
        if (this.viditelne && x > this.x && y > this.y && x < this.image.getHeight(null) + this.x && y < this.image.getWidth(null) + this.y) {
            //System.out.println("klik");
            this.hra.setStavObrazokvy(StavObrazovky.Stavanie);
        }
    }
}

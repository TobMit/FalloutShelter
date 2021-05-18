package sk.falloutshelter.fri.screan;

import javax.swing.*;
import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Tlacitka implements IZobraz{
    private final int x;
    private final int y;
    private final String cestaObrazka;
    private boolean viditelne;

    public Tlacitka(int x, int y, String cestaObrazka) {
        this.x = x;
        this.y = y;
        this.cestaObrazka = cestaObrazka;
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.viditelne) {
            grafika.drawImage(new ImageIcon(this.cestaObrazka).getImage(), this.x, this.y, null);
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.viditelne = viditelne;
    }
}

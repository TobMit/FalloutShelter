package sk.falloutshelter.fri.screan;

import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 * Tato trieda plní zatiaľ len jednú funkciu a to je stavanie nových miestnosti.
 *
 * @author Tobias
 */
public class Tlacitka implements IZobraz, IKlik {
    private final int x;
    private final int y;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final Image image;
    private boolean viditelne;

    //                                     Toto tu je dočastne (možno trvale) xD ka sa nájdu ešte ine spôsoby ako využiť tlačidlo.
    public Tlacitka(int x, int y, RozlozenieMiestnosti rozlozenieMiestnosti, String cestaObrazka) {
        this.x = x;
        this.y = y;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
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
            this.rozlozenieMiestnosti.vyberoveMenu();
        }
    }
}

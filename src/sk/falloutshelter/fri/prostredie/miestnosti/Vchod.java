package sk.falloutshelter.fri.prostredie.miestnosti;


import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 * Toto je "vstupná hala" bunkra, nedá sa ničiť ani postaviť. tá je vždy napevno 2 bunkova
 *
 * @author Tobias
 */
public class Vchod extends Miestnosti {


    private final int stlpec;
    private final int riadok;
    private Image image;

    public Vchod(int riadok, int stlpec) {
        super(riadok, stlpec);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/Vstup/vstup2.jpg").getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.drawImage(this.image, Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, null);
//        grafika.setColor(Color.decode("#424242"));
//        grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI * 2, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }
}

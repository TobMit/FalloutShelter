package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;


import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;

import javax.swing.*;
import java.awt.*;

/**
 * Toto je "vstupná hala" bunkra, nedá sa ničiť ani postaviť. tá je vždy napevno 2 bunkova
 *
 * @author Tobias
 */
public class Vchod extends Miestnosti {


    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final int riadok;
    private Image image;

    public Vchod(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
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

    @Override
    public void klik(int x, int y) {

    }

    @Override
    public String toString() {
        return "Vchod";
    }

    @Override
    public void tik() {

    }
}

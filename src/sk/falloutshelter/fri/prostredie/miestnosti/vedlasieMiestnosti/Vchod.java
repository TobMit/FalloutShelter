package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;


import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * Toto je "vstupná hala" bunkra, nedá sa ničiť ani postaviť. Tá je vždy na pevno postavená.
 * @author Tobias
 */
public class Vchod extends Miestnosti {


    private final int stlpec;
    private final int riadok;
    private final Image image;

    public Vchod(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/Vstup/vstup2.jpg").getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.drawImage(this.image, Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, null);
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

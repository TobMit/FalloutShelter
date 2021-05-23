package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 * Táto miestnosť slúži na to aby sa mohlo stavať aj v nižších vrstvách bunkra.
 * @author Tobias
 */
public class Vytah extends sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti {
    private final int riadok;
    private final int stlpec;
    private final Image image;

    public Vytah(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/Vytah/vytah-1.jpg").getImage();

        if (!(riadok == 0 && stlpec == 0)) {
            rozlozenieMiestnosti.novyVytah();
        }
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
        return "Vytah";
    }

    /**
     * Identifikátor pre uladanie
     * @return identifikátor
     */
    @Override
    public int toStringInentifikator() {
        return 0x567974;
    }
}

package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Tobias
 */
public class Vytah extends sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti {
    private final int riadok;
    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private Image image;

    public Vytah(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/Vytah/vytah-1.jpg").getImage();

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novyVytah();
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        //grafika.setColor(Color.white);
        //grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
        grafika.drawImage(this.image, Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, null);
        //System.out.printf("%d %d\n", this.image.getHeight(null), this.image.getWidth(null));
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

    @Override
    public void tik() {

    }
}

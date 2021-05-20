package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import java.awt.*;

/**
 *
 * @author Tobias
 */
public class Vodaren extends Miestnosti {
    private final int riadok;
    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;

    public Vodaren(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaVodaren();
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.black);
        grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }

    @Override
    public void klik(int x, int y) {

    }

    @Override
    public String toString() {
        return "Vodaren";
    }

    @Override
    public void tik() {

    }
}

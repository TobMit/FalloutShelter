package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;

import java.awt.*;

/**
 *
 * @author Tobias
 */
public class Ubytovanie extends Miestnosti {


    private final int riadok;
    private final int stlpec;

    public Ubytovanie(int riadok, int stlpec) {
        super(riadok, stlpec);
        this.riadok = riadok;
        this.stlpec = stlpec;
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.red);
        grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }
}

package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;

import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;

import java.awt.*;

/**
 * Tato miestnost slúži len ako prázdna miestnost a aby som nemal null takže príma všeto ale na nič nereaguje
 * možno v budúcnosti bude mať aj nejaké využitie
 *
 * @author Tobias
 */
public class VyplnaciaMiestnost extends Miestnosti {
    public VyplnaciaMiestnost(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
    }

    @Override
    public void zobraz(Graphics grafika) {

    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }

    @Override
    public void klik(int x, int y) {

    }

    @Override
    public void tik() {

    }

    @Override
    public String toString() {
        return "NullMiestnost";
    }
}

package sk.falloutshelter.fri.prostredie;

import java.awt.*;

/**
 * Tato miestnost slúži len ako prázdna miestnost a aby som nemal null takže príma všeto ale na nič nereaguje
 * možno v budúcnosti bude mať aj nejaké využitie
 *
 * @author Tobias
 */
public class NullMiestnost extends Miestnosti {
    public NullMiestnost(int riadok, int stlpec) {
        super(riadok, stlpec);
    }

    @Override
    public void zobraz(Graphics grafika) {

    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }
}

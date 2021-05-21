package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.ITik;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;


/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public abstract class Miestnosti implements IZobraz, IKlik, ITik {
    private final int riadok;
    private final int stlpec;
    protected StavMiestnosti stavMiestnosti;
    protected int odpocitavanie;

    public Miestnosti(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.riadok = riadok;
        this.stlpec = stlpec;
    }
    public void pridajCloveka() {
    }
    
    public void tik() {
        if (this.odpocitavanie <= 0 && (this.stavMiestnosti == StavMiestnosti.Pracuje)) {
            this.stavMiestnosti = StavMiestnosti.Spracovane;
        }

        if (this.stavMiestnosti == StavMiestnosti.Spracovane) {
            this.odpocitavanie = 0;
            return;
        }

        if (this.odpocitavanie > 0) {
            this.odpocitavanie--;
        }
    }


}

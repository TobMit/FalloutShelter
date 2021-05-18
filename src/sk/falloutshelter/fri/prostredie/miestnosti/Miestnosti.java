package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;


/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public abstract class Miestnosti implements IZobraz, IKlik {
    private final int riadok;
    private final int stlpec;

    public Miestnosti(int riadok, int stlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
    }

}

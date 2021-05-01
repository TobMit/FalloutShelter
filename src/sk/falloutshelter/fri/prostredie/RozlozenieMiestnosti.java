package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.NullMiestnost;
import sk.falloutshelter.fri.prostredie.miestnosti.Vchod;

import java.awt.*;

/**
 *Táto tireda má nastorosť vytváranie usporiadanie miestnosti a informovať miestnosti o počte ludí v nej
 */
public class RozlozenieMiestnosti {
    public static final int SIRKA_MIESTNOSTI = 140;
    public static final int VYSKA_MIESTNOSTI = 90;
    private final Miestnosti[][] miestnosti;

    //                                                prvý rad:      2, 1(vyťah ale ten môže byť kdekoľvek) 3, 3
    // Očakávané rozloženie všetkých miestností okrem prvého radu je 3, 1(vyťáh ale ten môže byť kdekoľvek) 3, 3  (čísla je dlžka miestnosti)
    // veľkosť bunkra je 11 (vyska) x 10
    public RozlozenieMiestnosti() {
        this.miestnosti = new Miestnosti[11][10];
        this.miestnosti[0][0] = new NullMiestnost(0 , 0);
        this.miestnosti[0][1] = new Vchod(0, 1);
        this.miestnosti[0][2] = this.miestnosti[0][1];
    }

    public void nacitajMiestnosti() {

    }

    public void zobrazMiestnosti(Graphics grafika) {
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                if (miestnost != null) {
                    miestnost.zobraz(grafika);
                }
            }
        }
    }
}

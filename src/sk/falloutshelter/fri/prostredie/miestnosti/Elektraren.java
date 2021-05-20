package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import java.awt.*;

/**
 *
 * @author Tobias
 */
public class Elektraren extends sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti {

    //todo dokončiť, Elektráreň a ostané okrem vodárne nedoknčené.
    private final int riadok;
    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final int xSuradnica;
    private final int ySuradnica;
    private final StavMiestnosti stavMiestnosti;
    private int pocetLudi;
    private int sirkaMiestnosti;

    public Elektraren(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        //this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();

        this.stavMiestnosti = StavMiestnosti.Pracuje;
        this.pocetLudi = 0;
        this.sirkaMiestnosti = 1;

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaElektraren();
        }
    }

    public void pridajCloveka() {
        if (this.pocetLudi < this.sirkaMiestnosti * 2) {
            this.pocetLudi++;
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
        return "Elektraren";
    }

    @Override
    public void tik() {

    }
}

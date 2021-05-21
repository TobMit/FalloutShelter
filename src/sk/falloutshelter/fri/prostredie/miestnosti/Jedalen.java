package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Tobias
 */
public class Jedalen extends Miestnosti {
    private final int riadok;
    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int sirkaMiestnosti;
    private final int xSuradnica;
    private final int ySuradnica;
    private StavMiestnosti stavMiestnosti;
    private int pocetLudi;
    private int odpocitavanie;
    private int maxTime = -1;

    public Jedalen(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        //this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();

        this.stavMiestnosti = StavMiestnosti.Pracuje;
        this.pocetLudi = 0;
        this.sirkaMiestnosti = 1;

        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaJedalen();
        }
        this.pocetLudi = 0;
        this.sirkaMiestnosti = 1;
    }

    public void pridajCloveka() {
        if (this.stavMiestnosti == StavMiestnosti.NemaLudi) {
            this.stavMiestnosti = StavMiestnosti.Pracuje;
        }
        if (this.pocetLudi < this.sirkaMiestnosti * 2) {
            this.pocetLudi++;
            this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCloveka();
            this.maxTime = ((this.pocetLudi - 1) * 54 - 300) * (-1);
            if (this.odpocitavanie > this.maxTime) {
                this.odpocitavanie = this.maxTime;
            }
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.yellow);
        grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);

        if (this.stavMiestnosti == StavMiestnosti.Spracovane) {
            Image spracovaneImag = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/dogenerovanaJedalen-1.png").getImage();
            grafika.drawImage(spracovaneImag, this.xSuradnica, this.ySuradnica, null);
        }

        if (this.stavMiestnosti == StavMiestnosti.ZobrazInfo) {
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + this.odpocitavanie;
            grafika.drawString(casDoKonca, 45, 646);

            String pocetDwelerov = "Pocet ludi: " + this.pocetLudi;
            grafika.drawString(pocetDwelerov, 45, 696);
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }

    @Override
    public void klik(int x, int y) {
        //todo opraviť
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * this.sirkaMiestnosti && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (this.stavMiestnosti == StavMiestnosti.Pracuje || this.stavMiestnosti == StavMiestnosti.NemaLudi) {
                this.stavMiestnosti = StavMiestnosti.ZobrazInfo;
            } else if (this.stavMiestnosti == StavMiestnosti.Spracovane) {
                this.reWork();
            }
        } else {
            if (this.pocetLudi != 0) {
                this.stavMiestnosti = StavMiestnosti.Pracuje;
            } else {
                this.stavMiestnosti = StavMiestnosti.NemaLudi;
            }
        }
    }

    private void reWork() {
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajVodu((2 * this.sirkaMiestnosti * 5) - 2);
        this.odpocitavanie = this.maxTime;
        this.stavMiestnosti = StavMiestnosti.Pracuje;
    }

    @Override
    public String toString() {
        return "Jedalen";
    }

    @Override
    public void tik() {
        if (this.pocetLudi != 0 && this.odpocitavanie <= 0 && (this.stavMiestnosti == StavMiestnosti.Pracuje || this.stavMiestnosti == StavMiestnosti.ZobrazInfo)) {
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

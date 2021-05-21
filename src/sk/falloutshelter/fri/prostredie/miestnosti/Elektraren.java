package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
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
    private StavMiestnosti stavMiestnosti;
    private int pocetLudi;
    private int sirkaMiestnosti;
    private int maxTime = -1;
    private int odpocitavanie;

    public Elektraren(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        //this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();

        super.stavMiestnosti = StavMiestnosti.NemaLudi;
        this.pocetLudi = 0;
        this.sirkaMiestnosti = 1;

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaElektraren();
        }

        //this.pridajCloveka();
    }

    public void pridajCloveka() {
        if (super.stavMiestnosti == StavMiestnosti.NemaLudi) {
            super.stavMiestnosti = StavMiestnosti.Pracuje;
        }
        if (this.pocetLudi < this.sirkaMiestnosti * 2) {
            this.pocetLudi++;
            this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCloveka();
            this.maxTime = ((this.pocetLudi - 1) * 54 - 300) * (-1);
            if (super.odpocitavanie > this.maxTime) {
                super.odpocitavanie = this.maxTime;
            }
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        //System.out.println(this.sirkaMiestnosti);
        grafika.setColor(Color.black);
        grafika.fillRect(this.xSuradnica, this.ySuradnica, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
        if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
            Image spracovaneImag = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/dogenerovanaElektraren-1.png").getImage();
            grafika.drawImage(spracovaneImag, this.xSuradnica, this.ySuradnica, null);
        }

        if (super.stavMiestnosti == StavMiestnosti.ZobrazInfo) {
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + super.odpocitavanie;
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
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * this.sirkaMiestnosti && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.stavMiestnosti == StavMiestnosti.Pracuje || super.stavMiestnosti == StavMiestnosti.NemaLudi) {
                super.stavMiestnosti = StavMiestnosti.ZobrazInfo;
            } else if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
                this.reWork();
            }
        } else {
            if (this.pocetLudi != 0) {
                super.stavMiestnosti = StavMiestnosti.Pracuje;
            } else {
                super.stavMiestnosti = StavMiestnosti.NemaLudi;
            }
        }
    }

    private void reWork() {
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajEnergie((2 * this.sirkaMiestnosti * 5) - 2);
        super.odpocitavanie = this.maxTime;
        super.stavMiestnosti = StavMiestnosti.Pracuje;
    }

    @Override
    public String toString() {
        return "Elektraren";
    }


}

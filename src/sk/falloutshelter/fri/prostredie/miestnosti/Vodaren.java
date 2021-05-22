package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Vodaren extends Miestnosti {
    private int riadok;
    private int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int ySuradnica;
    private int xSuradnica;
    private boolean zobrazInfo;
    private int pocetLudi;
    // keď je zaporne číslo tak nevyrábame
    private int maxTime = -1;
    private Image image;

    public Vodaren(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        //this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();

        super.stavMiestnosti = StavMiestnosti.NemaLudi;
        this.pocetLudi = 0;
        super.pocetLudi = 0;
        super.velkostMiestnosti = 1;
        this.zobrazInfo = false;

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaVodaren();
        }
    }

    public void pridajCloveka() {
        if (super.stavMiestnosti == StavMiestnosti.NemaLudi) {
            super.stavMiestnosti = StavMiestnosti.Pracuje;
        }
        if (this.pocetLudi < super.velkostMiestnosti * 2) {
            this.pocetLudi++;
            super.pocetLudi++;
            this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCloveka();
            this.maxTime = ((this.pocetLudi - 1) * 54 - 300) * (-1);
            if (super.odpocitavanie > this.maxTime) {
                super.odpocitavanie = this.maxTime;
            } else if (super.odpocitavanie == 0){
                super.odpocitavanie = this.maxTime;
            }
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.rozlozenieMiestnosti.getBunker().getZdroje().getEnergia() <= 0) {
            super.stavMiestnosti = StavMiestnosti.NemaEnergiu;
        }
        switch (super.velkostMiestnosti) {
            case 1:
                this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/vodaren/1trieda/vodaren1.jpg").getImage();
                break;
            case 2:
                this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/vodaren/2trieda/vodaren2.jpg").getImage();
                break;
            case 3:
                this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/vodaren/3trieda/vodaren3.jpg").getImage();
                break;
        }
        grafika.drawImage(this.image, this.xSuradnica, this.ySuradnica, null);
        if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
            Image spracovaneImag = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/vodaren/dogenerovanaVodaren-1.png").getImage();
            grafika.drawImage(spracovaneImag, this.xSuradnica, this.ySuradnica, null);
        }

        if (this.zobrazInfo) {
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + super.odpocitavanie;
            grafika.drawString(casDoKonca, 45, 646);

            String pocetDwelerov = "Pocet ludi: " + this.pocetLudi;
            grafika.drawString(pocetDwelerov, 45, 696);
        }
    }


    @Override
    public void klik(int x, int y) throws KlikException {
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * super.velkostMiestnosti && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.stavMiestnosti == StavMiestnosti.Pracuje || super.stavMiestnosti == StavMiestnosti.NemaLudi || super.stavMiestnosti == StavMiestnosti.NemaEnergiu) {
                this.zobrazInfo = true;
            } else if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
                this.reWork();
                throw new KlikException("Klik");
            }
        } else {
            this.zobrazInfo = false;
        }
    }

    private void reWork() {
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajVodu((2 * super.velkostMiestnosti * 5) - 2);
        Random random = new Random();
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCaps(random.nextInt(60) + 15);
        super.odpocitavanie = this.maxTime;
        super.stavMiestnosti = StavMiestnosti.Pracuje;
    }

    @Override
    public String toString() {
        if (this.riadok != 0 && this.stlpec != 0) {
            return String.format("Vodaren %d. poschodie, %d v poradi", this.riadok, this.stlpec);
        }
        return "Vodaren";
    }

    @Override
    public void setSuradnice (int riadok, int stlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
    }

}

package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.KlikException;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.screan.GrafickyZobraovac;
import sk.falloutshelter.fri.screan.GrafikaSelect;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Elektraren extends sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti {
    private int riadok;
    private int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int xSuradnica;
    private int ySuradnica;
    private boolean zobrazInfo;
    private int pocetLudi;
    private int maxTime = -1;

    /**
     * Trieda ktorá vytvára miestnosť pre Elektráreň a všetko čo je s ňou spojené.
     * @author Tobias
     */
    public Elektraren(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;

        super.setStavMiestnosti(StavMiestnosti.NemaLudi);
        this.pocetLudi = 0;
        super.setPocetLudi(this.pocetLudi);
        super.setVelkostMiestnosti(1);
        this.zobrazInfo = false;
        //super.odpocitavanie = this.maxTime;
        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaElektraren();
        }

        //this.pridajCloveka();
    }

    /**
     * Keď môže pridá človek a zmení stav miestnosti.
     */
    public void pridajCloveka() {
        if (super.getStavMiestnosti() == StavMiestnosti.NemaLudi) {
            super.setStavMiestnosti(StavMiestnosti.Pracuje);
        }
        if (this.pocetLudi < super.getVelkostMiestnosti() * 2) {
            this.pocetLudi++;
            super.setPocetLudi(super.getPocetLudi() + 1);
            this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCloveka();
            this.maxTime = ((this.pocetLudi - 1) * 54 - 300) * (-1);
            if (super.getOdpocitavanie() > this.maxTime) {
                super.setOdpocitavanie(this.maxTime);
            } else if (super.getOdpocitavanie() == 0) {
                super.setOdpocitavanie(this.maxTime);
            }
        }
    }

    /**
     * Zobrazí grafiku
     */
    @Override
    public void zobraz(Graphics grafika) {
        new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/1trieda/ekektraen 1.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/2trieda/elekrtraen2.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/3trieda/Elektraren3.jpg",
                grafika, super.getVelkostMiestnosti(), this.xSuradnica, this.ySuradnica);
        if (super.getStavMiestnosti() == StavMiestnosti.Spracovane) {
            new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/1trieda/dogenerovanaElektraren-1.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/2trieda/dogenerovaneElektraren2.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/elektraren/3trieda/dogenerovaneElektraren3.png",
                    grafika, super.getVelkostMiestnosti(), this.xSuradnica, this.ySuradnica);
        }

        if (this.zobrazInfo) {
            new GrafikaSelect(grafika, this.xSuradnica, this.ySuradnica, super.getVelkostMiestnosti());
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + super.getOdpocitavanie();
            grafika.drawString(casDoKonca, 45, 646);

            String pocetDwelerov = "Pocet ludi: " + this.pocetLudi;
            grafika.drawString(pocetDwelerov, 45, 696);
        }
    }

    /**
     * Reaguje na klik a ak klik ukatzuje na túto miestnosť tak vyhodí exception
     */

    @Override
    public void klik(int x, int y) throws KlikException {
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * super.getVelkostMiestnosti() && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.getStavMiestnosti() == StavMiestnosti.Pracuje || super.getStavMiestnosti() == StavMiestnosti.NemaLudi) {
                this.zobrazInfo = true;
            } else if (super.getStavMiestnosti() == StavMiestnosti.Spracovane) {
                this.reWork();
                throw new KlikException("klik");
            }
        } else {
            this.zobrazInfo = false;
        }
    }

    /**
     * reštar práce a doplnenie zdrojov
     */
    private void reWork() {
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajEnergie((2 * super.getVelkostMiestnosti() * 5) - 2);
        Random random = new Random();
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCaps(random.nextInt(60) + 5);
        super.setOdpocitavanie(this.maxTime);
        super.setStavMiestnosti(StavMiestnosti.Pracuje);
    }

    /**
     * vráti korektný názov
     * @return korektný názov
     */
    @Override
    public String toString() {
        if (this.riadok != 0 && this.stlpec != 0) {
            return String.format("Elektraren %d. poschodie, %d v poradi", this.riadok, this.stlpec);
        }
        return "Elektraren";
    }

    @Override
    public void setSuradnice (int riadok, int stlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
    }

    /**
     * identifikátor pre ukladanie
     */
    @Override
    public int toStringInentifikator() {
        //Ele - Elektraren
        return 0x456c65;
    }
}

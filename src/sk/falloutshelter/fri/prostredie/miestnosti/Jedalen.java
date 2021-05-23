package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.screan.GrafickyZobraovac;
import sk.falloutshelter.fri.screan.GrafikaSelect;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Jedalen extends Miestnosti {
    private int riadok;
    private int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private boolean zobrazInfo;
    private int xSuradnica;
    private int ySuradnica;
    private int pocetLudi;
    private int maxTime = -1;

    public Jedalen(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;

        super.setStavMiestnosti(StavMiestnosti.Pracuje);
        this.pocetLudi = 0;
        super.setPocetLudi(this.pocetLudi);
        super.setVelkostMiestnosti(1);
        this.zobrazInfo = false;

        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.novaJedalen();
        }
    }

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

    @Override
    public void zobraz(Graphics grafika) {
        if (this.rozlozenieMiestnosti.getBunker().getZdroje().getEnergia() <= 0) {
            super.setStavMiestnosti(StavMiestnosti.NemaEnergiu);
        } else {
            if (super.getStavMiestnosti() != StavMiestnosti.Spracovane) {
                if (this.pocetLudi != 0) {
                    super.setStavMiestnosti(StavMiestnosti.Pracuje);
                } else {
                    super.setStavMiestnosti(StavMiestnosti.NemaLudi);
                }
            }
        }
        new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/1trieda/jedalen1.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/2trieda/jedalen2.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/3trieda/jedalen3.jpg",
                grafika, super.getVelkostMiestnosti(), this.xSuradnica, this.ySuradnica);
        if (super.getStavMiestnosti() == StavMiestnosti.Spracovane) {
            new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/1trieda/dogenerovanaJedalen-1.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/2trieda/dogenerovanaJedalen2.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/jedalen/3trieda/dogenerovanaJedalen3.png",
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


    @Override
    public void klik(int x, int y) throws KlikException {
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * super.getVelkostMiestnosti() && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.getStavMiestnosti() == StavMiestnosti.Pracuje || super.getStavMiestnosti() == StavMiestnosti.NemaLudi || super.getStavMiestnosti() == StavMiestnosti.NemaEnergiu) {
                this.zobrazInfo = true;
            } else if (super.getStavMiestnosti() == StavMiestnosti.Spracovane) {
                this.reWork();
                throw new KlikException("klik");
            }
        } else {
            this.zobrazInfo = false;
        }
    }

    private void reWork() {
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajJedlo((2 * super.getVelkostMiestnosti() * 5) - 2);
        Random random = new Random();
        this.rozlozenieMiestnosti.getBunker().getZdroje().pridajCaps(random.nextInt(80) + 15);
        super.setOdpocitavanie(this.maxTime);
        super.setStavMiestnosti(StavMiestnosti.Pracuje);
    }

    @Override
    public String toString() {
        if (this.riadok != 0 && this.stlpec != 0) {
            return String.format("Jedalen %d. poschodie, %d v poradi", this.riadok, this.stlpec);
        }
        return "Jedalen";
    }

    @Override
    public void setSuradnice (int riadok, int stlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
    }

    @Override
    public int toStringInentifikator() {
        //Jed - Jedalen
        return 0x4a6564;
    }
}

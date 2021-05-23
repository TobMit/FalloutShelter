package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.screan.GrafickyZobraovac;
import sk.falloutshelter.fri.screan.GrafikaSelect;
import sk.falloutshelter.fri.screan.JpanelVyberMiestnosti;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Ubytovanie extends Miestnosti {


    private int riadok;
    private int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int xSuradnica;
    private int ySuradnica;
    private boolean zobrazInfo;
    private int maxTime;

    public Ubytovanie(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;

        super.setStavMiestnosti(StavMiestnosti.Pracuje);
        Random random = new Random();
        this.maxTime = random.nextInt(300) + 30;
        //dočasne 15
        super.setOdpocitavanie(15);
        super.setVelkostMiestnosti(1);
        this.zobrazInfo = false;

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.noveUbytovanie();
        }
    }

    @Override
    public boolean volneMiestoNaLudi() {
        return false;
    }

    @Override
    public void zobraz(Graphics grafika) {
        new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/ubytovanie/1trieda/ubytovanie1.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/ubytovanie/2trieda/ubytovanie2.jpg",
                "src/sk/falloutshelter/fri/obr/Miestnosti/ubytovanie/3trieda/ubytovanie3.jpg",
                grafika, super.getVelkostMiestnosti(), this.xSuradnica, this.ySuradnica);
        if (super.getStavMiestnosti() == StavMiestnosti.Spracovane) {
            new GrafickyZobraovac("src/sk/falloutshelter/fri/obr/Miestnosti/Ubytovanie/1trieda/dogenerovaneUbytovanie1.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/Ubytovanie/2trieda/dogenerovaneUbytovanie2.png",
                    "src/sk/falloutshelter/fri/obr/Miestnosti/Ubytovanie/3trieda/dogenerovaneUbytovanie3.png",
                    grafika, super.getVelkostMiestnosti(), this.xSuradnica, this.ySuradnica);
        }

        if (this.zobrazInfo) {
            new GrafikaSelect(grafika, this.xSuradnica, this.ySuradnica, super.getVelkostMiestnosti());
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + super.getOdpocitavanie();
            grafika.drawString(casDoKonca, 45, 646);
        }
    }


    @Override
    public void klik(int x, int y) throws KlikException {
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * super.getVelkostMiestnosti() && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.getStavMiestnosti() == StavMiestnosti.Pracuje) {
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
        Miestnosti[] mestnost = this.rozlozenieMiestnosti.getMiestnostiSMaloLudmi();
        for (int i = 0; i < this.getVelkostMiestnosti(); i++) {
            Miestnosti miestnost = JpanelVyberMiestnosti.zobrazVyberoveMenu(mestnost, new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/novyClen.png"));
            if (miestnost == null) {
                //keď použivateľ nič nevyberie tak sa mu nezníži počet odobratých vygenerovaných ľudí.
                i--;
                return;
            }
            miestnost.pridajCloveka();
        }
        Random random = new Random();
        this.maxTime = random.nextInt(300) + 30;
        super.setOdpocitavanie(this.maxTime);
        super.setStavMiestnosti(StavMiestnosti.Pracuje);
    }

    @Override
    public String toString() {
        return "Ubytovanie";
    }

    @Override
    public void setSuradnice (int riadok, int stlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
    }

}

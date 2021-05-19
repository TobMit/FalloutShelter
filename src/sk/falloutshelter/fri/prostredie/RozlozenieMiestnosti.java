package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.prostredie.miestnosti.BuilderMiestnost;
import sk.falloutshelter.fri.prostredie.miestnosti.Elektraren;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.VyplnaciaMiestnost;
import sk.falloutshelter.fri.prostredie.miestnosti.Ubytovanie;
import sk.falloutshelter.fri.prostredie.miestnosti.Vchod;
import sk.falloutshelter.fri.prostredie.miestnosti.Vytah;
import sk.falloutshelter.fri.screan.StavObrazovky;

import javax.swing.*;
import java.awt.*;

/**
 *Táto tireda má nastorosť vytváranie usporiadanie miestnosti a informovať miestnosti o počte ludí v nej
 */
public class RozlozenieMiestnosti {
    //todo Pridať každej miestnosti svoj vhlad a grafiku
    //todo Načitavanie miestnostií zo súboru
    public static final int SIRKA_MIESTNOSTI = 140;
    public static final int VYSKA_MIESTNOSTI = 90;
    private final Miestnosti[][] miestnosti;
    private final Hra hra;
    private Miestnosti miestnostNaPostavenie = null;

    //                                                prvý rad:      2, 1(vyťah ale ten môže byť kdekoľvek) 3, 3
    // Očakávané rozloženie všetkých miestností okrem prvého radu je 3, 1(vyťáh ale ten môže byť kdekoľvek) 3, 3  (čísla je dlžka miestnosti)
    // veľkosť bunkra je 11 (vyska) x 10
    public RozlozenieMiestnosti(Hra hra) {
        this.hra = hra;
        this.miestnosti = new Miestnosti[11][10];

        for (int i = 0; i < this.miestnosti.length; i++) {
            for (int j = 0; j < this.miestnosti[i].length; j++) {
                this.miestnosti[i][j] = new BuilderMiestnost(i, j, this);
            }
        }

        this.miestnosti[0][0] = new VyplnaciaMiestnost(0 , 0, this);
        this.miestnosti[0][1] = new Vchod(0, 1, this);
        this.miestnosti[0][2] = this.miestnosti[0][1];
        this.miestnosti[0][4] = new Ubytovanie(0, 4, this);

        this.miestnosti[0][3] = new Vytah(0, 3, this);// výťah na prvom poschodí
        this.miestnosti[1][3] = new Vytah(1, 3, this);
        this.miestnosti[2][3] = new Vytah(2, 3, this);

        this.miestnosti[1][2] = new Elektraren(1, 2, this);
    }

    public void nacitajMiestnostiZoSuboru() {
    }

    public void ulozMiestnostiDoSuboru() {

    }

    public void zobrazMiestnosti(Graphics grafika) {
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                if (miestnost != null) {
                    miestnost.zobraz(grafika);
                }

                // keď Builder miestnosti už viac niesu v staviteľskom režime schovajú sa.
                if (this.hra.getStavObrazokvy() == StavObrazovky.HraBezi && miestnost instanceof BuilderMiestnost) {
                    miestnost.jeVidetelne(false);
                }

            }
        }
    }

    public void vyberoveMenu() {
        //todo môže vrátiť aj null treba to ošetriť

        //Kým beží táto metóda. Hra čaká.
        ImageIcon icon = new ImageIcon("src/sk/falloutshelter/fri/obr/build-ico.png");
        //todo aby bola financií vrátilo zoznam ktorý si môže daný človek kúpiť
        Miestnosti[] zoznamMiestnosti = {new Vytah(0, 0, this), new Elektraren(0, 0, this), new Ubytovanie(0 , 0, this)};
        this.miestnostNaPostavenie = (Miestnosti)JOptionPane.showInputDialog (
                null,
                "Vyber miestnosť kotru chceš postaviť: ",
                "Postavenie miestnosti",
                JOptionPane.WARNING_MESSAGE,
                icon,
                zoznamMiestnosti,
                zoznamMiestnosti[0]);
        this.hra.setStavObrazokvy(StavObrazovky.Stavanie);
        this.zobrazMoznostiStavania(this.miestnostNaPostavenie);
        System.out.println(this.miestnostNaPostavenie);

    }

    /**
     * Tato metoda zobrazi možnosti stavnia na základe toho aká miestnosť je vybratá.
     * @param vyberoveMenu - miestnosť ktorú chceme postaviť
     */
    private void zobrazMoznostiStavania(Miestnosti vyberoveMenu) {
        for (int i = 0; i < this.miestnosti.length; i++) {
            for (int j = 0; j < this.miestnosti[i].length; j++) {
                if (!(this.miestnosti[i][j] instanceof BuilderMiestnost)) {
                    if (j > 0 && this.miestnosti[i][j - 1] instanceof BuilderMiestnost) {
                        this.miestnosti[i][j - 1].jeVidetelne(true);
                    }

                    if (j < this.miestnosti[i].length && this.miestnosti[i][j + 1] instanceof BuilderMiestnost) {
                        this.miestnosti[i][j + 1].jeVidetelne(true);
                    }

                    if (i > 0 && i < this.miestnosti.length - 1 && !(this.miestnosti[i - 1][j] instanceof BuilderMiestnost) && vyberoveMenu instanceof Vytah) {
                        this.miestnosti[i + 1][j].jeVidetelne(true);
                    }
                }
            }
        }
    }

    public void pridajMiestnosti(Miestnosti miestnost, int riadok, int stlpec) {
        //todo Pridať overovanie či sa dá na dané miesto pridať miestnosť alebo nie
        this.miestnosti[riadok][stlpec] = miestnost;
        this.miestnostNaPostavenie = null;
    }
}

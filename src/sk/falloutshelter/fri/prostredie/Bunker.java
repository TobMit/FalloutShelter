package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Táto trieda má na starosť vytvorenie zdrojov a vytvorenie miesta pre Miestnosti. Taktiež spája vykresľovanie a hlavnú slučku hry s miestnosťami preposiela: zobraz, tik, klik. Hlavnou úlohou sú zdroje, takže tie obsluhuje.
 * @author Tobias
 */
public class Bunker implements IZobraz, IKlik, ITik {
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final Zdroje zdroje;
    private boolean jeVidetelne;

    public static final int X_SURADNICA_BUNKRA = Hra.GAME_SIRKA / 3 - RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
    public static final int Y_SURADNICA_BUNKRA = Hra.GAME_VYSKA / 10 - RozlozenieMiestnosti.VYSKA_MIESTNOSTI  + 5;
    private int sekundaTik;


    public Bunker(Hra hra) {
        this.rozlozenieMiestnosti = new RozlozenieMiestnosti(hra, this);
        this.zdroje = new Zdroje(this.rozlozenieMiestnosti);
        this.zdroje.nacitajZrojeZoSuboru();
        this.rozlozenieMiestnosti.nacitajMiestnostiZoSuboru();
        this.sekundaTik = 0;

    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.jeVidetelne) {
            this.vykresliGrafiku(grafika);
            this.rozlozenieMiestnosti.zobrazMiestnosti(grafika);
            this.zdroje.jeVidetelne(true);
            this.zdroje.zobraz(grafika);
        }
    }

    /**
     * Metóda vykresli oblasti kde sa stavajú miestnosti.
     */
    private void vykresliGrafiku(Graphics grafika) {
        //grafika.setColor(Color.decode("#7a674d"));
        // hodnota a: 127 je 50% priehladnosť - čí menšia hodnota tým menšia priehladnosť
        grafika.setColor(new Color(122, 103, 77, 50));
        grafika.fillRect(Hra.GAME_SIRKA / 3, Hra.GAME_VYSKA / 10 - RozlozenieMiestnosti.VYSKA_MIESTNOSTI  + 5, 9 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
        grafika.fillRect(Hra.GAME_SIRKA / 3 - RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Hra.GAME_VYSKA / 10 + 5 , 10 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, 10 * RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
    }

    public Zdroje getZdroje() {
        return this.zdroje;
    }

    public RozlozenieMiestnosti getRozlozenieMiestnosti() {
        return this.rozlozenieMiestnosti;
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.jeVidetelne = viditelne;
    }

    /**
     * Iba prepošle klik ďalej.
     */
    @Override
    public void klik(int x, int y) {
        this.rozlozenieMiestnosti.klik(x, y);
    }

    /**
     * Podľa veľkosti bunkra odoberá zdroje.
     * @throws KoniecHryException koniec hry na málo zdrojov
     */
    @Override
    public void tik() throws KoniecHryException {
        this.sekundaTik++;
        this.rozlozenieMiestnosti.tik();
        if (this.sekundaTik > 60) {
            this.sekundaTik = 0;
            //                                  spotreba na ubytovanie                    vaultDor                  vytahy                                      jedalne                                             Vodarne
            int odberEnergie = (int)Math.floor(this.rozlozenieMiestnosti.getPocetUbytovania() * 0.5 + 1 + this.rozlozenieMiestnosti.getPocetVytahov() * 0.5 + this.rozlozenieMiestnosti.getPocetJedalni() * 1.25 + this.rozlozenieMiestnosti.getPocetVodarni() * 2);
            int odberVody = (int)Math.floor(this.rozlozenieMiestnosti.getPocetElektrari() * 0.25);
            this.zdroje.odoberZdroje(odberVody, odberEnergie);
        }
    }
}

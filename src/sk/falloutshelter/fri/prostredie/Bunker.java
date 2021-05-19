package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 * Bunker ma nastarosť vytvorenie triedy rozloženia miestností.
 * Jeho hlavnou úlohov sú ludia, drží počet ľudí v bunkry, počet vólnych miest bunkra,
 * Taktiež má nastarosť prenos ludí do miestností a túto informáciu predávať daným miestnostiam.
 * @author Tobias
 */
public class Bunker implements IZobraz, IKlik {
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final Statistiky statistiky;
    private boolean jeVidetelne;

    public static final int X_SURADNICA_BUNKRA = Hra.GAME_SIRKA / 3 - RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
    public static final int Y_SURADNICA_BUNKRA = Hra.GAME_VYSKA / 10 - RozlozenieMiestnosti.VYSKA_MIESTNOSTI  + 5;

//    private static final int

    public RozlozenieMiestnosti getRozlozenieMiestnosti() {
        return this.rozlozenieMiestnosti;
    }

    //TODO Pridať a sfunkčniť ludí.
    public Bunker(Hra hra) {
        this.rozlozenieMiestnosti = new RozlozenieMiestnosti(hra);
        this.rozlozenieMiestnosti.nacitajMiestnostiZoSuboru();
        this.statistiky = new Statistiky(this.rozlozenieMiestnosti);

    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.jeVidetelne) {
            this.vykresliGrafiku(grafika);
            this.rozlozenieMiestnosti.zobrazMiestnosti(grafika);
            this.statistiky.jeVidetelne(true);
            this.statistiky.zobraz(grafika);
        }
    }

    private void vykresliGrafiku(Graphics grafika) {
        //grafika.setColor(Color.decode("#7a674d"));
        // hodnota a: 127 je 50% priehladnosť - čí menšia hodnota tým menšia priehladnosť
        grafika.setColor(new Color(122, 103, 77, 50));
        grafika.fillRect(Hra.GAME_SIRKA / 3, Hra.GAME_VYSKA / 10 - RozlozenieMiestnosti.VYSKA_MIESTNOSTI  + 5, 9 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
        grafika.fillRect(Hra.GAME_SIRKA / 3 - RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Hra.GAME_VYSKA / 10 + 5 , 10 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, 10 * RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.jeVidetelne = viditelne;
    }

    /**
     * iba prepošle klik ďalej
     */
    @Override
    public void klik(int x, int y) {
        this.rozlozenieMiestnosti.klik(x, y);
    }
}

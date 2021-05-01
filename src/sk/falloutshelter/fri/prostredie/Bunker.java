package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.grafika.IZobraz;

import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 * Bunker ma nastarosť vytvorenie triedy rozloženia miestností.
 * Jeho hlavnou úlohov sú ludia, drží počet ľudí v bunkry, počet vólnych miest bunkra,
 * Taktiež má nastarosť prenos ludí do miestností a túto informáciu predávať daným miestnostiam.
 * @author Tobias
 */
public class Bunker implements IZobraz {
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private boolean jeVidetelne;

//    private static final int

    //TODO Pridať a sfunkčniť ludí.
    public Bunker() {
        this.rozlozenieMiestnosti = new RozlozenieMiestnosti();
        this.rozlozenieMiestnosti.nacitajMiestnosti();

    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.jeVidetelne) {
            this.vykresliGrafiku(grafika);
        }
    }

    private void vykresliGrafiku(Graphics grafika) {
        grafika.setColor(Color.decode("#7a674d"));
        grafika.fillRect(Hra.GAME_SIRKA / 3, Hra.GAME_VYSKA / 10 - RozlozenieMiestnosti.VYSKA_MIESTNOSTI , 9 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI + 10);
        grafika.fillRect(Hra.GAME_SIRKA / 3 - RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Hra.GAME_VYSKA / 10 + 10 , 10 * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, 10 * RozlozenieMiestnosti.VYSKA_MIESTNOSTI);

    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.jeVidetelne = viditelne;
    }
}

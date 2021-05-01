package sk.falloutshelter.fri.prostredie;

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

    //TODO Pridať a sfunkčniť ludí.
    public Bunker() {
        this.rozlozenieMiestnosti = new RozlozenieMiestnosti();

    }

    @Override
    public void zobraz(Graphics grafika) {

    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }
}

package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.*;

/**
 * Táto trieda je ready to use pre ostané vykreslovanie či miestností alebo niečoho iného....
 *
 * @author Tobias
 */
public class Pozadie implements IZobraz {
    //todo Pridať obrázok pozadia
    private boolean jeViditelne;

    public Pozadie() {

    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.jeViditelne) {
            this.vykreslovacPozadia(grafika);
        }

    }

    private void vykreslovacPozadia(Graphics grafika) {
        grafika.setColor(Color.decode("#AE8F65"));
        grafika.fillRect(Hra.GAME_SIRKA / 3 - 10 , 0, Hra.GAME_SIRKA, Hra.GAME_VYSKA);
        grafika.fillRect(0 , Hra.GAME_VYSKA / 10, Hra.GAME_SIRKA, Hra.GAME_VYSKA);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

        this.jeViditelne = viditelne;
    }
}

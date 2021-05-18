package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;

/**
 * Táto miestnosť slúži ako podborná miestnosť ktorá ukažé kde sa môže stavať nová miestnosť. Vlastne je to taký zobrazovač ikonky.
 *
 * @author Tobias
 */
public class BuilderMiestnost extends Miestnosti {
    private final int riadok;
    private final int stlpec;
    private final Image image;
    private boolean videtelne = false;

    public BuilderMiestnost(int riadok, int stlpec) {
        super(riadok, stlpec);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();
    }

    @Override
    public void klik(int x, int y) {
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.videtelne) {
            grafika.drawImage(this.image, Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, null);
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.videtelne = viditelne;
    }
}

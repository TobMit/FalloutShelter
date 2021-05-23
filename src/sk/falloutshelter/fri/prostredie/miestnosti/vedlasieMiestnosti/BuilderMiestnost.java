package sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;

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
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final Image image;
    private final int xSuradnica;
    private final int ySuradnica;
    private boolean videtelne = false;

    public BuilderMiestnost(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();
    }

    @Override
    public void klik(int x, int y) {
        if (!this.videtelne) {
            return;
        }

        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + this.image.getWidth(null) && y < this.ySuradnica + this.image.getHeight(null)) {
            this.rozlozenieMiestnosti.pridajMiestnosti(this.riadok, this.stlpec);
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.videtelne) {
            grafika.drawImage(this.image, this.xSuradnica, this.ySuradnica, null);
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.videtelne = viditelne;
    }

    @Override
    public String toString() {
        return "BuilderRoom";
    }
}

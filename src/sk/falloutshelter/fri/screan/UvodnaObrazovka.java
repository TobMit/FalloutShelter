package sk.falloutshelter.fri.screan;

import sk.falloutshelter.fri.Hra;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Image;
/**
 * Táto trieda zobrazí úvodnú obrazovku a počká na kliknutie a spustí bunker.
 */
public class UvodnaObrazovka implements IZobraz, IKlik {
    private final Hra hra;
    private boolean zobrazene;
    private final Image image;

    public UvodnaObrazovka(Hra hra) {
        this.hra = hra;
        this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/boot screan.png").getImage();
    }

    @Override
    public void zobraz(Graphics grafika) {
        if (this.zobrazene) {
            grafika.drawImage(this.image, 0, 0, null);
        }

    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.zobrazene = viditelne;
    }

    /**
     * Reaguje na tlačidlo „ENTER WAULT 777“
     */
    @Override
    public void klik(int x, int y) {
        if (this.zobrazene) {
            if (x >= 753 && y >= 328 && x <= 1260 && y <= 909) {
                this.hra.prepinacObrazoviek();
            }
        }
    }
}

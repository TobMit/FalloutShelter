package sk.falloutshelter.fri.screan;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Trieda vykreslí vybratie miestnosti. Veľkosť „selektového obrázka“ sa zadáva v konštruktore podľa veľkosti triedy.
 */
public class GrafikaSelect {
    public GrafikaSelect(Graphics grafika, int xSuradnica, int ySuradnica, int velkostMiestnosti) {
        Image image;
        switch (velkostMiestnosti) {
            case 1:
                image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/select/Select1.png").getImage();
                break;
            case 2:
                image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/select/Select2.png").getImage();
                break;
            case 3:
                image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/select/Select3.png").getImage();
                break;
            default:
                return;
        }
        grafika.drawImage(image, xSuradnica, ySuradnica, null);
    }
}

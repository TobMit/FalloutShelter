package sk.falloutshelter.fri.screan;

import javax.swing.*;
import java.awt.*;

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

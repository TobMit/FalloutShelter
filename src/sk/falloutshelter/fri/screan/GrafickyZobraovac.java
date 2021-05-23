package sk.falloutshelter.fri.screan;

import javax.swing.*;
import java.awt.*;

/**
 * Trieda vykreslí miestnosti podľa veľkosti. Všetky informácie za udávajú do konštruktora.
 */
public class GrafickyZobraovac {
    public GrafickyZobraovac(String level1, String level2, String level3, Graphics grafika, int velkostMiestnosti, int xSuradnica, int ySuradnica) {
        Image image;
        switch (velkostMiestnosti) {
            case 1:
                image = new ImageIcon(level1).getImage();
                break;
            case 2:
                image = new ImageIcon(level2).getImage();
                break;
            case 3:
                image = new ImageIcon(level3).getImage();
                break;
            default:
                return;
        }
        grafika.drawImage(image, xSuradnica, ySuradnica, null);
    }
}

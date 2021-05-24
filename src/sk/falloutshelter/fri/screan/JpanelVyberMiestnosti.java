package sk.falloutshelter.fri.screan;


import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

/**
 * Trieda má na starosť vykresliť a zobraziť výberové menu. Všetky položky aj obrázky sa je dávajú v parametre konštruktora.
 */
public class JpanelVyberMiestnosti {

    public static Miestnosti zobrazVyberoveMenu(Miestnosti[] zoznamMiestnosti, ImageIcon icon) {
        return (Miestnosti)JOptionPane.showInputDialog (
                null,
                "Vyber miestnosť kotru chceš postaviť: ",
                "Postavenie miestnosti",
                JOptionPane.WARNING_MESSAGE,
                icon,
                zoznamMiestnosti,
                zoznamMiestnosti[0]);
    }
}

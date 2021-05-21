package sk.falloutshelter.fri.screan;


import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;

import javax.swing.*;

/**
 *má na staroť zobrazovať vyberové menu
 */
public class JpanelVyberMiestnosti {

    public static Miestnosti zobrazVyberoveMenu(Miestnosti[] zoznamMiestnosti, ImageIcon icon) {
        return (Miestnosti) JOptionPane.showInputDialog (
                null,
                "Vyber miestnosť kotru chceš postaviť: ",
                "Postavenie miestnosti",
                JOptionPane.WARNING_MESSAGE,
                icon,
                zoznamMiestnosti,
                zoznamMiestnosti[0]);
    }
}

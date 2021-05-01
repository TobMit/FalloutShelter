package sk.falloutshelter.fri;

import javax.swing.*;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Hra {

    private final Pozadie pozadie;
    private final JFrame jframe;

    public Hra() {
        this.pozadie = new Pozadie();
        this.pozadie.zobraz();
        this.jframe = new JFrame();
        this.jframe.setSize(1920, 1080);
        this.jframe.setVisible(true);
    }

    public void hraj() {
    }
}

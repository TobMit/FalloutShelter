package sk.falloutshelter.fri.grafika;

import sk.falloutshelter.fri.Hra;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {
    private final Hra hra;

    public Render(Hra hra) {
        super();
        this.hra = hra;
    }

    @Override
    protected void paintComponent(Graphics grafika) {
        super.paintComponent(grafika);

        this.hra.repaint(grafika);
        this.setBackground(Color.CYAN);
    }
}

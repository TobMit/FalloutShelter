package sk.falloutshelter.fri;

import javax.swing.*;
import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Render extends JPanel {
    private final Hra hra;

    public Render(Hra hra) {
        super();
        this.hra = hra;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.hra.repaint(g);
        this.setBackground(Color.CYAN);
    }
}

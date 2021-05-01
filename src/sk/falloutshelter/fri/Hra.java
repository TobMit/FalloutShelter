package sk.falloutshelter.fri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Hra implements ActionListener {

    private final Pozadie pozadie;
    private final JFrame jframe;
    private final Render render;
    private final Timer casovac;

    public static final int GAME_SIRKA = 1920;
    public static final int GAME_VYSKA = 1080;

    private int tik;

    public Hra() {
        this.jframe = new JFrame();
        this.casovac = new Timer(20, this);

        this.render = new Render(this);
        this.vytvorenieOkna();

        this.pozadie = new Pozadie();
        this.pozadie.jeVidetelne(true);
        this.tik = 0;


    }

    private void vytvorenieOkna() {
        this.jframe.add(this.render);
        this.jframe.setDefaultCloseOperation(this.jframe.EXIT_ON_CLOSE);
        this.jframe.setSize(Hra.GAME_SIRKA, Hra.GAME_VYSKA);
        this.jframe.setResizable(false);
        this.jframe.setTitle("Fallout Shelter");
        this.jframe.setVisible(true);
        this.casovac.start();
    }

    public void repaint(Graphics grafika) {

        this.pozadie.zobraz(grafika);
    }

    public void hraj() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.tik++;
        this.render.repaint();
    }
}

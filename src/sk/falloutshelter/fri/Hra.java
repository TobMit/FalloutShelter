package sk.falloutshelter.fri;

import sk.falloutshelter.fri.screan.StavObrazovky;
import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.Pozadie;
import sk.falloutshelter.fri.screan.UvodnaObrazovka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Hra implements ActionListener {

    //TODO úvodu obrazovku a načitavanie zo súboru na výber budú 3 save a viac nie, potom má hráč smolu


    private final Pozadie pozadie;
    private final JFrame jframe;
    private final JPanel render;
    private final Timer casovac;

    public static final int GAME_SIRKA = 1920;
    public static final int GAME_VYSKA = 1080;
    private final Bunker bunker;
    private final StavObrazovky stavObrazokvy;

    //tik je pre animáciu
    private int tik;
    private UvodnaObrazovka uvodnaObraovka;

    public Hra() {
        this.jframe = new JFrame();

        this.render = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Hra.this.repaint(g);
                this.setBackground(Color.CYAN);

            }
        };

        this.stavObrazokvy = StavObrazovky.UvodnaObrazovka;

        this.uvodnaObraovka = new UvodnaObrazovka();

        this.vytvorenieOkna();

        this.pozadie = new Pozadie();
        this.pozadie.jeVidetelne(false);

        this.bunker = new Bunker();
        this.bunker.jeVidetelne(false);

        // delay: 16 pretože je to približne 60 FPS
        this.casovac = new Timer(16, this);
        this.tik = 0;


    }

    private void vytvorenieOkna() {
        this.jframe.add(this.render);
        this.jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jframe.setSize(Hra.GAME_SIRKA, Hra.GAME_VYSKA);
        this.jframe.setResizable(false);
        this.jframe.setTitle("Fallout Shelter");
        this.jframe.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, Hra.GAME_SIRKA, Hra.GAME_VYSKA);
        this.jframe.add(panel);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(e.getX() + " " + e.getY());
            }
        });
    }

    public void repaint(Graphics grafika) {

        if (this.stavObrazokvy == StavObrazovky.UvodnaObrazovka) {
            this.uvodnaObraovka.jeVidetelne(true);
            this.uvodnaObraovka.zobraz(grafika);
        } else {
            /*
             * Odstranenie chyby Bunker = null. Niekedy sa repaint volalo skôr ako sa stihol bunker vôbec vytvoriť tak to pri prvom spustení hádzalo chybu.
             */
            if (this.tik != 0) {
                this.pozadie.zobraz(grafika);
                this.bunker.zobraz(grafika);
            }
            this.tik++;

            if (this.tik > 60) {
                this.tik = 1;
            }
        }


    }

    public void hraj() {
        this.casovac.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //this.tik++;
        this.render.repaint();

    }
}

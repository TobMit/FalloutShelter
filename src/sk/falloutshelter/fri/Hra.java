package sk.falloutshelter.fri;

import sk.falloutshelter.fri.prostredie.KoniecHryException;
import sk.falloutshelter.fri.screan.StavObrazovky;
import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.Pozadie;
import sk.falloutshelter.fri.screan.Tlacitka;
import sk.falloutshelter.fri.screan.UvodnaObrazovka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 *
 * @author Tobias
 */
public class Hra {
private final Pozadie pozadie;
    private final JFrame jframe;
    private final JPanel render;

    public static final int GAME_SIRKA = 1920;
    public static final int GAME_VYSKA = 1080;
    private final Bunker bunker;
    private final Tlacitka builderButton;
    private StavObrazovky stavObrazokvy;

    //tik je pre animáciu
    private int tik;
    private final UvodnaObrazovka uvodnaObraovka;

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

        this.uvodnaObraovka = new UvodnaObrazovka(this);

        this.vytvorenieOkna();

        this.pozadie = new Pozadie();
        this.pozadie.jeVidetelne(false);

        this.bunker = new Bunker(this);
        this.bunker.jeVidetelne(false);

        this.builderButton = new Tlacitka(10, 925, this.bunker.getRozlozenieMiestnosti(), "src/sk/falloutshelter/fri/obr/build-ico.png");
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
                Hra.this.klik(e.getX(), e.getY());
            }
        });
    }

    public void prepinacObrazoviek() {
        this.stavObrazokvy = StavObrazovky.HraBezi;
        this.uvodnaObraovka.jeVidetelne(false);
        this.pozadie.jeVidetelne(true);
        this.bunker.jeVidetelne(true);
        this.builderButton.jeVidetelne(true);
    }
    private void klik(int x, int y) {
        this.uvodnaObraovka.klik(x, y);
        this.builderButton.klik(x, y);
        this.bunker.klik(x, y);
    }

    public void repaint(Graphics grafika) {

        if (this.stavObrazokvy == StavObrazovky.UvodnaObrazovka) {
            this.uvodnaObraovka.jeVidetelne(true);
            this.uvodnaObraovka.zobraz(grafika);
        } else {
            /*
             * Odstranenie chyby Bunker = null. Niekedy sa repaint volalo skôr ako sa stihol bunker vôbec vytvoriť tak to pri prvom spustení hádzalo chybu.
             */
            this.pozadie.zobraz(grafika);
            this.bunker.zobraz(grafika);
            this.builderButton.zobraz(grafika);
            this.tik++;

            if (this.tik > 60) {
                this.tik = 0;
                try {
                    this.bunker.tik();
                } catch (KoniecHryException e) {
                    JOptionPane.showMessageDialog(null, "Ludia zomreli od hadlu a smedu. Koniec hry.", "Koniec hry.", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }

            }

        }




    }

    public StavObrazovky getStavObrazokvy() {
        return this.stavObrazokvy;
    }

    public void setStavObrazokvy(StavObrazovky stavObrazokvy) {
        this.stavObrazokvy = stavObrazokvy;
    }

    /**
     * Hlavná slučka. Udržuje hru aby prebehlo 60 tikou za sekundu.
     */
    public void hraj() {
        long poslednyCasCyklu = System.nanoTime();
        long poslenyCysVystupu = System.currentTimeMillis();
        double nespracovaneTiky = 0;
        double perTik = Math.pow(10, 9) / 60;
        int tick = 0;
        int fps = 0;
        while (true) {
            long aktualnyCas = System.nanoTime();
            nespracovaneTiky += (aktualnyCas - poslednyCasCyklu) / perTik;
            poslednyCasCyklu = aktualnyCas;

            while (nespracovaneTiky >= 1) {
                this.render.repaint();
                tick++;
                nespracovaneTiky--;
            }

            fps++;

            if (System.currentTimeMillis() - poslenyCysVystupu > 1000) {
                poslenyCysVystupu += 1000;
                //System.out.printf("Tik: %d FPS: %d\n", tik, fps);
                fps = 0;
                tick = 0;
            }
        }
    }

}

package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Statistiky implements IZobraz {
    private final int jedlo;
    private final int voda;
    private final int energia;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final int caps;
    private final int ludia;
    private boolean viditelne;

    public Statistiky(RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.viditelne = false;
        this.voda = 0;
        this.energia = 0;
        this.jedlo = 0;
        this.caps = 0;
        this.ludia = 0;
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.decode("#18f817"));
        grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

        int maxLudi = 2 * ((this.rozlozenieMiestnosti.getPocetUbytovania() * 3) / 3) * 5 - 2;
        String ludiaString = "Ludia: " + this.ludia+ "/" + maxLudi;
        grafika.drawString(ludiaString, 45, 300);

        int maxEnergia = 25 * this.rozlozenieMiestnosti.getPocetElektrari() * 2;
        String energiaString = "Energia: " + this.energia + "/" + maxEnergia;
        grafika.drawString(energiaString, 45, 396);

        int maxVoda = 25 * this.rozlozenieMiestnosti.getPocetVodarni() * 2;
        String vodaaString = "Voda: " + this.voda + "/" + maxVoda;
        grafika.drawString(vodaaString, 45, 446);

        int maxJedlo = 25 * this.rozlozenieMiestnosti.getPocetJedalni() * 2;
        String jedloString = "Jedlo: " + this.jedlo + "/" + maxJedlo;
        grafika.drawString(jedloString, 45, 496);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.viditelne = viditelne;
    }
}

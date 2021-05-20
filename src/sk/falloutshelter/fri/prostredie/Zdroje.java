package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.*;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public class Zdroje implements IZobraz {
    private int jedlo;
    private int voda;
    private int energia;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int caps;
    private final int ludia;
    private boolean viditelne;

    public Zdroje(RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.viditelne = false;
        this.voda = 0;
        this.energia = 0;
        this.jedlo = 0;
        this.caps = 1000;
        this.ludia = 0;
    }

    public void odoberZdroje(int voda, int energia) {
        this.voda -= (voda + this.ludia);
        this.energia -= energia;
        this.jedlo -= this.ludia;
    }

    public void nakupuj(int cena) {
        this.caps -= cena;
    }

    public boolean mozemKupit(int cena) {
        return this.caps >= cena;
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.decode("#18f817"));
        grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

        int maxLudi = 2 * ((this.rozlozenieMiestnosti.getPocetUbytovania() * 3) / 3) * 5 - 2;
        String ludiaString = "Ludia: " + this.ludia + "/" + maxLudi;
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

        String capsString = "Caps: " + this.caps;
        grafika.drawString(capsString, 45, 546);
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.viditelne = viditelne;
    }
}

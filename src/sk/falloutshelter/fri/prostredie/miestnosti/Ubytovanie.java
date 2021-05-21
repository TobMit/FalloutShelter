package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.Bunker;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class Ubytovanie extends Miestnosti {


    private final int riadok;
    private final int stlpec;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private final int xSuradnica;
    private final int ySuradnica;
    private boolean zobrazInfo;
    private StavMiestnosti stavMiestnosti;
    private final int sirkaMiestnosti;
    private int odpocitavanie;
    private int maxTime;

    public Ubytovanie(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        super(riadok, stlpec, rozlozenieMiestnosti);
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.xSuradnica = Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI;
        this.ySuradnica = Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI;
        //this.image = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/builderMiestnost/builderMiestnost-1.png").getImage();

        super.stavMiestnosti = StavMiestnosti.Pracuje;
        Random random = new Random();
        super.odpocitavanie = random.nextInt(300) + 30;
        this.sirkaMiestnosti = 1;
        this.zobrazInfo = false;

        if (!(riadok == 0 && stlpec == 0)) {
            this.rozlozenieMiestnosti.noveUbytovanie();
        }
    }

    @Override
    public void zobraz(Graphics grafika) {
        grafika.setColor(Color.red);
        grafika.fillRect(Bunker.X_SURADNICA_BUNKRA + this.stlpec * RozlozenieMiestnosti.SIRKA_MIESTNOSTI, Bunker.Y_SURADNICA_BUNKRA + this.riadok * RozlozenieMiestnosti.VYSKA_MIESTNOSTI, RozlozenieMiestnosti.SIRKA_MIESTNOSTI, RozlozenieMiestnosti.VYSKA_MIESTNOSTI);
        if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
            //todo dorobiť grafiku
            //Image spracovaneImag = new ImageIcon("src/sk/falloutshelter/fri/obr/Miestnosti/vodaren/dogenerovanaVodaren-1.png").getImage();
            //grafika.drawImage(spracovaneImag, this.xSuradnica, this.ySuradnica, null);
        }

        if (this.zobrazInfo) {
            grafika.setColor(Color.decode("#18f817"));
            grafika.setFont(new Font("TimesRoman", Font.PLAIN, 45));

            String casDoKonca = "Cas dokoncenia: " + super.odpocitavanie;
            grafika.drawString(casDoKonca, 45, 646);
        } else {
            this.zobrazInfo = false;
        }
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }

    @Override
    public void klik(int x, int y) {
        if (x > this.xSuradnica && y > this.ySuradnica && x < this.xSuradnica + RozlozenieMiestnosti.SIRKA_MIESTNOSTI * this.sirkaMiestnosti && y < this.ySuradnica + RozlozenieMiestnosti.VYSKA_MIESTNOSTI) {
            if (super.stavMiestnosti == StavMiestnosti.Pracuje) {
                this.zobrazInfo = true;
            } else if (super.stavMiestnosti == StavMiestnosti.Spracovane) {
                this.reWork();
            }
        } else {
            this.zobrazInfo = false;
        }

    }

    private void reWork() {
        //todo dokončiť pridavanie ľudí
        //this.rozlozenieMiestnosti.getBunker().getZdroje().pridajVodu((2 * this.sirkaMiestnosti * 5) - 2);
        Random random = new Random();
        this.maxTime = random.nextInt(300) + 30;
        super.odpocitavanie = this.maxTime;
        super.stavMiestnosti = StavMiestnosti.Pracuje;
    }

    @Override
    public String toString() {
        return "Ubytovanie";
    }
}

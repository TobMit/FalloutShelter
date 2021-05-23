package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.ITik;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;


/**
 * Hlavná trieda miestnosti. Všetky ostatné miestnosti sú jej potomkovia má na starosti niektoré spoločné úlohy ale väčšina je prepísaná priamo v triedach.
 * @author Tobias
 */
public abstract class Miestnosti implements IZobraz, IKlik, ITik {
    private final int riadok;
    private final int stlpec;
    private int tiki;
    private StavMiestnosti stavMiestnosti;
    private int odpocitavanie;
    private int pocetLudi;
    private int velkostMiestnosti = 1;

    public Miestnosti(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.tiki = 0;
    }
    public void pridajCloveka() {
    }

    /**
     * Jedna z mála úloh o ktoré sa stará sama. Má na starosť odpočet času dokončenia miestnosti. Ak to stav miestnosti dovoluje.
     */
    public void tik() {
        this.tiki++;
        if (this.tiki >= this.velkostMiestnosti) {
            if (this.odpocitavanie <= 0 && (this.stavMiestnosti == StavMiestnosti.Pracuje)) {
                this.stavMiestnosti = StavMiestnosti.Spracovane;
            }

            if (this.stavMiestnosti == StavMiestnosti.Spracovane) {
                this.odpocitavanie = 0;
                return;
            }

            if (this.stavMiestnosti != StavMiestnosti.NemaEnergiu) {
                if (this.odpocitavanie > 0) {
                    this.odpocitavanie--;
                }
            }
            this.tiki = 0;
        }
    }

    public boolean volneMiestoNaLudi() {
        return this.pocetLudi < 2 * this.velkostMiestnosti;
    }

    @Override
    public void jeVidetelne(boolean viditelne) {

    }

    @Override
    public String toString() {
        return "Global Miestnost - Musela nastať niekde chyba, pretože toto sa nemalo nikdy zobraziť. XD";
    }

    public int getVelkostMiesnosti() {
        return this.velkostMiestnosti;
    }

    public void zvetsiMiestnost() {
        this.velkostMiestnosti++;
    }

    public void setSuradnice(int riadok, int stlpec) {

    }

    protected StavMiestnosti getStavMiestnosti() {
        return this.stavMiestnosti;
    }

    protected void setStavMiestnosti(StavMiestnosti stavMiestnosti) {
        this.stavMiestnosti = stavMiestnosti;
    }

    protected int getOdpocitavanie() {
        return this.odpocitavanie;
    }

    protected void setOdpocitavanie(int odpocitavanie) {
        this.odpocitavanie = odpocitavanie;
    }

    public int getPocetLudi() {
        return this.pocetLudi;
    }

    protected void setPocetLudi(int pocetLudi) {
        this.pocetLudi = pocetLudi;
    }

    protected int getVelkostMiestnosti() {
        return this.velkostMiestnosti;
    }

    protected void setVelkostMiestnosti(int velkostMiestnosti) {
        this.velkostMiestnosti = velkostMiestnosti;
    }

    /**
     * identifikátor pre ukladanie
     */
    public int toStringInentifikator() {
        //nul
        return 0x6e756c;
    }
}

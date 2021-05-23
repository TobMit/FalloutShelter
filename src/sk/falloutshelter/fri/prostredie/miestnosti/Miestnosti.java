package sk.falloutshelter.fri.prostredie.miestnosti;

import sk.falloutshelter.fri.prostredie.ITik;
import sk.falloutshelter.fri.prostredie.RozlozenieMiestnosti;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.IZobraz;


/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public abstract class Miestnosti implements IZobraz, IKlik, ITik {
    //todo korekcia pre tik keď je väčšia miestnosť
    private final int riadok;
    private final int stlpec;
    private StavMiestnosti stavMiestnosti;
    private int odpocitavanie;
    private int pocetLudi;
    private int velkostMiestnosti = 1;

    public Miestnosti(int riadok, int stlpec, RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.riadok = riadok;
        this.stlpec = stlpec;
    }
    public void pridajCloveka() {
    }
    
    public void tik() {

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

    protected int getPocetLudi() {
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

}

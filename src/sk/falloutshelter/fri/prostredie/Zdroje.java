package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.screan.IZobraz;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Trieda obsluhujúca zdroje.
 * @author Tobias
 */
public class Zdroje implements IZobraz {
    private int jedlo;
    private int voda;
    private int energia;
    private final RozlozenieMiestnosti rozlozenieMiestnosti;
    private int caps;
    private int ludia;
    private boolean viditelne;

    public Zdroje(RozlozenieMiestnosti rozlozenieMiestnosti) {
        this.rozlozenieMiestnosti = rozlozenieMiestnosti;
        this.viditelne = false;
        this.voda = 0;
        this.energia = 0;
        this.jedlo = 0;
        this.caps = 0;
        this.ludia = 0;
    }

    /**
     * Metóda inteligentne odoberá zdroje. Keď je dôležitých zdrojov málo tak vyhodí exception a hra končí.
     * @param voda spotreba vody
     * @param energia spotreba energie
     * @throws KoniecHryException keď je vody a jedla málo hra končí
     */
    public void odoberZdroje(int voda, int energia) throws KoniecHryException {
        this.voda -= (voda + this.ludia);
        this.energia -= energia;
        this.jedlo -= this.ludia;

        if (this.jedlo < 0 || this.voda < 0) {
            throw new KoniecHryException("ľudia zomreli od hladu a smedu");
        }
        this.ulozDoSuboru();

    }

    public int getEnergia() {
        return this.energia;
    }

    public void nakupuj(int cena) {
        this.caps -= cena;
        this.ulozDoSuboru();
    }

    public boolean mozemKupit(int cena) {
        return this.caps >= cena;
    }

    public void pridajVodu(int mnozstvo) {
        this.voda += mnozstvo;
        this.ulozDoSuboru();
    }

    public void pridajJedlo(int mnozstvo) {
        this.jedlo += mnozstvo;
        this.ulozDoSuboru();
    }

    public void pridajEnergie(int mnozstvo) {
        this.energia += mnozstvo;
        this.ulozDoSuboru();
    }

    public void pridajCaps(int mnozstvo) {
        this.caps += mnozstvo;
        this.ulozDoSuboru();
    }

    /**
     * Metóda vykreslí na obrazovku aktuálny stav zásob bunkru.
     */
    @Override
    public void zobraz(Graphics grafika) {
        if (this.viditelne) {
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
    }

    @Override
    public void jeVidetelne(boolean viditelne) {
        this.viditelne = viditelne;
    }

    public void pridajCloveka() {
        this.ludia++;
    }

    /**
     * Načítava zo svojho súboru informácie o zdrojoch.
     */
    public void nacitajZrojeZoSuboru() {
        File saveSubor = new File("src/sk/falloutshelter/fri/save/zdroje.fos");
        try (DataInputStream save = new DataInputStream(new FileInputStream(saveSubor))) {
            int subor = save.readInt();
            if (subor != 0x464f53) {
                return;
            }
            int znacka = save.readInt();
            if (znacka != 0x5a4452) {
                return;
            }
            this.energia = save.readInt();
            this.voda = save.readInt();
            this.jedlo = save.readInt();
            this.caps = save.readInt();

        } catch (FileNotFoundException e) {
            System.out.println("Nepodarilo sa nacitat save. Hra bola poškodená. Prosím reinstalujte hru.");
        } catch (IOException e) {
            System.out.println("Nepodarilo sa nacitat save.");
        }
    }

    /**
     * Ukladá informácie o zdrojoch do svojho súboru.
     */
    private void ulozDoSuboru() {
        File saveSubor = new File("src/sk/falloutshelter/fri/save/zdroje.fos");
        try (DataOutputStream save = new DataOutputStream(new FileOutputStream(saveSubor))) {
            // 0x464f53  -- v preklade FOS (ako názov hry)
            save.writeInt(0x464f53);
            // ďaľej je bezbečnostná informácie akotra oddeluje bunker a zdroje od seba. -> 0x5a4452   (ZDR - zdroje)
            save.writeInt(0x5a4452);
            save.writeInt(this.energia);
            save.writeInt(this.voda);
            save.writeInt(this.jedlo);
            save.writeInt(this.caps);
        } catch (FileNotFoundException e) {
            System.out.println("Nepodarilo sa ulozit hru. Hra bola poškodená. Prosím reinstalujte hru.");
        } catch (IOException e) {
            System.out.println("Nepodarilo sa ulozit save.");
            e.printStackTrace(System.out);
        }
    }
}

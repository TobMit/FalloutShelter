package sk.falloutshelter.fri.prostredie;

import sk.falloutshelter.fri.Hra;
import sk.falloutshelter.fri.prostredie.miestnosti.Elektraren;
import sk.falloutshelter.fri.prostredie.miestnosti.Jedalen;
import sk.falloutshelter.fri.prostredie.miestnosti.Miestnosti;
import sk.falloutshelter.fri.prostredie.miestnosti.Ubytovanie;
import sk.falloutshelter.fri.prostredie.miestnosti.Vodaren;
import sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti.BuilderMiestnost;
import sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti.Vchod;
import sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti.VyplnaciaMiestnost;
import sk.falloutshelter.fri.prostredie.miestnosti.vedlasieMiestnosti.Vytah;
import sk.falloutshelter.fri.screan.IKlik;
import sk.falloutshelter.fri.screan.JpanelVyberMiestnosti;
import sk.falloutshelter.fri.screan.StavObrazovky;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Najrozsiahlejšia a najdôležitejšie trieda hry. Má na starosti udržiavanie, vytváranie, ukladanie a načítavanie miestnosti v hre.
 * Rozosiela klik, tik a zobraz metódy. Ďalej má na starosť spájanie rovnakých budov do celkov. Výberové menu.
 * Zobrazenie podpory pre stavanie nových budov. Inými slovami všetko spojené s budovami.
 */
public class RozlozenieMiestnosti implements IKlik, ITik {
    public static final int SIRKA_MIESTNOSTI = 140;
    public static final int VYSKA_MIESTNOSTI = 90;
    private final Miestnosti[][] miestnosti;
    private final Hra hra;
    private final Bunker bunker;
    private boolean nakupovanie;
    private int pocetJedalni;
    private int pocetUbytovania;
    private int pocetVodarni;


    private int pocetElektrari;
    private Miestnosti miestnostNaPostavenie = null;
    private int pocetVytahov;

    //                                                prvý rad:      2, 1(vyťah ale ten môže byť kdekoľvek) 3, 3
    // Očakávané rozloženie všetkých miestností okrem prvého radu je 3, 1(vyťáh ale ten môže byť kdekoľvek) 3, 3  (čísla je dlžka miestnosti)
    // veľkosť bunkra je 11 (vyska) x 10
    public RozlozenieMiestnosti(Hra hra, Bunker bunker) {
        this.hra = hra;
        this.bunker = bunker;
        this.pocetUbytovania = 0;
        this.pocetElektrari = 0;
        this.pocetVodarni = 0;
        this.pocetJedalni = 0;
        this.pocetVytahov = 0;
        // this.nakupovanie uzamika nakupovanie a menu stavu keď sa načítava miestnoť zo súboru. Potom je už nakupovanie odomkntuté.
        this.nakupovanie = false;
        this.miestnosti = new Miestnosti[11][10];

        /*
         * Celá matica je naplnená pomocnou triedou BuilderMiestnost
         */
        for (int i = 0; i < this.miestnosti.length; i++) {
            for (int j = 0; j < this.miestnosti[i].length; j++) {
                this.miestnosti[i][j] = new BuilderMiestnost(i, j, this);
            }
        }
        // tieto miestnosti budú vždy
        this.miestnosti[0][0] = new VyplnaciaMiestnost(0 , 0, this);
        this.miestnosti[0][1] = new Vchod(0, 1, this);
        this.miestnosti[0][2] = this.miestnosti[0][1];
    }

    /**
     * Metóda načíta celu maticu zo súboru a postará sa o to aby každá Miestnosť mala správnu veľkosť a počet ľudí.
     */
    public void nacitajMiestnostiZoSuboru() {
        File saveSubor = new File("src/sk/falloutshelter/fri/save/bunker.fos");
        try (DataInputStream save = new DataInputStream(new FileInputStream(saveSubor))) {
            int subor = save.readInt();
            if (subor != 0x464f53) {
                return;
            }
            int znacka = save.readInt();
            if (znacka != 0x42554e) {
                return;
            }

            for (int i = 0; i < this.miestnosti.length; i++) {
                // velkostMiestnostiTemp - Keď v matici je miestnosť z väčšou veľkosťou ako 1 tak sa ukladajú na ňu iba referencie. To slúži na to aby sa zabránilo vytváranie malých miestnosti, slúži na spracovávanie referencií.
                int velkostMiestnostiTemp = 0;
                int pocetLudiTemp = 0;
                for (int j = 0; j < this.miestnosti[i].length; j++) {
                    //Spracuvávajú sa referencie na miestnosti. keďže by boli vždy tie istné údaje - referencie ukazujú na jednú miestnosť.
                    if (velkostMiestnostiTemp == 0) {
                        int sirkaMiestnosti = save.readInt();
                        int pocetLudi = save.readInt();
                        velkostMiestnostiTemp = sirkaMiestnosti;
                        pocetLudiTemp = pocetLudi;
                    }
                    int miestnost = save.readInt();
                    //vedlajsie miestnosti
                    if (miestnost == 0x6e756c) {
                        continue;
                    } else if (miestnost == 0x567974) {
                        //vyťah
                        this.miestnostNaPostavenie = new Vytah(0, 0, this);
                        this.pridajMiestnosti(i, j);
                        this.miestnostNaPostavenie = null;
                    } else {
                        velkostMiestnostiTemp--;
                        switch (miestnost) {
                            case 0x456c65:
                                this.miestnostNaPostavenie = new Elektraren( 0, 0, this);
                                this.pridajMiestnosti(i, j);
                                break;
                            case 0x556279:
                                this.miestnostNaPostavenie = new Ubytovanie( 0, 0, this);
                                this.pridajMiestnosti(i, j);
                                break;
                            case 0x4a6564:
                                this.miestnostNaPostavenie = new Jedalen( 0, 0, this);
                                this.pridajMiestnosti(i, j);
                                break;
                            case 0x566f64:
                                this.miestnostNaPostavenie = new Vodaren( 0, 0, this);
                                this.pridajMiestnosti(i, j);
                                break;
                        }
                        this.miestnostNaPostavenie = null;
                        if (velkostMiestnostiTemp == 0) {
                            //miestnosti sa dá potrebný počet ľudí
                            for (int k = 0; k < pocetLudiTemp; k++) {
                                this.miestnosti[i][j].pridajCloveka();
                            }
                            pocetLudiTemp = 0;
                        }
                    }
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Nepodarilo sa nacitat save. Hra bola poškodená. Prosím reinstalujte hru.");
        } catch (IOException e) {
            System.out.println("Nepodarilo sa nacitat save.");
        }
        this.nakupovanie = true;
    }

    /**
     * Ukladá hru do súboru.
     */
    public void ulozMiestnostiDoSuboru() {
        File saveSubor = new File("src/sk/falloutshelter/fri/save/bunker.fos");
        try (DataOutputStream save = new DataOutputStream(new FileOutputStream(saveSubor))) {
            // 0x464f53  -- v preklade FOS (ako názov hry)
            save.writeInt(0x464f53);
            // ďaľej je bezbečnostná informácie akotra oddeluje bunker a zdroje od seba. -> 0x42554e   (BUN - bunker)
            save.writeInt(0x42554e);

            for (int i = 0; i < this.miestnosti.length; i++) {
                int velkostMiestnostiTemp = 0;
                for (int j = 0; j < this.miestnosti[i].length; j++) {
                    if (this.miestnosti[i][j] instanceof VyplnaciaMiestnost || this.miestnosti[i][j] instanceof Vchod || this.miestnosti[i][j] instanceof BuilderMiestnost) {
                        //sirka miestnosti
                        save.writeInt(0);
                        // pocet ludi
                        save.writeInt(0);
                        save.writeInt(this.miestnosti[i][j].toStringInentifikator());
                        continue;
                    } else if (this.miestnosti[i][j] instanceof Vytah) {
                        //sirka miestnosti
                        save.writeInt(0);
                        //pocet ludi
                        save.writeInt(0);
                        save.writeInt(this.miestnosti[i][j].toStringInentifikator());
                    } else {
                        if (velkostMiestnostiTemp == 0) {
                            velkostMiestnostiTemp = this.miestnosti[i][j].getVelkostMiesnosti();
                            save.writeInt(this.miestnosti[i][j].getVelkostMiesnosti());
                            save.writeInt(this.miestnosti[i][j].getPocetLudi());
                        }
                        save.writeInt(this.miestnosti[i][j].toStringInentifikator());
                        velkostMiestnostiTemp--;
                    }
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Nepodarilo sa ulozit hru. Hra bola poškodená. Prosím reinstalujte hru.");
        } catch (IOException e) {
            System.out.println("Nepodarilo sa ulozit save.");
            e.printStackTrace(System.out);
        }


    }

    /**
     * Každá miestnosť ktorá sa má zobraziť sa zobrazí.
     */
    public void zobrazMiestnosti(Graphics grafika) {
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                if (miestnost != null) {
                    miestnost.zobraz(grafika);
                }
                // keď Builder miestnosti už viac niesu v staviteľskom režime schovajú sa.
                if (this.hra.getStavObrazokvy() == StavObrazovky.HraBezi && miestnost instanceof BuilderMiestnost) {
                    miestnost.jeVidetelne(false);
                }

            }
        }
    }

    /**
     * Metóda zobrazí menu miestností ktoré si môžem dovoliť postaviť (mám na to dosť caps). Taktiež sa stará o aby keď hráč nič nekúpil alebo nakoniec nechcel nič postavať aby sa to vrátilo do pôvodného stavu.
     */
    public void vyberoveMenu() {
        //keď druhý krát zmačknem tlačidlo stavania, tak sa zruší vybrana miestnost na stavanie.
        if (this.hra.getStavObrazokvy() == StavObrazovky.Stavanie) {
            this.hra.setStavObrazokvy(StavObrazovky.HraBezi);
            this.miestnostNaPostavenie = null;
            return;
        }

        //Kým beží táto metóda. Hra čaká.
        ImageIcon icon = new ImageIcon("src/sk/falloutshelter/fri/obr/build-ico.png");

        ArrayList<Miestnosti> rawZoznamMiestnosti = new ArrayList<>();
        if (this.bunker.getZdroje().mozemKupit(50)) {
            rawZoznamMiestnosti.add(new Vytah(0, 0, this));
        }
        if (this.bunker.getZdroje().mozemKupit(300)) {
            rawZoznamMiestnosti.add(new Elektraren(0, 0, this));
        }
        if (this.bunker.getZdroje().mozemKupit(250)) {
            rawZoznamMiestnosti.add(new Vodaren(0, 0, this));
        }
        if (this.bunker.getZdroje().mozemKupit(200)) {
            rawZoznamMiestnosti.add(new Ubytovanie(0, 0, this));
        }
        if (this.bunker.getZdroje().mozemKupit(200)) {
            rawZoznamMiestnosti.add(new Jedalen(0, 0, this));
        }

        Miestnosti[] zoznamMiestnosti = new Miestnosti[rawZoznamMiestnosti.size()];

        int poradie = 0;
        for (Miestnosti miestnosti1 : rawZoznamMiestnosti) {
            zoznamMiestnosti[poradie] = miestnosti1;
            poradie++;
        }


        if (zoznamMiestnosti.length > 0) {
            this.miestnostNaPostavenie = JpanelVyberMiestnosti.zobrazVyberoveMenu(zoznamMiestnosti, icon);
            if (this.miestnostNaPostavenie == null) {
                return;
            }
            this.hra.setStavObrazokvy(StavObrazovky.Stavanie);
            this.zobrazMoznostiStavania(this.miestnostNaPostavenie);
            //System.out.println(this.miestnostNaPostavenie);
        }

    }

    /**
     * Tato metóda zobrazí možnosti stavania na základe toho aká miestnosť je vybratá.
     * @param vyberoveMenu miestnosť ktorú chceme postaviť
     */
    private void zobrazMoznostiStavania(Miestnosti vyberoveMenu) {
        for (int i = 0; i < this.miestnosti.length; i++) {
            for (int j = 0; j < this.miestnosti[i].length; j++) {
                if (!(this.miestnosti[i][j] instanceof BuilderMiestnost)) {
                    if (j > 0 && this.miestnosti[i][j - 1] instanceof BuilderMiestnost) {
                        this.miestnosti[i][j - 1].jeVidetelne(true);
                    }

                    if (j < this.miestnosti[i].length && this.miestnosti[i][j + 1] instanceof BuilderMiestnost) {
                        this.miestnosti[i][j + 1].jeVidetelne(true);
                    }

                    if (i > 0 && i < this.miestnosti.length - 1 && !(this.miestnosti[i - 1][j] instanceof BuilderMiestnost) && vyberoveMenu instanceof Vytah) {
                        this.miestnosti[i + 1][j].jeVidetelne(true);
                    }
                }
            }
        }
    }


    /**
     * Miestnosť ktorú chcem postaviť získa s parametra this.miestnostNaPostavenie
     *
     * Vytváranie miestnosti funguje nasledovne:
     *      Kliknutím na tlačidlo stavania za zobrazí v Rozložení miestnosti menu kde si vyberieme akú miestnosť chceme stavať
     *      Keď vyberieme z menu miestnosť, tak sa na ukáže kde ju môžeme postavať.
     *      Kliknutím na to miesto sa odošle správa všetkým miestnostiam klik. Miestnosť na ktorú som klikol (Builder miestnost - tie na klik reagujú iba keď je hra v builder režime).
     *      Builder miestnosť zavolá metódu pridaj miestnosť a dá jej svoje súradnice v matici.
     *      Metóda pridaj miestnosť pridá miestnosť podľa toho aká bola vybratá vo výberom menu a postaví ju na správne miesto - získala od Builder miestnosti.
     *
     * @param riadok y os
     * @param stlpec x os
     */
    public void pridajMiestnosti(int riadok, int stlpec) {
        if (this.miestnostNaPostavenie != null && this.miestnosti[riadok][stlpec] instanceof BuilderMiestnost) {
            Zdroje zdroje = this.bunker.getZdroje();
            if (this.miestnostNaPostavenie instanceof Elektraren) {
                if (stlpec - 1 >= 0 && this.miestnosti[riadok][stlpec - 1] instanceof Elektraren && this.miestnosti[riadok][stlpec - 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec - 1];
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaElektraren();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(300);
                    }
                } else if (stlpec + 1 < this.miestnosti[riadok].length && this.miestnosti[riadok][stlpec + 1] instanceof Elektraren && this.miestnosti[riadok][stlpec + 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec + 1];
                    this.miestnosti[riadok][stlpec].setSuradnice(riadok, stlpec);
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaElektraren();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(300);
                    }
                } else {
                    this.miestnosti[riadok][stlpec] = new Elektraren(riadok, stlpec, this);
                    if (this.nakupovanie) {
                        zdroje.nakupuj(300);
                    }
                }
            } else if (this.miestnostNaPostavenie instanceof Ubytovanie) {
                if (stlpec - 1 >= 0 && this.miestnosti[riadok][stlpec - 1] instanceof Ubytovanie && this.miestnosti[riadok][stlpec - 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec - 1];
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.noveUbytovanie();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                } else if (stlpec + 1 < this.miestnosti[riadok].length && this.miestnosti[riadok][stlpec + 1] instanceof Ubytovanie && this.miestnosti[riadok][stlpec + 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec + 1];
                    this.miestnosti[riadok][stlpec].setSuradnice(riadok, stlpec);
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.noveUbytovanie();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                } else {
                    this.miestnosti[riadok][stlpec] = new Ubytovanie(riadok, stlpec, this);
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                }
            } else if (this.miestnostNaPostavenie instanceof Vytah) {
                this.miestnosti[riadok][stlpec] = new Vytah(riadok, stlpec, this);
                if (this.nakupovanie) {
                    zdroje.nakupuj(50);
                }
            } else if (this.miestnostNaPostavenie instanceof Jedalen) {
                if (stlpec - 1 >= 0 && this.miestnosti[riadok][stlpec - 1] instanceof Jedalen && this.miestnosti[riadok][stlpec - 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec - 1];
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaJedalen();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                } else if (stlpec + 1 < this.miestnosti[riadok].length && this.miestnosti[riadok][stlpec + 1] instanceof Jedalen && this.miestnosti[riadok][stlpec + 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec + 1];
                    this.miestnosti[riadok][stlpec].setSuradnice(riadok, stlpec);
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaJedalen();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                } else {
                    this.miestnosti[riadok][stlpec] = new Jedalen(riadok, stlpec, this);
                    if (this.nakupovanie) {
                        zdroje.nakupuj(200);
                    }
                }
            } else if (this.miestnostNaPostavenie instanceof Vodaren) {
                if (stlpec - 1 >= 0 && this.miestnosti[riadok][stlpec - 1] instanceof Vodaren && this.miestnosti[riadok][stlpec - 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec - 1];
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaVodaren();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(250);
                    }
                } else if (stlpec + 1 < this.miestnosti[riadok].length && this.miestnosti[riadok][stlpec + 1] instanceof Vodaren && this.miestnosti[riadok][stlpec + 1].getVelkostMiesnosti() < 3) {
                    this.miestnosti[riadok][stlpec] = this.miestnosti[riadok][stlpec + 1];
                    this.miestnosti[riadok][stlpec].setSuradnice(riadok, stlpec);
                    this.miestnosti[riadok][stlpec].zvetsiMiestnost();
                    this.novaVodaren();
                    if (this.nakupovanie) {
                        zdroje.nakupuj(250);
                    }
                } else {
                    this.miestnosti[riadok][stlpec] = new Vodaren(riadok, stlpec, this);
                    if (this.nakupovanie) {
                        zdroje.nakupuj(250);
                    }
                }
            }
            this.miestnostNaPostavenie = null;
            if (this.nakupovanie) {
                this.hra.setStavObrazokvy(StavObrazovky.HraBezi);
            }
        }
    }


    /**
     * Prepošle klik každej miestnosti.
     */
    @Override
    public void klik(int x, int y) {
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                try {
                    miestnost.klik(x, y);
                } catch (KlikException e) {
                    this.ulozMiestnostiDoSuboru();
                    return;
                }
            }
        }
        this.ulozMiestnostiDoSuboru();
    }

    /**
     * Odošle tik.
     */
    @Override
    public void tik() {
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                miestnost.tik();
            }
        }
    }


    /**
     * Pomocná metóda ktorá vytvorí maticu s miestnosťami kde môžeme pridať ľudí.
     * @return Matica pre menu s miestnosťami ktoré prímu nových ľudí.
     */
    public Miestnosti[] getMiestnostiSMaloLudmi() {
        ArrayList<Miestnosti> rawZoznamMiestnosti = new ArrayList<>();
        for (Miestnosti[] miestnostis : this.miestnosti) {
            for (Miestnosti miestnost : miestnostis) {
                if (miestnost.volneMiestoNaLudi() && !(miestnost instanceof Vytah) && !(miestnost instanceof Vchod) && !(miestnost instanceof BuilderMiestnost) && !(miestnost instanceof VyplnaciaMiestnost)) {
                    if (rawZoznamMiestnosti.size() == 0) {
                        rawZoznamMiestnosti.add(miestnost);
                        continue;
                    }
                    if (rawZoznamMiestnosti.get(rawZoznamMiestnosti.size() - 1) != miestnost) {
                        rawZoznamMiestnosti.add(miestnost);
                    }
                }
            }
        }

        return rawZoznamMiestnosti.toArray(Miestnosti[]::new);
    }

    public Bunker getBunker() {
        return this.bunker;
    }

    public void novaElektraren() {
        this.pocetElektrari++;
    }

    public void noveUbytovanie() {
        this.pocetUbytovania++;
    }

    public void novaVodaren() {
        this.pocetVodarni++;
    }

    public void novaJedalen() {
        this.pocetJedalni++;
    }

    public void novyVytah() {
        this.pocetVytahov++;
    }


    public int getPocetUbytovania() {
        return this.pocetUbytovania;
    }

    public int getPocetVodarni() {
        return this.pocetVodarni;
    }

    public int getPocetElektrari() {
        return this.pocetElektrari;
    }

    public int getPocetJedalni() {
        return this.pocetJedalni;
    }

    public int getPocetVytahov() {
        return this.pocetVytahov;
    }
}

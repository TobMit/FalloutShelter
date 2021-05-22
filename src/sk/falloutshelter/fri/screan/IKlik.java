package sk.falloutshelter.fri.screan;

import sk.falloutshelter.fri.prostredie.miestnosti.KlikException;

/**
 * 1. 5. 2021 - 17:51
 *
 * @author Tobias
 */
public interface IKlik {
    void klik(int x, int y) throws KlikException;
}

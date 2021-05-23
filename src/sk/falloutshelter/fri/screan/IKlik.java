package sk.falloutshelter.fri.screan;

import sk.falloutshelter.fri.prostredie.KlikException;

/**
 * Interface pre klik myškou.
 * @author Tobias
 */
public interface IKlik {
    void klik(int x, int y) throws KlikException;
}

package sk.falloutshelter.fri.screan;

import java.awt.*;

/**
 * Interface pre vykresľovanie a obsluhu grafiky.
 * @author Tobias
 */
public interface IZobraz {
    void zobraz(Graphics grafika);
    void jeVidetelne(boolean viditelne);
}

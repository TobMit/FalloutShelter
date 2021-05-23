package sk.falloutshelter.fri.prostredie;

/**
 * Keď nejaká miestnosť zareaguje na klik - klik ukazuje na tú miestnosť tak už sa nepokračuje v cykle keďže v matici sú rovnaké miestnosti zapojené za sebou (referencie na jednu hlavnú miestnosť) – klik by sa vykonal toľko krát koľko krát je miestnosť veľká, čo nie je správne.
 */
public class KlikException extends Exception {
    public KlikException(String message) {
        super(message);
    }
}

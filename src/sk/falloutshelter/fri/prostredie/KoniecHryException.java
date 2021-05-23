package sk.falloutshelter.fri.prostredie;

/**
 * Exception na koniec hry. – Jeden z dvoch spôsobov ako ukončiť hru.
 */
public class KoniecHryException extends Exception {
    public KoniecHryException(String message) {
        super(message);
    }
}

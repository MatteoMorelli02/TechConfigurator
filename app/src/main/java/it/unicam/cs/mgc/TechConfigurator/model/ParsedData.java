package it.unicam.cs.mgc.TechConfigurator.model;

import java.util.Collection;

/**
 * Le classi che implementano quest'interfaccia sono utilizzate per esporre
 * i dati che sono già stati elaborati.
 */
public interface ParsedData {

    /**
     * Questo metodo recupera il valore associato a una chiave specifica nei dati elaborati.
     *
     * @param property il valore chiave.
     * @return Una stringa che rappresenta il valore associato alla chiave specificata
     */
    String getProperty(String property);

    /**
     * Questo metodo restituisce tutti i valori contenuti nei dati elaborati.
     *
     * @return Una collezione di stringhe che rappresenta tutti i valori disponibili nei dati elaborati.
     */
    Collection<String> getAllValues();

}
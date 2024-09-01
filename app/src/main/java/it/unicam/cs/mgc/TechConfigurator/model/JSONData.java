package it.unicam.cs.mgc.TechConfigurator.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Questa classe viene utilizzata come wrapper per esporre dati analizzati in formato simile a JSON.
 */
public class JSONData implements ParsedData {

    /**
     * Questo attributo è una mappa che contiene i dati.
     */
    Map<String, String> data;

    public JSONData(Map<String, String> data) {
        this.data = data;
    }

    /**
     * Questo metodo restituisce il valore associato alla chiave specificata.
     *
     * @return null se la chiave non esiste nella mappa.
     */
    public String getProperty(String property) {
        return this.data.get(property);
    }

    /**
     * Questo metodo restituisce tutti i valori presenti nella mappa.
     */
    public Collection<String> getAllValues() {
        return this.data.values();
    }

    /**
     * Questo metodo fornisce un iteratore per scorrere tutte le coppie chiave-valore presenti nella mappa.
     */
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.data.entrySet().iterator();
    }
}
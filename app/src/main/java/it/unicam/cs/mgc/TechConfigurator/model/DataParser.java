package it.unicam.cs.mgc.TechConfigurator.model;

import org.apache.jena.query.QueryExecution;

/**
 * Quest'interfaccia è progettata per essere implementata da classi
 * che si occupano di convertire i risultati ottenuti da una query SPARQL in un formato specifico.
 */
public interface DataParser {

    /**
     * Questo metodo si occupa di trasformare il risultato di una query SPARQL in un blocco di dati.
     *
     * @param queryExecution l'esecuzione della query SPARQL.
     */
    ParsedData parse(QueryExecution queryExecution);

}



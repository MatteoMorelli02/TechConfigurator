package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.queries.SparqlQuery;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;

/**
 * Le classi che implementano quest'interfaccia sono utilizzate
 * per eseguire query SPARQL su un modello RDF di ontologia.
 */
public interface QueryProcessor {

    /**
     * Esegue una query SPARQL sul modello dato.
     *
     * @param query la query da eseguire
     * @param model il modello su cui eseguire la query
     * @return l'esecuzione della query
     */
    QueryExecution perform(SparqlQuery query, Model model);

    /**
     * Esegue una query SPARQL parametrizzata sul modello dato.
     *
     * @param query la query da eseguire
     * @param model il modello su cui eseguire la query
     * @param args gli argomenti per la query
     * @return l'esecuzione della query
     */
    QueryExecution perform(SparqlQuery query, Model model, Object... args);

}

package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.queries.SparqlQuery;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;

/**
 *  Questa classe è progettata per eseguire query SPARQL su un'ontologia RDF all'interno dell'applicazione.
 */
public class OntologyQueryProcessor implements QueryProcessor {

    /**
     * Questo metodo esegue una query SPARQL sul modello RDF fornito.
     */
    public QueryExecution perform(SparqlQuery query, Model model) {
        Query queryToPerform = QueryFactory.create(query.getCompleteQuery());
        return QueryExecutionFactory.create(queryToPerform, model);
    }

    /**
     * Questo metodo esegue una query SPARQL sul modello RDF fornito,
     * con argomenti aggiuntivi per la query.
     */
    public QueryExecution perform(SparqlQuery query, Model model, Object...args) {
        Query queryToPerform = QueryFactory.create(query.getCompleteQuery(args));
        return QueryExecutionFactory.create(queryToPerform, model);
    }

    public QueryExecution perform(String queryString, Model model) {
        return QueryExecutionFactory.create(queryString, model);
    }

}

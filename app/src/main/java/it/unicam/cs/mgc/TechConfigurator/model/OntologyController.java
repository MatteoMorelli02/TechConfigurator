package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.builders.InferredOntologyBuilder;
import it.unicam.cs.mgc.TechConfigurator.model.queries.SPARQLSelectionQuery;
import it.unicam.cs.mgc.TechConfigurator.model.util.OntologyURI;
import openllet.jena.PelletReasonerFactory;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.*;

/**
 * Questa è progettata per gestire e interagire con un'ontologia RDF all'interno dell'applicazione.
 */
public class OntologyController {

    private Model model;
    private final InferredOntologyBuilder modelBuilder = new InferredOntologyBuilder();
    private final OntologyQueryProcessor queryExecutor = new OntologyQueryProcessor();

    public OntologyController() {
        this.model = modelBuilder.buildOntologyModel(OntModelSpec.OWL_DL_MEM,  OntologyURI.LOCAL.getURI());
        this.startInference();
    }

    /**
     * Questo metodo avvia il processo di inferenza sul modello RDF corrente.
     * Utilizza 'PelletReasonerFactory' per ottenere un reasoner.
     */
    public void startInference() {
        this.model = modelBuilder.createInferredModel(model, PelletReasonerFactory.THE_SPEC.getReasoner());
    }

    /**
     * Questo metodo ottiene i dati dall'ontologia utilizzando una query SPARQL come stringa.
     *
     * @param queryString la stringa di query SPARQL.
     * @return il risultato dei dati della query.
     */
    public ParsedData get(String queryString) {
        JSONParser parser = new JSONParser();
        QueryExecution queryExecution = queryExecutor.perform(queryString, this.model);
        return parser.parse(queryExecution);
    }

    /**
     * Questo metodo ottiene i dati dall'ontologia.
     *
     * @param query i dati
     * @return il risultato dei dati della query.
     */
    public ParsedData get(SPARQLSelectionQuery query) {
        JSONParser parser = new JSONParser();
        QueryExecution queryExecution = queryExecutor.perform(query, this.model);
        return parser.parse(queryExecution);
    }

    /**
     * Questo metodo ottiene i dati dall'ontologia e consente di passare argomenti aggiuntivi alla query.
     *
     * @param query i dati.
     * @param args i parametri per la query.
     * @return il risultato dei dati della query.
     */
    public ParsedData get(SPARQLSelectionQuery query, Object...args) {
        JSONParser parser = new JSONParser();
        QueryExecution queryExecution = queryExecutor.perform(query, this.model, args);
        return parser.parse(queryExecution);
    }

    /**
     * Questo metodo controlla se il modello inferito è consistente.
     *
     * @return true se il modello è consistente
     */
    public boolean isConsistent() {
        if(this.model instanceof InfModel) {
            return InferredOntologyBuilder.checkModelConsistency((InfModel) model);
        }
        return false;
    }

}
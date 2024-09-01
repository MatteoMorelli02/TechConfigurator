package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import java.util.*;

/**
 * Questa classe viene utilizzata per trasformare i risultati di una query SPARQL
 * in una struttura dati simile al formato JSON.
 */
public class JSONParser implements DataParser {

    private final Map<String, String> data = new HashMap<>();

    /**
     * Questo metodo esegue la query SPARQL,
     * analizza i risultati e li trasforma in una mappa di dati che rappresenta una struttura JSON-like.
     */
    public ParsedData parse(QueryExecution queryExecution) {
        ResultSet results = queryExecution.execSelect();
        while(results.hasNext()) {
            QuerySolution result = results.nextSolution();
            String label = this.parseNodeToString(result.get("label"));
            String value = this.parseNodeToString(result.get("value"));
            if(data.containsKey(label)) {
                data.put(label, data.get(label) + ", " + value);
            } else {
                data.put(label, value);
            }
        }
        return new JSONData(data);
    }

    /**
     * Questo metodo converte un nodo RDF in una stringa.
     *
     * @param node il nodo RDF da convertire.
     * @return una stringa che rappresenta il nodo. Una stringa vuota se il nodo è null o se non è ne una risorsa ne un literal.
     */
    private String parseNodeToString(RDFNode node) {
        if(node == null) return "";
        if(node.isResource()) {
            return this.getNodeLabel(node.asResource());
        } else {
            return node.asLiteral().getString();
        }
    }
    /**
     * Restituisce l'etichetta RDF di un nodo risorsa. Se la risorsa non ha un'etichetta,
     * restituisce il nome della risorsa estratto dal suo URI.
     *
     * @param resource il nodo risorsa
     * @return la rappresentazione dell'etichetta RDF come stringa
     */
    private String getNodeLabel(Resource resource) {
        if(resource.getProperty(RDFS.label) == null) {
            return PropertiesFormatter.removeUriPrefix(resource.getURI());
        } else {
            return resource.getProperty(RDFS.label).getString();
        }
    }

}

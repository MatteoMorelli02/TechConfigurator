package it.unicam.cs.mgc.TechConfigurator.model.queries;

/**
 * Le Enumerazioni che implementano quest'interfaccia sono utilizzate
 * per rappresentare un gruppo di query SPARQL.
 */
public interface SparqlQuery {

    /**
     * Questo metodo restituisce la query SPARQL come una stringa.
     */
    String getQueryString();

    /**
     * Questo metodo restituisce una query SPARQL formattata, utilizzando gli argomenti passati come parametri.
     */
    String getQueryString(Object... args);

    default String getCompleteQuery() {
        return getPrefixes() + this.getQueryString();
    }

    default String getCompleteQuery(Object...args) {
        return getPrefixes() + this.getQueryString(args);
    }

    /**
     * Questo metodo statico restituisce una stringa contenente i prefissi SPARQL necessari per le query.
     */
    static String getPrefixes() {
        return """
                PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX tc: <https://www.unicam.it/cs/matteomorelli/tech-configurator#>
                """;
    }
}
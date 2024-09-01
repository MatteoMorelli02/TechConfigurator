package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.queries.SPARQLSelectionQuery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuerySparqlTest {

    @Test
    public void testQueryParameterization() {
        String query = SPARQLSelectionQuery.LISTA_TECHOBJECT.getQueryString("Monitor, Mouse, Computer, Tastiera, Cuffie");
        String expectedQuery = "SELECT ?label ?value WHERE {?techobject rdfs:subClassOf tc:TechObject .FILTER NOT EXISTS {?techobject rdfs:subClassOf ?superclass .?superclass rdfs:subClassOf tc:Computer .}?techobject rdfs:label ?value .BIND(STR(?techobject) AS ?label)}";
        assertEquals(expectedQuery, query);
    }

}

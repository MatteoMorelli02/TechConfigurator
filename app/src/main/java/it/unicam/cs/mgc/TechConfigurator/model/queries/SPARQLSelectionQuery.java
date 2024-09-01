package it.unicam.cs.mgc.TechConfigurator.model.queries;
/**
 * Questa enumerazione è utilizzata per rappresentare una query SPARQL che seleziona dati dall'ontologia.
 */
public enum SPARQLSelectionQuery implements SparqlQuery {

    LISTA_TECHOBJECT("SELECT ?label ?value WHERE {?techobject rdfs:subClassOf tc:TechObject .FILTER NOT EXISTS {?techobject rdfs:subClassOf ?superclass .?superclass rdfs:subClassOf tc:Computer .}?techobject rdfs:label ?value .BIND(STR(?techobject) AS ?label)}"),
    CATEGORIE_COMPUTER("SELECT ?label ?value WHERE { ?TechObjectCategory rdfs:subClassOf tc:Computer. BIND((?TechObjectCategory) AS ?label). ?TechObjectCategory rdfs:label ?value .}"),
    LISTA_COLORI("SELECT ?label ?value WHERE {  ?individual rdf:type tc:Colore. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_MATERIALI("SELECT ?label ?value WHERE {  ?individual rdf:type tc:Materiale. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    DIMENSIONE_TECHOBJECT("SELECT ?label ?value  WHERE { ?dimtc rdf:type tc:Dimensione%s. BIND((?dimtc) AS ?label) .?dimtc rdfs:label ?value .}"),
    TIPO_TASTIERA("SELECT ?label ?value WHERE { ?TipoTastiera rdfs:subClassOf tc:PerTastiera. BIND((?TipoTastiera) AS ?label). ?TipoTastiera rdfs:label ?value .}"),
    TIPO_RETROILLUMINAZIONE("SELECT ?label ?value  WHERE { ?TipoRetroilluminazione rdfs:subClassOf tc:Retroilluminazione. BIND((?TipoRetroilluminazione) AS ?label) .?TipoRetroilluminazione rdfs:label ?value .}"),
    TIPO_CONNESSIONE("SELECT ?label ?value  WHERE { ?TipoConnessione rdfs:subClassOf tc:Connessione. BIND((?TipoConnessione) AS ?label) .?TipoConnessione rdfs:label ?value .}"),
    LISTA_DPI("SELECT ?label ?value WHERE {  ?individual rdf:type tc:DPI. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_NUMPULSANTI("SELECT ?label ?value WHERE {  ?individual rdf:type tc:NumeroPulsantiProgrammabili. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_TIPOPANNELLO("SELECT ?label ?value WHERE {  ?individual rdf:type tc:TipoPannello. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_RISOLUZIONE("SELECT ?label ?value WHERE {  ?individual rdf:type tc:Risoluzione. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_DISCORIGIDO("SELECT ?label ?value WHERE {  ?individual rdf:type tc:DiscoRigido. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_MEMORIARAM("SELECT ?label ?value WHERE {  ?individual rdf:type tc:MemoriaRAM. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_PROCESSORE("SELECT ?label ?value WHERE {  ?individual rdf:type tc:Processore. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}"),
    LISTA_SCHEDAGRAFICA("SELECT ?label ?value WHERE {  ?individual rdf:type tc:SchedaGrafica. BIND((?individual) AS ?label) .?individual rdfs:label ?value.}");

    private final String query;

    SPARQLSelectionQuery(String query) {
        this.query = query;
    }

    public String getQueryString() {
        return query;
    }

    public String getQueryString(Object... args) {return String.format(query, args);}

}
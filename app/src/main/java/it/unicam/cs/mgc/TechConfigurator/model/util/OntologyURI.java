package it.unicam.cs.mgc.TechConfigurator.model.util;

/**
 * Questa classe rappresenta un'enumerazione che contiene una serie di URI
 * relativi a ontologie sia web che locali
 */
public enum OntologyURI {

    XSD("http://www.w3.org/2001/XMLSchema#"),
    OWL("http://www.w3.org/2002/07/owl#"),
    RDF("http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
    RDFS("http://www.w3.org/2000/01/rdf-schema#"),
    TC("https://www.unicam.it/cs/matteomorelli/tech-configurator#"),
    LOCAL("/owl/tech-configurator.rdf");


    private final String URI;

    OntologyURI(String URI) {
        this.URI = URI;
    }

    public String getURI() {
        return URI;
    }
}


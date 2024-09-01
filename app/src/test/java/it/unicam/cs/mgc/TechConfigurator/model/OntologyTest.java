package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.builders.BasicOntologyBuilder;
import it.unicam.cs.mgc.TechConfigurator.model.util.OntologyURI;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OntologyTest {

    private Model model;

    @BeforeEach
    @Test
    public void setUp(){
        BasicOntologyBuilder builder = new BasicOntologyBuilder();
        this.model = builder.buildOntologyModel(OntModelSpec.OWL_DL_MEM, OntologyURI.LOCAL.getURI());
        assertNotNull(model);
    }

    @Test
    public void testCorrectStatementPresence(){
        Resource subject = model.getResource(OntologyURI.TC.getURI() + "Rosso");
        Resource object = model.getResource(OntologyURI.TC.getURI() + "Colore");
        assertTrue(model.contains(subject, RDF.type, object));
    }


}

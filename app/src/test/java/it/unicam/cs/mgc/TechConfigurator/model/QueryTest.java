package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.queries.SPARQLSelectionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QueryTest {

    private OntologyController ontology;

    @BeforeEach
    @Test
    public void setUp() {
        ontology = new OntologyController();
        assertNotNull(ontology);
    }

    @Test
    public void queryListaTechObject(){
        ParsedData data = ontology.get(SPARQLSelectionQuery.LISTA_TECHOBJECT);
        Collection<String> listaTechObject = data.getAllValues();
        assertEquals(5, listaTechObject.size());
    }

    @Test
    public void queryCategorieComputer(){
        ParsedData data = ontology.get(SPARQLSelectionQuery.CATEGORIE_COMPUTER);
        Collection<String> listaTechObject = data.getAllValues();
        assertEquals(2, listaTechObject.size());
    }

    @Test
    public void queryListaColori(){
        ParsedData data = ontology.get(SPARQLSelectionQuery.LISTA_COLORI);
        Collection<String> listaTechObject = data.getAllValues();
        assertEquals(6, listaTechObject.size());
    }


    @Test
    public void queryDimensione() {
        ParsedData data = ontology.get(SPARQLSelectionQuery.DIMENSIONE_TECHOBJECT, "Mouse");
        Collection<String> dimensioni = data.getAllValues();
        assertEquals(3, dimensioni.size());
    }



}

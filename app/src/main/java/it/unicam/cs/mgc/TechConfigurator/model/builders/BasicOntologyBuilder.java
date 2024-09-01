package it.unicam.cs.mgc.TechConfigurator.model.builders;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.net.URL;
import java.util.Objects;

/**
 *Questa classe è utilizzata per creare modelli RDF.
 */
public class BasicOntologyBuilder implements OntologyBuilder {

    /**
     * FileManager è uno strumento fornito da Jena per gestire l'accesso ai file RDF.
     * FileManager.getInternal() crea un gestore che utilizza solo risorse interne al progetto.
     */
    private final FileManager fileManager = FileManager.getInternal();

    /**
     * Questo metodo crea un modello RDF di base vuoto.
     */
    public Model createBasicModel() {
        return ModelFactory.createDefaultModel();
    }

    /**
     * Questo metodo consente di creare un modello RDF di base.
     *
     * @param filenames file che possono essere caricato all'interno del modello RDF.
     * getClass().getResource(filename) trova il file all’interno del classpath,
     * assicurandosi che esista tramite Objects.requireNonNull.
     */
    public Model createBasicModel(String...filenames) {
        Model model = ModelFactory.createDefaultModel();
        for(String filename: filenames) {
            model.read(fileManager.open(Objects.requireNonNull
                    (getClass().getResource(filename)).toString()), null);
        }
        return model;
    }

    /**
     * Questo metodo crea un modello ontologico RDF basato su una specifica di modello (OntModelSpec),
     * che definisce come l'ontologia deve essere gestita
     */
    public Model buildOntologyModel(OntModelSpec modelSpecification) {
        return ModelFactory.createOntologyModel(modelSpecification);
    }

    /**
     * Simile al precedente metodo, ma oltre a creare il modello ontologico,
     * carica anche file RDF specificati come input.
     */
    public Model buildOntologyModel(OntModelSpec modelSpecification, String... filenames) {
        Model model = ModelFactory.createOntologyModel(modelSpecification);
        for (String filename : filenames) {
            URL resource = getClass().getResource(filename);
            if (resource == null) {
                throw new IllegalArgumentException("File non trovato: " + filename);
            }
            model.read(resource.toString(), null);
        }
        return model;
    }
}

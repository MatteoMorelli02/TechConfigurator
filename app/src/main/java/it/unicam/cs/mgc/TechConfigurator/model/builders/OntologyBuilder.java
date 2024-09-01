package it.unicam.cs.mgc.TechConfigurator.model.builders;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;

/**
 * Le classi che implementano quest'interfaccia sono utilizzate per creare modelli RDF.
 */
public interface OntologyBuilder {

    /**
     * Questo metodo è responsabile della creazione di un modello RDF di base
     * senza alcun supporto per inferenza o di un'ontologia.
     * @return il modello RDF
     */
    Model createBasicModel();

    /**
     * Questo metodo permette di creare un modello RDF di base
     * caricando dati RDF da uno o più file specificati nei parametri.
     *
     * @param filenames il nome del file
     * @return il modello RDF
     */
    Model createBasicModel(String...filenames);

    /**
     * Questo metodo è progettato per creare un modello RDF che supporta ragionamenti ontologici,
     * utilizzando specifiche regole o configurazioni.
     *
     * @param modelSpecification specifica il tipo di regole da applicare
     * @return il modello RDF
     */
    Model buildOntologyModel(OntModelSpec modelSpecification);

    /**
     * Questo metodo è simile al precedente,
     * ma oltre a creare un modello RDF con supporto per ragionamenti ontologici,
     * carica anche dati RDF dai file specificati.
     *
     * @param modelSpecification specifica il tipo di regole da applicare
     * @param filenames il nome del file
     * @return il modello RDF
     */
    Model buildOntologyModel(OntModelSpec modelSpecification, String...filenames);

}

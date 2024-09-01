package it.unicam.cs.mgc.TechConfigurator.model.builders;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import java.util.concurrent.CompletableFuture;

/**
 * La classe InferredOntologyBuilder aggiunge funzionalità per gestire i modelli RDF
 * con inferenza usando Apache Jena.
 */
public class InferredOntologyBuilder extends BasicOntologyBuilder {

    /**
     * Questo metodo crea un modello inferito.
     *
     * @param model il modello RDF di base da cui partire.
     * @param reasoner il motore di ragionamento da utilizzare.
     * @return un modello arricchito dalle inferenze generate.
     */
    public InfModel createInferredModel(Model model, Reasoner reasoner) {
        reasoner.bindSchema(model);
        return ModelFactory.createInfModel(reasoner, model);
    }

    /**
     * Questo metodo crea un modello inferito in modo asincrono.
     *
     * @param model    il modello RDF di base da cui partire.
     * @param reasoner il motore di ragionamento da utilizzare.
     * @return Un CompletableFuture che rappresenta l'operazione asincrona.
     */
    public CompletableFuture<InfModel> createInferredModelAsync(Model model, Reasoner reasoner) {
        CompletableFuture<InfModel> future = new CompletableFuture<>();
        Thread thread = new Thread(() -> {
            try {
                reasoner.bindSchema(model);
                InfModel inferredModel = ModelFactory.createInfModel(reasoner, model);
                future.complete(inferredModel);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        thread.start();
        return future;
    }

    /**
     * Questo metodo controlla se il modello è valido e consistente.
     *
     * @return true se è valido.
     */
    public static boolean checkModelConsistency(InfModel model) {
        ValidityReport validity = model.validate();
        return validity.isValid();
    }

    /**
     * Questo metodo verifica se una determinata tripla RDF è stata correttamente inferita.
     *
     * @param subject il soggetto della tripla.
     * @param predicate il predicato della tripla.
     * @param object l'oggetto della tripla
     * @return true se la tripla è stata inferita correttamente.
     */
    public static boolean checkInference(Resource subject, Property predicate, Resource object, InfModel model) {
        return model.contains(subject, predicate, object);
    }

}
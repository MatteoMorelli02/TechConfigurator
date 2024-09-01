package it.unicam.cs.mgc.TechConfigurator.controller;

import it.unicam.cs.mgc.TechConfigurator.model.ParsedData;
import it.unicam.cs.mgc.TechConfigurator.model.OntologyController;
import it.unicam.cs.mgc.TechConfigurator.model.queries.SPARQLSelectionQuery;


/**
 * Questa classe viene utilizzata per gestire lo stato dell'applicazione.
 */
public class Controller {

    private final OntologyController ontology = new OntologyController();


    /**
     * Questa metodo mostra la lista dei Tech Object dell'ontologia.
     *
     * @return dati contenti l'elenco dei Tech Object.
     */
    public ParsedData getListaTechObject() {return ontology.get(SPARQLSelectionQuery.LISTA_TECHOBJECT);}

    /**
     * Questa metodo mostra la lista delle categorie di alcuni Tech Object dell'ontologia.
     *
     * @return dati contenti l'elenco delle categorie.
     */
    public ParsedData getListaCategorieComputer() {return ontology.get(SPARQLSelectionQuery.CATEGORIE_COMPUTER);}

    /**
     * Questa metodo mostra la lista dei Colori dell'ontologia.
     *
     * @return dati contenti l'elenco dei Colori.
     */
    public ParsedData getListaColori() {return ontology.get(SPARQLSelectionQuery.LISTA_COLORI);}

    /**
     * Questa metodo mostra la lista dei Materiali dell'ontologia.
     *
     * @return dati contenti l'elenco dei Materiali.
     */
    public ParsedData getListaMateriali() {return ontology.get(SPARQLSelectionQuery.LISTA_MATERIALI);}

    /**
     * Questa metodo mostra la lista delle dimensioni per ogni Tech Object dell'ontologia.
     *
     * @return dati contenti l'elenco delle dimensioni per ogni Tech Object.
     */
    public ParsedData getListaDimensioni(String techObjectName) {
        return ontology.get(SPARQLSelectionQuery.DIMENSIONE_TECHOBJECT, techObjectName);
    }

    /**
     * Questa metodo mostra i tipi di retroilluminazione che possono essere
     * assegnate al mouse o alla tastiera.
     *
     * @return dati contenti i tipi di retroilluminazione.
     */
    public ParsedData getListaRetroilluminazioni() {return ontology.get(SPARQLSelectionQuery.TIPO_RETROILLUMINAZIONE);}

    /**
     * Questa metodo mostra i tipi di tastiera che possono essere scelte dall'utente
     *
     * @return dati contenti i tipi di tastiera.
     */
    public ParsedData getListaTastiera() {return ontology.get(SPARQLSelectionQuery.TIPO_TASTIERA);}

    /**
     * Questa metodo mostra i tipi di connessione che possono essere
     * assegnate al mouse o alle cuffie.
     *
     * @return dati contenti i tipi di connessione.
     */
    public ParsedData getListaConnessioni() {return ontology.get(SPARQLSelectionQuery.TIPO_CONNESSIONE);}

    /**
     * Questa metodo mostra la opzioni di dpi che si possono assegnare a un mouse.
     *
     * @return dati contenti il tipo di dpi.
     */
    public ParsedData getListaDpi() {return ontology.get(SPARQLSelectionQuery.LISTA_DPI);}

    /**
     * Questa metodo mostra le opzioni di pulsanti programmabili che si possono assegnare a un mouse.
     *
     * @return dati contenti il numero di pulsanti programmabili.
     */
    public ParsedData getListaNumPulsanti() {return ontology.get(SPARQLSelectionQuery.LISTA_NUMPULSANTI);}

    /**
     * Questa metodo mostra le opzioni di pannelli che si possono assegnare a un monitor.
     *
     * @return dati contenti il tipo dei pannelli.
     */
    public ParsedData getListaTipoPannello() {return ontology.get(SPARQLSelectionQuery.LISTA_TIPOPANNELLO);}

    /**
     * Questa metodo mostra le opzioni di risoluzione che si possono assegnare a un monitor.
     *
     * @return dati contenti il tipo di risoluzione.
     */
    public ParsedData getListaRisoluzione() {return ontology.get(SPARQLSelectionQuery.LISTA_RISOLUZIONE);}

    /**
     * Questa metodo mostra le opzioni di disco rigido che si possono assegnare a un computer.
     *
     * @return dati contenti il tipo disco rigido.
     */
    public ParsedData getListaDiscoRigido() {return ontology.get(SPARQLSelectionQuery.LISTA_DISCORIGIDO);}

    /**
     * Questa metodo mostra le opzioni di memoria RAM che si possono assegnare a un computer.
     *
     * @return dati contenti il tipo di memoria RAM.
     */
    public ParsedData getListaMemoriaRam() {return ontology.get(SPARQLSelectionQuery.LISTA_MEMORIARAM);}

    /**
     * Questa metodo mostra le opzioni di processore che si possono assegnare a un computer.
     *
     * @return dati contenti il tipo di processore.
     */
    public ParsedData getListaProcessore() {return ontology.get(SPARQLSelectionQuery.LISTA_PROCESSORE);}

    /**
     * Questa metodo mostra le opzioni di scheda grafica che si possono assegnare a un computer.
     *
     * @return dati contenti il tipo di scheda grafica.
     */
    public ParsedData getListaSchedaGrafica() {return ontology.get(SPARQLSelectionQuery.LISTA_SCHEDAGRAFICA);}

    /**
     * Questo metodo mostra se l'ontologia è consistente o no.
     */
    public String getOntologyConsistencyStatus() {
        if(ontology.isConsistent()) {
            return "Ontology consistent ✓";
        } else {
            return "Inconsistent ontology ｘ";
        }
    }


}
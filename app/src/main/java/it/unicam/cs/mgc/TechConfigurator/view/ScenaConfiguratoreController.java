package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import it.unicam.cs.mgc.TechConfigurator.model.ParsedData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Questa classe rappresenta il controller della scena di configurazione nell'applicazione "Tech Configurator".
 */
public class ScenaConfiguratoreController {

    private Controller appController;

    // Rappresenta il tipo di query attuale
    private String query;

    // Oggetto attualmente selezionato dall'utente
    private String selectedObject;

    // Tipo di TechObject selezionato
    private String selectedTechObject;

    private final List<Consumer<ActionEvent>> actions = new ArrayList<>();

    private int currentActionIndex = 0;

    private GestoreConfigurazione gestoreConfigurazione = new GestoreConfigurazione();

    private GestoreView gestoreView;

    // Lista delle selezioni effettuate dall'utente
    private final List<String> userSelections = new ArrayList<>();

    // Bottone per procedere alla fase successiva della configurazione
    @FXML
    private Button bottoneNext;

    // ListView per mostrare le opzioni disponibili per la configurazione
    @FXML
    private ListView<String> listView;

    /**
     * Imposta il controller dell'applicazione e inizializza la scena di configurazione.
     */
    public void setAppController(Controller appController) {
        this.appController = appController;
        setupListViewListener();
        Initialize();
    }

    public void setGestoreView(GestoreView gestoreView) {
        this.gestoreView = gestoreView;
    }

    /**
     * Questo metodo da il via alla configurazione caricando le opzioni iniziali e impostando le azioni successive.
     */
    private void Initialize() {
        if (appController != null) {
            // Popola la ListView con i TechObject
            populateListView("TechObject");
            actions.add(event -> populateListView("Colori"));
            actions.add(event -> populateListView("Materiali"));
            // Assegna al pulsante next il suo comportamento usando una lambda expression
            bottoneNext.setOnAction(event -> initializeButton(event));
        }
    }


    /**
     * Popola la ListView con le opzioni basate sul tipo di query fornito.
     */
    void populateListView(String query) {
        this.query = query;
        gestoreView.setTitleLabel(query);
        try {
            ParsedData parsedData = configura(query);
            gestoreView.populateListViewWithItems(parsedData.getAllValues());
            updateListView(); // Aggiorna la dimensione della ListView
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'invocazione del metodo: " + e.getMessage());
        }
    }

    /**
     * Recupera i dati in base alla query fornita.
     */
    private ParsedData configura(String query) {
        if (query.equals("Dimensioni")) {
            return configuraDimensione();
        } else {
            return configuraQuery(query);
        }
    }

    /**
     * Gestisce la configurazione per la query "Dimensioni".
     */
    private ParsedData configuraDimensione() {
        if (selectedObject != null && (selectedObject.equals("Case") || selectedObject.equals("Portatile"))) {
            return appController.getListaDimensioni(selectedObject);
        } else {
            return appController.getListaDimensioni(selectedTechObject);
        }
    }

    /**
     * Invoca dinamicamente un metodo sull'appController basandosi sulla query.
     */
    private ParsedData configuraQuery(String query) {
        try {
            String methodName = "getLista" + query;
            Method method = appController.getClass().getMethod(methodName);
            return (ParsedData) method.invoke(appController);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Errore durante l'invocazione del metodo: " + e.getMessage(), e);
        }
    }

    /**
     * Questo metodo permette di modificare la dimensione della listview in base
     * al numero di elementi al suo interno.
     */
    private void updateListView() {
        // Altezza dell'elemento
        double itemHeight = 25.0;
        // Numero di elementi nella ListView
        int numItems = listView.getItems().size();
        // Calcola la nuova altezza
        double newHeight = Math.min(itemHeight * numItems, 400.0);
        // Imposta la nuova altezza della ListView
        listView.setPrefHeight(newHeight);
    }

    /**
     * Questo metodo permette di aggiornare l'oggetto selezionato dall'utente.
     */
    private void setupListViewListener() {
        // Listener per aggiornare l'oggetto selezionato
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedObject = newValue;
            // Se si seleziona un TechObject, aggiorna anche selectedTechObject
            if (query.equals("TechObject")) {
                selectedTechObject = newValue;
            }
        });
    }

    /**
     * Gestisce l'azione del pulsante "Next" durante il processo di configurazione.
     * Avanza la configurazione eseguendo la prossima azione o completa la configurazione.
     */
    private void initializeButton(ActionEvent event) {
        if (selectedObject == null) {
            // Mostra un errore se nessuna opzione è selezionata
            gestoreView.showError("Seleziona un'opzione prima di procedere.");
            return;
        }
        // Aggiunge la selezione corrente alla lista delle selezioni
        userSelections.add(selectedObject);
        if (currentActionIndex < actions.size()) {
            // Esegue l'azione corrente
            actions.get(currentActionIndex).accept(event);
            // Incrementa l'indice dell'azione
            currentActionIndex++;
            if (currentActionIndex >= actions.size()) {
                // Se tutte le azioni sono completate, imposta il pulsante per applicare la configurazione
                bottoneNext.setOnAction(e -> configurationButton(e)); // Lambda expression qui
                gestoreConfigurazione.applicaConfigurazione(selectedTechObject, this);
            }
        } else {
            // Applica la configurazione se tutte le azioni sono completate
            gestoreConfigurazione.applicaConfigurazione(selectedTechObject, this);
        }
        // Resetta l'oggetto selezionato
        selectedObject = null;
    }

    /**
     * Gestisce il pulsante "Configuration" utile una volta completata la configurazione.
     */
    void configurationButton(ActionEvent event) {
        if (selectedObject == null) {
            // Mostra un errore se nessuna opzione è selezionata
            gestoreView.showError("Seleziona un'opzione prima di procedere.");
            return;
        }
        // Aggiunge la selezione corrente alla lista delle selezioni
        userSelections.add(selectedObject);
        if (currentActionIndex < actions.size()) {
            // Esegue l'azione corrente
            actions.get(currentActionIndex).accept(event);
            // Incrementa l'indice dell'azione
            currentActionIndex++;
        } else {
            // Naviga alla scena finale se tutte le azioni sono completate
            gestoreView.navigateToFinalScene();
        }
        // Resetta l'oggetto selezionato
        selectedObject = null;
    }

    public List<Consumer<ActionEvent>> getActions() {
        return actions;
    }

    public void setBottoneNextAction(EventHandler<ActionEvent> action) {
        bottoneNext.setOnAction(action);
    }

    public void setCurrentActionIndex(int index) {
        this.currentActionIndex = index;
    }
}

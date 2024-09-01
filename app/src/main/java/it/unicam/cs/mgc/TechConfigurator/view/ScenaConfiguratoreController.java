package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import it.unicam.cs.mgc.TechConfigurator.model.ParsedData;
import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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

    // Lista delle selezioni effettuate dall'utente
    private final List<String> userSelections = new ArrayList<>();

    private int currentActionIndex = 0;

    private GestoreConfigurazione configurazioneManager = new GestoreConfigurazione();

    // Testo per il titolo che descrive l'azione corrente
    @FXML
    private TextField titleLabel;

    // ListView per mostrare le opzioni disponibili per la configurazione
    @FXML
    private ListView<String> listView;

    // Bottone per procedere alla fase successiva della configurazione
    @FXML
    private Button bottoneNext;

    /**
     * Imposta il controller dell'applicazione e inizializza la scena di configurazione.
     */
    public void setAppController(Controller appController) {
        this.appController = appController;
        setupListViewListener();
        Initialize();
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
            // Assegna al pulsante next il suo comportamento
            bottoneNext.setOnAction(this::initializeButton);
        }
    }

    /**
     * Popola la ListView con le opzioni basate sul tipo di query fornito.
     */
    void populateListView(String query) {
        this.query = query;
        ParsedData parsedData;
        Collection<String> allValues;
        titleLabel.setText("Seleziona " + (PropertiesFormatter.convertCamelCaseToSpaced
                (PropertiesFormatter.upperCaseFirstLetter(query))));
        try {
            // Gestisce casi particolari per "Dimensioni"
            if (query.equals("Dimensioni")) {
                if (selectedObject != null && (selectedObject.equals("Case") || selectedObject.equals("Portatile"))) {
                    parsedData = appController.getListaDimensioni(selectedObject);
                } else {
                    parsedData = appController.getListaDimensioni(selectedTechObject);
                }
            } else {
                // Costruisce il nome del metodo, presente nel controller principale,
                // utilizzando il nome della query corrente.
                String methodName = "getLista" + query;
                Method method = appController.getClass().getMethod(methodName);
                parsedData = (ParsedData) method.invoke(appController);
            }
            // Ottiene tutti i valori e li aggiunge alla ListView
            allValues = parsedData.getAllValues();
            ObservableList<String> observableItems = FXCollections.observableArrayList(allValues);
            listView.setItems(observableItems);
            updateListView(); // Aggiorna la dimensione della ListView
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'invocazione del metodo: " + e.getMessage());
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
            showError("Seleziona un'opzione prima di procedere.");
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
                bottoneNext.setOnAction(this::configurationButton);
                configurazioneManager.applicaConfigurazione(selectedTechObject, this);
            }
        } else {
            // Applica la configurazione se tutte le azioni sono completate
            configurazioneManager.applicaConfigurazione(selectedTechObject, this);
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
            showError("Seleziona un'opzione prima di procedere.");
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
            navigateToFinalScene();
        }
        // Resetta l'oggetto selezionato
        selectedObject = null;
    }

    /**
     * Carica alla scena finale con le selezioni dell'utente.
     */
    private void navigateToFinalScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScenaFinale.fxml"));
            Parent root = loader.load();

            // Recupera il controller della scena finale e imposta le selezioni dell'utente
            ScenaFinaleController finaleController = loader.getController();
            finaleController.setUserSelections(userSelections);

            // Mostra la scena finale nella finestra corrente
            Stage stage = (Stage) bottoneNext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Errore nel caricamento della scena finale.");
        }
    }

    /**
     * Mostra un messaggio di errore all'utente.
     *
     * @param message il messaggio di errore da mostrare
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

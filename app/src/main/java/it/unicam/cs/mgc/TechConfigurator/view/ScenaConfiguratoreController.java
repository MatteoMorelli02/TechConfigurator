package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import it.unicam.cs.mgc.TechConfigurator.model.ParsedData;
import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Questa classe rappresenta il controller della scena di configurazione nell'applicazione "Tech Configurator".
 */
public class ScenaConfiguratoreController {

    private Controller appController;
    private String query;
    private String selectedObject;
    private String selectedTechObject;
    private final List<Consumer<ActionEvent>> actions = new ArrayList<>();
    private final List<String> userSelections = new ArrayList<>();
    private int currentActionIndex = 0;
    private GestoreConfigurazione configurazioneManager = new GestoreConfigurazione();
    @FXML
    private TextField titleLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button bottoneNext;

    /**
     * Imposta il controller dell'applicazione e inizializza la scena di configurazione.
     */
    public void setAppController(Controller appController) {
        this.appController = appController;
        setupListViewListener();
        initialize();
    }

    /**
     * Questo metodo da il via alla configurazione caricando le opzioni iniziali e impostando le azioni successive.
     */
    private void initialize() {
        if (appController != null) {
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
        setTitleLabel(query);
        try {
            ParsedData parsedData = configura(query);
            populateListViewWithItems(parsedData.getAllValues());
            updateListView(); // Aggiorna la dimensione della ListView
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'invocazione del metodo: " + e.getMessage());
        }
    }

    /**
     * Imposta il titolo della label in base alla query.
     */
    private void setTitleLabel(String query) {
        String formattedTitle = "Seleziona " +
                PropertiesFormatter.convertCamelCaseToSpaced(query);
        titleLabel.setText(formattedTitle);
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
     * Popola la ListView con gli elementi forniti, inserendo lo spazio e le maiuscole.
     */
    private void populateListViewWithItems(Collection<String> allValues) {
        // Applica la formattazione a ciascun elemento della lista
        List<String> formattedValues = allValues.stream()
                .map(value -> PropertiesFormatter.convertCamelCaseToSpaced(value))
                .collect(Collectors.toList());

        // Crea una lista osservabile con i valori formattati
        ObservableList<String> observableItems = FXCollections.observableArrayList(formattedValues);
        listView.setItems(observableItems);
    }

    /**
     * Questo metodo permette di modificare la dimensione della listview in base
     * al numero di elementi al suo interno.
     */
    private void updateListView() {
        double itemHeight = 25.0;
        int numItems = listView.getItems().size();
        double newHeight = Math.min(itemHeight * numItems, 400.0);
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
            actions.get(currentActionIndex).accept(event);
            currentActionIndex++;
            if (currentActionIndex >= actions.size()) {
                bottoneNext.setOnAction(e -> configurationButton(e)); // Lambda expression qui
                configurazioneManager.applicaConfigurazione(selectedTechObject, this);
            }
        } else {
            configurazioneManager.applicaConfigurazione(selectedTechObject, this);
        }
        selectedObject = null;
    }

    /**
     * Gestisce il pulsante "Configuration" utile una volta completata la configurazione.
     */
    void configurationButton(ActionEvent event) {
        if (selectedObject == null) {
            showError("Seleziona un'opzione prima di procedere.");
            return;
        }
        userSelections.add(selectedObject);
        if (currentActionIndex < actions.size()) {
            actions.get(currentActionIndex).accept(event);
            currentActionIndex++;
        } else {
            navigateToFinalScene();
        }
        selectedObject = null;
    }

    /**
     * Carica alla scena finale con le selezioni dell'utente.
     */
    private void navigateToFinalScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScenaFinale.fxml"));
            Parent root = loader.load();
            ScenaFinaleController finaleController = loader.getController();
            finaleController.setUserSelections(userSelections);
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
    protected void showError(String message) {
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

package it.unicam.cs.mgc.TechConfigurator.view;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScenaConfiguratoreController {

    private Controller appController;
    private String query;
    private String selectedObject;
    private String selectedTechObject;
    private final List<Consumer<ActionEvent>> actions = new ArrayList<>();
    private final List<String> userSelections = new ArrayList<>();
    private int currentActionIndex = 0;
    private final GestoreConfigurazione gestoreConfigurazione = new GestoreConfigurazione();
    @FXML
    private TextField titleLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button bottoneNext;

    public Controller getAppController() {
        return appController;
    }

    public String getSelectedObject() {
        return selectedObject;
    }

    public String getSelectedTechObject() {
        return selectedTechObject;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public TextField getTitleLabel() {
        return titleLabel;
    }

    public ListView<String> getListView() {
        return listView;
    }

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
            gestoreConfigurazione.populateListView("TechObject", this);
            actions.add(event -> gestoreConfigurazione.populateListView("Colori", this));
            actions.add(event -> gestoreConfigurazione.populateListView("Materiali", this));
            bottoneNext.setOnAction(this::initializeButton);
        }
    }

    /**
     * Questo metodo permette di aggiornare l'oggetto selezionato dall'utente.
     */
    private void setupListViewListener() {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedObject = newValue;
            if ("TechObject".equals(query)) {
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
            showError("Seleziona un'opzione prima di procedere.");
            return;
        }
        userSelections.add(selectedObject);
        if (currentActionIndex < actions.size()) {
            actions.get(currentActionIndex).accept(event);
            currentActionIndex++;
            if (currentActionIndex >= actions.size()) {
                bottoneNext.setOnAction(e -> configurationButton(e));
                gestoreConfigurazione.applicaConfigurazione(selectedTechObject, this);
            }
        } else {
            gestoreConfigurazione.applicaConfigurazione(selectedTechObject, this);
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








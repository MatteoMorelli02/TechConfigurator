package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GestoreView {

    // Testo per il titolo che descrive l'azione corrente
    @FXML
    private TextField titleLabel;

    // ListView per mostrare le opzioni disponibili per la configurazione
    @FXML
    private ListView<String> listView;

    // Bottone per procedere alla fase successiva della configurazione
    @FXML
    private Button bottoneNext;

    // Lista delle selezioni effettuate dall'utente
    private final List<String> userSelections = new ArrayList<>();


    public GestoreView(ListView<String> listView, TextField titleLabel) {
        this.listView = listView;
        this.titleLabel = titleLabel;
    }

    /**
     * Imposta il titolo della label in base alla query.
     */
    public void setTitleLabel(String query) {
        String formattedTitle = "Seleziona " +
                PropertiesFormatter.convertCamelCaseToSpaced((query));
        titleLabel.setText(formattedTitle);
    }

    /**
     * Popola la ListView con gli elementi forniti, inserendo lo spazio e le maiuscole.
     */
    public void populateListViewWithItems(Collection<String> allValues) {
        // Applica la formattazione a ciascun elemento della lista
        List<String> formattedValues = allValues.stream()
                .map(value -> PropertiesFormatter.convertCamelCaseToSpaced(value))
                .collect(Collectors.toList());

        // Crea una lista osservabile con i valori formattati
        ObservableList<String> observableItems = FXCollections.observableArrayList(formattedValues);
        listView.setItems(observableItems);
    }

    /**
     * Carica alla scena finale con le selezioni dell'utente.
     */
    public void navigateToFinalScene() {
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
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}


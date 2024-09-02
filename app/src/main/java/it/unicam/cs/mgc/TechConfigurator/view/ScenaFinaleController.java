package it.unicam.cs.mgc.TechConfigurator.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Questa classe rappresenta il controller che gestisce la scena finale dell'applicazione "Tech Configurator".
 * Permette la visualizzazione delle selezioni dell'utente in una tabella.
 */
public class ScenaFinaleController {

    // TableView per visualizzare le selezioni dell'utente
    @FXML
    private TableView<SelectionItem> tabella;

    // Colonna per le categorie di configurazione
    @FXML
    private TableColumn<SelectionItem, String> colonnaCategoria;

    // Colonna per i valori selezionati dall'utente
    @FXML
    private TableColumn<SelectionItem, String> colonnaSelezione;

    // Mappa che permette di associare a un Tech Object le etichette che verranno mostrate a schermo
    private final Map<String, String[]> mappaScelte = new HashMap<>();

    /**
     * Costruttore della classe, inizializza le categorie per ciascun TechObject.
     */
    public ScenaFinaleController() {
        mappaScelte.put("Computer", new String[]{"TechObject", "Colore", "Materiale","Categoria","Dimensione", "Disco Rigido", "Memoria RAM", "Processore", "Scheda Grafica"});
        mappaScelte.put("Monitor", new String[]{"TechObject", "Colore", "Materiale", "Dimensione", "Tipo Pannello", "Risoluzione"});
        mappaScelte.put("Mouse", new String[]{"TechObject", "Colore", "Materiale", "Dimensione", "DPI", "Numero Pulsanti", "Retroilluminazione", "Connessione"});
        mappaScelte.put("Tastiera", new String[]{"TechObject", "Colore", "Materiale", "Dimensione", "Tipo Tastiera", "Retroilluminazione"});
        mappaScelte.put("Cuffie", new String[]{"TechObject", "Colore", "Materiale", "Dimensione", "Connessione"});
    }

    /**
     * Classe interna per rappresentare un elemento di selezione con categoria e selezione.
     */
    public static class SelectionItem {
        private final String categoria;
        private final String selezione;

        public SelectionItem(String label, String value) {
            this.categoria = label;
            this.selezione = value;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getSelezione() {
            return selezione;
        }
    }

    /**
     * Inizializza il controller e imposta le proprietà delle colonne del TableView.
     * Permette di centrare il testo nelle colonne.
     */
    @FXML
    public void initialize() {
        // Imposta le colonne del TableView per collegare i dati agli attributi della classe SelectionItem
        colonnaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colonnaSelezione.setCellValueFactory(new PropertyValueFactory<>("selezione"));

        // Centra il testo nelle celle per la colonna delle categorie
        colonnaCategoria.setCellFactory(centraTesto());

        // Centra il testo nelle celle per la colonna delle selezioni
        colonnaSelezione.setCellFactory(centraTesto());

        // Imposta la stessa larghezza per entrambe le colonne
        double columnWidth = tabella.getPrefWidth() / 2;
        colonnaCategoria.setPrefWidth(columnWidth);
        colonnaSelezione.setPrefWidth(columnWidth);
    }

    /**
     * Crea delle celle con testo centrato per il TableView.
     */
    private Callback<TableColumn<SelectionItem, String>, TableCell<SelectionItem, String>> centraTesto() {
        return param -> {
            TableCell<SelectionItem, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null); // Cancella il testo se la cella è vuota
                    } else {
                        setText(item); // Imposta il testo della cella
                        setTextAlignment(TextAlignment.CENTER); // Allinea il testo al centro
                    }
                }
            };
            // Imposta l'allineamento al centro per le celle
            cell.setStyle("-fx-alignment: CENTER;");
            return cell;
        };
    }

    /**
     * Imposta le selezioni dell'utente nel TableView e le visualizza con le categorie corrispondenti.
     *
     * @param userSelections le selezioni effettuate dall'utente
     */
    public void setUserSelections(List<String> userSelections) {
        if (userSelections == null || userSelections.isEmpty()) {
            showError("Nessuna selezione effettuata."); // Mostra un errore se la lista è vuota
            return;
        }
        // Il primo elemento nella lista dovrebbe essere il TechObject selezionato dall'utente
        String selectedTechObject = userSelections.get(0);

        // Recupera le categorie associate al TechObject selezionato
        String[] categorie = mappaScelte.getOrDefault(selectedTechObject, new String[]{});
        ObservableList<SelectionItem> observableSelections = FXCollections.observableArrayList();

        // Associa ogni elemento della lista userSelections a una descrizione
        for (int i = 0; i < userSelections.size(); i++) {
            String categoria;
            if (i < categorie.length) {
                categoria = categorie[i]; // Associa l'elemento alla categoria corrispondente
            } else {
                categoria = "Opzione " + (i + 1); // Usa una descrizione generica se mancano categorie
            }
            observableSelections.add(new SelectionItem(categoria, userSelections.get(i)));
        }

        // Imposta gli elementi nel TableView
        tabella.setItems(observableSelections);
    }

    /**
     * Mostra un messaggio di errore.
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
}

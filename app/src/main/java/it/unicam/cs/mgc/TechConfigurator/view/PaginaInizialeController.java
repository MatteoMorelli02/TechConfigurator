package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Questa classe rappresenta il controller per la pagina iniziale dell'applicazione "Tech Configurator".
 * Gestisce l'interazione con l'interfaccia utente della scena iniziale
 * e la navigazione verso la scena di configurazione.
 */
public class PaginaInizialeController {

    /**
     * Riferimento al pulsante che è stato definito nel file FXML.
     * Quando viene cliccato permette di avviare la configurazione.
     */
    @FXML
    private Button bottoneStart;

    private Controller appController;
    public PaginaInizialeController() {}

    /**
     * Imposta il controller principale dell'applicazione.
     * Questo metodo permette di passare il controller principale alla scena iniziale.
     *
     * @param appController il controller principale dell'applicazione
     */
    public void setAppController(Controller appController) {
        this.appController = appController;
    }

    /**
     * Gestisce l'evento per il clic del pulsante 'bottoneStart'.
     * Carica la scena di configurazione e cambia la scena corrente con quella nuova.
     */
    @FXML
    private void iniziaConfigurazione() {
        try {
            // Carica il file FXML per la nuova scena di configurazione
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScenaConfigurazione.fxml"));
            Parent configuratorRoot = loader.load();

            // Ottiene il controller associato alla nuova scena di configurazione
            ScenaConfiguratoreController configuratorController = loader.getController();

            // Passa il controller principale al nuovo controller
            configuratorController.setAppController(appController);

            // Cambia la scena corrente con la nuova scena di configurazione
            Stage stage = (Stage) bottoneStart.getScene().getWindow();
            stage.setScene(new Scene(configuratorRoot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

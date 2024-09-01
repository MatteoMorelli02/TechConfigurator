package it.unicam.cs.mgc.TechConfigurator;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import it.unicam.cs.mgc.TechConfigurator.view.PaginaInizialeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Questa è la classe principale dell'applicazione "Tech Configurator".
 * Questa classe estende {@link Application} e rappresenta il punto di ingresso dell'applicazione.
 * Permette di avviare l'interfaccia utente e di impostare la scena iniziale.
 */
public class App extends Application {

    /**
     * Metodo principale di JavaFX che viene chiamato quando viene avviata l'applicazione.
     * Imposta la finestra principale con la scena iniziale caricata da un file FXML.
     *
     * @param primaryStage la finestra principale dell'applicazione
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica il file FXML che rappresenta l'interfaccia utente della scena iniziale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScenaIniziale.fxml"));
            Parent root = loader.load();

            // Ottiene il controller della pagina iniziale dalla scena caricata
            PaginaInizialeController initialController = loader.getController();

            // Inizializza il controller principale e lo passa al controller della pagina iniziale
            initialController.setAppController(new Controller());

            // Imposta il titolo della finestra principale
            primaryStage.setTitle("Tech Configurator");

            // Imposta la scena principale della finestra come quella definita nel file FXML
            primaryStage.setScene(new Scene(root));

            // Mostra la finestra dell'applicazione all'utente
            primaryStage.show();
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante il caricamento del file FXML e stampa il messaggio di errore
            e.printStackTrace();
            System.err.println("Errore durante il caricamento del file FXML: " + e.getMessage());
        }
    }

    /**
     * Metodo main che avvia l'applicazione JavaFX.
     * Questo metodo chiama {@link #launch(String...)} per iniziare l'esecuzione dell'applicazione.
     *
     * @param args argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        // Lancia l'applicazione JavaFX, che a sua volta chiamerà il metodo 'start'
        launch(args);
    }
}

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

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo principale di JavaFX che viene chiamato quando viene avviata l'applicazione.
     * Imposta la finestra principale con la scena iniziale caricata da un file FXML.
     *
     * @param primaryStage la finestra principale dell'applicazione
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ScenaIniziale.fxml"));
            Parent root = loader.load();
            PaginaInizialeController initialController = loader.getController();
            initialController.setAppController(new Controller());
            primaryStage.setTitle("Tech Configurator");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante il caricamento del file FXML
            // e stampa il messaggio di errore
            e.printStackTrace();
            System.err.println("Errore durante il caricamento del file FXML: " + e.getMessage());
        }
    }
}

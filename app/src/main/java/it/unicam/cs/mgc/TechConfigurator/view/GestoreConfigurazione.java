package it.unicam.cs.mgc.TechConfigurator.view;

import it.unicam.cs.mgc.TechConfigurator.controller.Controller;
import it.unicam.cs.mgc.TechConfigurator.model.ParsedData;
import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Questa classe gestisce le configurazioni dei differenti tipi di Tech Object.
 * Imposta le azioni appropriate per il controller della scena di configurazione.
 */
public class GestoreConfigurazione {

    private final Map<String, String[]> configurazioni = new HashMap<>();

    public GestoreConfigurazione() {
        configurazioni.put("Computer", new String[]{
                "CategorieComputer", "Dimensioni", "DiscoRigido", "MemoriaRam", "Processore", "SchedaGrafica"
        });
        configurazioni.put("Monitor", new String[]{
                "Dimensioni", "TipoPannello", "Risoluzione"
        });
        configurazioni.put("Mouse", new String[]{
                "Dimensioni", "Dpi", "NumPulsanti", "Retroilluminazioni", "Connessioni"
        });
        configurazioni.put("Tastiera", new String[]{
                "Dimensioni", "Tastiera", "Retroilluminazioni"
        });
        configurazioni.put("Cuffie", new String[]{
                "Dimensioni", "Connessioni"
        });
    }

    /**
     * Applica la configurazione per un tipo specifico di Tech Object e imposta le azioni nel controller.
     *
     * @param tipo il tipo di dispositivo per il quale applicare la configurazione.
     * @param controller il controller della scena di configurazione al quale applicare le configurazioni.
     */
    public void applicaConfigurazione(String tipo, ScenaConfiguratoreController controller) {
        String[] items = configurazioni.get(tipo);
        if (items != null) {
            controller.getActions().clear();
            for (String item : items) {
                controller.getActions().add(creaAzione(item, controller));
            }
            controller.setCurrentActionIndex(0);
            controller.setBottoneNextAction(creaAzioneBottone(controller));
        } else {
            System.out.println("Oggetto selezionato non riconosciuto: " + tipo);
        }
    }

    /**
     * Questo metodo crea un'azione per la configurazione.
     * Questa azione aggiorna la ListView nel controller con le categorie specificate.
     *
     * @param item il nome dell'elemento di configurazione
     * @param controller il controller della scena di configurazione
     * @return un Consumer<ActionEvent> che aggiorna la ListView
     */
    private Consumer<ActionEvent> creaAzione(String item, ScenaConfiguratoreController controller) {
        return event -> populateListView(item, controller);
    }

    /**
     * Crea l'handler dell'evento per il pulsante "Avanti" nel controller.
     * Questo handler chiama il metodo del controller associato al pulsante.
     *
     * @param controller il controller della scena di configurazione
     * @return un EventHandler<ActionEvent> per gestire il clic del pulsante
     */
    private EventHandler<ActionEvent> creaAzioneBottone(ScenaConfiguratoreController controller) {
        return event -> controller.configurationButton(event);
    }


    /**
     * Popola la ListView con le opzioni basate sul tipo di query fornito.
     */
    void populateListView(String query, ScenaConfiguratoreController controller) {
        controller.setQuery(query);
        setTitleLabel(query, controller.getTitleLabel());
        try {
            ParsedData parsedData = configura(query, controller.getAppController(), controller.getSelectedObject(), controller.getSelectedTechObject());
            populateListViewWithItems(parsedData.getAllValues(), controller.getListView());
            updateListView(controller.getListView());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'invocazione del metodo: " + e.getMessage());
        }
    }

    /**
     * Imposta il titolo della label in base alla query.
     */
    private void setTitleLabel(String query, TextField titleLabel) {
        String formattedTitle = "Seleziona " + PropertiesFormatter.convertCamelCaseToSpaced(query);
        titleLabel.setText(formattedTitle);
    }

    /**
     * Recupera i dati in base alla query fornita.
     */
    private ParsedData configura(String query, Controller appController, String selectedObject, String selectedTechObject) {
        if (query.equals("Dimensioni")) {
            return configuraDimensione(appController, selectedObject, selectedTechObject);
        } else {
            return configuraQuery(query, appController);
        }
    }

    /**
     * Gestisce la configurazione per la query "Dimensioni".
     */
    private ParsedData configuraDimensione(Controller appController, String selectedObject, String selectedTechObject) {
        if (selectedObject != null && (selectedObject.equals("Case") || selectedObject.equals("Portatile"))) {
            return appController.getListaDimensioni(selectedObject);
        } else {
            return appController.getListaDimensioni(selectedTechObject);
        }
    }

    /**
     * Invoca dinamicamente un metodo sull'appController basandosi sulla query.
     */
    private ParsedData configuraQuery(String query, Controller appController) {
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
    private void populateListViewWithItems(Collection<String> allValues, ListView<String> listView) {
        List<String> formattedValues = allValues.stream()
                .map(value -> PropertiesFormatter.convertCamelCaseToSpaced(value))
                .collect(Collectors.toList());
        ObservableList<String> observableItems = FXCollections.observableArrayList(formattedValues);
        listView.setItems(observableItems);
    }

    /**
     * Questo metodo permette di modificare la dimensione della listview in base
     * al numero di elementi al suo interno.
     */
    private void updateListView(ListView<String> listView) {
        double itemHeight = 25.0;
        int numItems = listView.getItems().size();
        double newHeight = Math.min(itemHeight * numItems, 400.0);
        listView.setPrefHeight(newHeight);
    }
}
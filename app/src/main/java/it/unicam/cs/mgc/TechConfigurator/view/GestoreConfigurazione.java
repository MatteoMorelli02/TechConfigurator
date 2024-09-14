package it.unicam.cs.mgc.TechConfigurator.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
            // Se il Tech Object non viene riconosciuto viene mostrato un messaggio di errore.
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
        return event -> controller.populateListView(item);
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
}

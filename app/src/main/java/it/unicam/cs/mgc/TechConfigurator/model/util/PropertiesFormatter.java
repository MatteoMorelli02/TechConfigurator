package it.unicam.cs.mgc.TechConfigurator.model.util;


/**
 * Questa classe ontiene una serie di metodi statici utilizzati per formattare e pulire stringhe RDF.
 */
public class PropertiesFormatter {

    /**
     * Rimuove tipi superflui (come "Thing" e "NamedIndividual") da una stringa RDF.
     * @param input la string RDF.
     * @return la stringa senza elementi non necessari.
     */
    public static String removeDefaultTypes(String input) {
        input = input.replace("Thing, ", "");
        input = input.replace(", NamedIndividual", "");
        return input;
    }

    /**
     * Questo metodo estrae la risorsa finale da una URI, eliminando il prefisso
     * @param uri L'URI completo da formattare.
     * @return la risorsa come una stringa.
     */
    public static String removeUriPrefix(String uri) {
        if(uri != null) {
            int index = uri.indexOf('#');
            if (index != -1) return uri.substring(index + 1);
            else return uri;
        } else {
            return "null";
        }
    }

    /**
     * Converte una stringa in formato camelCase in una stringa con parole separate da spazi.
     * @param camelCase la stringa nel formato camel case.
     * @return la stringa con gli spazi.
     */
    public static String convertCamelCaseToSpaced(String camelCase) {
        StringBuilder spacedString = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char currentChar = camelCase.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                if (i > 0) spacedString.append(' ');
                spacedString.append(Character.toLowerCase(currentChar));
            } else {
                spacedString.append(currentChar);
            }
        }
        return spacedString.toString();
    }

    public static String upperCaseFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}

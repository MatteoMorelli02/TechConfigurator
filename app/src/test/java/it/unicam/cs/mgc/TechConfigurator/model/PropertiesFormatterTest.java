package it.unicam.cs.mgc.TechConfigurator.model;

import it.unicam.cs.mgc.TechConfigurator.model.util.PropertiesFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertiesFormatterTest {

    @Test
    public void testRemoveDefaultType(){
        String test = "Thing, Colore, Materiale, NamedIndividual";
        String result = PropertiesFormatter.removeDefaultTypes(test);
        assertEquals("Colore, Materiale", result);
    }

    @Test
    public void testRemoveUriPrefix(){
        String test = "http://www.w3.org/2000/01/rdf-schema#type";
        String result = PropertiesFormatter.removeUriPrefix(test);
        assertEquals("type", result);
    }

    @Test
    public void testConvertCamelCaseToSpaced(){
        String test = "DiscoRigido";
        String result = PropertiesFormatter.convertCamelCaseToSpaced(test);
        assertEquals("disco rigido", result);
    }

    @Test
    public void testUpperCaseFirstLetter(){
        String test = "disco rigido";
        String result = PropertiesFormatter.upperCaseFirstLetter(test);
        assertEquals("Disco rigido", result);
    }

}

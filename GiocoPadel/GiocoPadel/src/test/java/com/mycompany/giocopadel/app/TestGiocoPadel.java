/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.GiocoPadel;
import com.mycompany.giocopadel.app.domain.Padeleur;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
        
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gerar
 */
public class TestGiocoPadel {
    static GiocoPadel giocoPadel;
    static Padeleur persona1;
    
    @BeforeAll
    public static void initTest() throws ParseException {
        giocoPadel = GiocoPadel.getInstance();
        persona1=giocoPadel.getElencoPadeleur().get("ABC123");
    }

    @Test
    public void testUC1() {
        try {
            verificaElencoPadeleur();
            verificaDettagliPersona1();
            verificaEsistenzaPadeleur();
            verificaNonEsistenzaPadeleur();
            svuotaFilePadeleur();
            inserisciNuovoPadeleur();
            salvaPadeleurSuFile();
            verificaPadeleurSalvatoSuFile();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
    }
}

    private void verificaElencoPadeleur() {
        Map<String, Padeleur> elencoPadeleur = giocoPadel.getElencoPadeleur();
        assertNotNull(elencoPadeleur);
        assertEquals(1, elencoPadeleur.size());
    }

    private void verificaDettagliPersona1() {
        assertEquals("Mario", persona1.getNome());
        assertEquals("Rossi", persona1.getCognome());
        assertEquals("ABC123", persona1.getCodiceFiscale());
        assertEquals("01/01/1990", new SimpleDateFormat("dd/MM/yyyy").format(persona1.getDataDiNascita()));
        assertEquals("mario.rossi@example.com", persona1.getEmail());
    }

    private void verificaEsistenzaPadeleur() {
        assertTrue(giocoPadel.verificaEsistenzaPadeleur("mario.rossi@example.com"));
    }

    private void verificaNonEsistenzaPadeleur() {
        assertFalse(giocoPadel.verificaEsistenzaPadeleur("nonEsiste@example.com"));
    }

    private void svuotaFilePadeleur() {
        giocoPadel.svuotaFilePadeleur();
        Padeleur padeleurSalvato = giocoPadel.caricaPadeleurDaFile();
        assertNull(padeleurSalvato);
    }

    private void inserisciNuovoPadeleur() throws ParseException {
        giocoPadel.inserisciNuovoPadeleur("Luigi", "Verdi", "GHI789", new SimpleDateFormat("dd/MM/yyyy").parse("10/12/1988"), "luigi.verdi@example.com");
    }

    private void salvaPadeleurSuFile() {
        giocoPadel.salvaPadeleurSuFile();
    }

    private void verificaPadeleurSalvatoSuFile() throws ParseException {
        Padeleur padeleurSalvato = giocoPadel.caricaPadeleurDaFile();
        assertNotNull(padeleurSalvato);
        assertEquals("Luigi", padeleurSalvato.getNome());
        assertEquals("Verdi", padeleurSalvato.getCognome());
        assertEquals("GHI789", padeleurSalvato.getCodiceFiscale());
        assertEquals("10/12/1988", new SimpleDateFormat("dd/MM/yyyy").format(padeleurSalvato.getDataDiNascita()));
        assertEquals("luigi.verdi@example.com", padeleurSalvato.getEmail());
    }

}
   
   


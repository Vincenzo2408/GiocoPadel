/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.GiocoPadel;
import com.mycompany.giocopadel.app.domain.Magazzino;
import com.mycompany.giocopadel.app.domain.Padeleur;
import com.mycompany.giocopadel.app.domain.Prenotazione;
import com.mycompany.giocopadel.app.domain.RichiestaAttrezzatura;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    
    static Prenotazione prenotazione;
    static RichiestaAttrezzatura richiestaAttrezzatura;
    static Magazzino magazzino;
    
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
         
            inserisciNuovoPadeleur();
            salvaPadeleurSuFile();
            
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

    private void inserisciNuovoPadeleur() throws ParseException {
        giocoPadel.inserisciNuovoPadeleur("Luigi", "Verdi", "GHI789", new SimpleDateFormat("dd/MM/yyyy").parse("10/12/1988"), "luigi.verdi@example.com");
    }

    private void salvaPadeleurSuFile() {
        giocoPadel.salvaPadeleurSuFile();
    }

    @Test
    public void testUC2() {
        try {
            verificaInserimentoNuovaPrenotazione();
            verificaInserimentoAttrezzatura();
            verificaControlloPrenotazione();
            verificaConfermaNuovaPrenotazione();
            
            salvaPrenotazioneSuFile();
            salvaMagazzinoSuFile();
            
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
        }
    }
    
    private void verificaInserimentoNuovaPrenotazione() throws ParseException {
        giocoPadel.inserisciNuovaPrenotazione(10, new SimpleDateFormat("dd/MM/yyyy").parse("20/07/2023"), Time.valueOf(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("HH:mm"))),  Time.valueOf(LocalTime.parse("11:00", DateTimeFormatter.ofPattern("HH:mm"))), "mario.rossi@example.com", "laura.bianchi@example.com", "francesco.marrone@example.com", "vincenzo.giallo@example.com", Boolean.parseBoolean("false"), 2);
    }
    
    private void verificaInserimentoAttrezzatura() throws ParseException {
        giocoPadel.inserimentoAttrezzatura(4, 4);
    }
    
    private void verificaControlloPrenotazione()throws ParseException {
        assertTrue(giocoPadel.ControlloPrenotazione(1, new SimpleDateFormat("dd/MM/yyyy").parse("18/05/2020"), Time.valueOf(LocalTime.parse("15:00", DateTimeFormatter.ofPattern("HH:mm"))), Time.valueOf(LocalTime.parse("17:00", DateTimeFormatter.ofPattern("HH:mm")))));
    }
    
    private void verificaConfermaNuovaPrenotazione(){
        giocoPadel.confermaNuovaPrenotazione();
    }
    
    private void salvaPrenotazioneSuFile() {
        giocoPadel.salvaPrenotazioneSuFile(prenotazione);
    }
    
    private void salvaMagazzinoSuFile() {
        giocoPadel.salvaMagazzinoSuFile(magazzino);
    }
}

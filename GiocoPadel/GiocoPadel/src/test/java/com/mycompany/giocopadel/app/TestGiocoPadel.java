/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
        

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gerar
 */
public class TestGiocoPadel {
    static GiocoPadel giocoPadel;
    static Padeleur personaEsistente;
    String nome;
    String cognome;
    String email;
    String codiceFiscale;
    Date dataDiNascita;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Map<String, Padeleur> elencoPadeleur;
    
    String email2;
    String email3;
    String email4;
    boolean attrezzaturaRichiesta;
    Date giornoPrenotazione;
    Time oraInizio;
    Time oraFine;
    int idCampo;
    int idPrenotazione;
    int numeroRacchette;
    int numeroPalline;
    Map<Integer, Prenotazione> elencoPrenotazioni;
    
    @BeforeAll
    public static void initTestGiocoPadel() {
        giocoPadel = GiocoPadel.getInstance();
    }
    
    @BeforeEach
    public void initTest() throws ParseException{
       nome = "Maria";
       cognome = "De Filippi";
       email = "mariadefilippi@example.com";
       codiceFiscale = "MRA789";
       String dataInput = "05/12/1961";
       dataDiNascita = sdf.parse(dataInput);   
       personaEsistente = giocoPadel.getPadeleurByEmail("mario.rossi@example.com");
       
       email2 = "barbaradurso@example.com";
       email3 = "francescomeli@example.com";
       email4 = "vincenzomicieli@example.com";
       String dataPrenotazione = "08/08/2023";
       giornoPrenotazione = sdf.parse(dataPrenotazione);
       String oraInizioString = "15:40";
       LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
       oraInizio = Time.valueOf(localTimeInizio);
       String oraFineString="16:30";
       LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
       oraFine = Time.valueOf(localTimeFine);
       idCampo = 2;
       idPrenotazione = 22;
       numeroRacchette = 4;
       numeroPalline = 4;
    }
    
    @AfterEach
    public void clearTest(){
        giocoPadel.rimuoviPadeleur(email);
        nome = null;
        cognome = null;
        email = null;
        codiceFiscale = null;
        dataDiNascita = null;
        
        email2 = null;
        email3 = null;
        email4 = null;
        giornoPrenotazione = null;
        oraInizio = null;
        oraFine = null;
        idCampo = 0;
        idPrenotazione = 0;
        numeroRacchette = 0;
        numeroPalline = 0;
    }

    @Test
    @DisplayName("Test UC1")
    public void testUC1() {
        try {
            //Verifica che un padeleur esiste all'interno dell'elenco
            assertTrue(giocoPadel.verificaEsistenzaPadeleur(personaEsistente.getEmail()), "Errore nella verifica esistenza padeleur");
            System.out.println("Verificato esistenza padeleur");
            
            //Verifica che un padeleur non esiste all'interno dell'elenco
            assertFalse(giocoPadel.verificaEsistenzaPadeleur(email),"Errore nella non verifica esistenza padeleur");
            System.out.println("Verificato non Esistenza Padeleur");
            
            //Verifica inserimento di un padeleur
            giocoPadel.inserisciNuovoPadeleur(nome, cognome, codiceFiscale, dataDiNascita, email);
            System.out.println("Inserimento Padeleur avvenuto con successo");
            
            //Conferma inserimento di un padeleur
            giocoPadel.confermaNuovoPadeleur();
            System.out.println("Conferma Inserimento Padeleur avvenuto con successo");
            
            
            //Verifica nell'elenco inserimento di Maria De Filippi
            elencoPadeleur = giocoPadel.getElencoPadeleur();
                    System.out.println("Elenco dei Padeleur:");
                    for (Padeleur padeleur : elencoPadeleur.values()) {
                        System.out.println("Codice fiscale: " + padeleur.getCodiceFiscale());
                        System.out.println("Nome: " + padeleur.getNome());
                        System.out.println("Cognome: " + padeleur.getCognome());
                        System.out.println("Data di nascita: " + sdf.format(padeleur.getDataDiNascita()));
                        System.out.println("Email: " + padeleur.getEmail());
                        System.out.println("---------------");
                    }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
        }
    }   
    
    
    @Test
    @DisplayName("Test UC2")
    public void testUC2() {
        try {
            //La verifica tramite email che un padeleur esiste o non esiste all'interno dell'elenco è stata effettuata nell'UC1
            
            //Verifica controllo di una prenotazione
            giocoPadel.ControlloPrenotazione(idCampo, giornoPrenotazione, oraInizio, oraFine);
            System.out.println("Controllo Prenotazione avvenuta con successo");
            
            //Verifica inserimento di una nuova prenotazione
            //Caso attrezzaturaRichiesta = true
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, true, idCampo, numeroRacchette, numeroPalline);
            System.out.println("Inserimento Prenotazione avvenuto con successo");
            
            //Verifica inserimento di una nuova prenotazione
            //Caso attrezzaturaRichiesta = false
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, false, idCampo, 0, 0);
            System.out.println("Inserimento Prenotazione avvenuto con successo");
            
            //Conferma inserimento di una prenotazione
            giocoPadel.confermaNuovaPrenotazione();
            System.out.println("Conferma Inserimento Prenotazione avvenuto con successo");
            
            //Verifica nell'elenco inserimento della prenotazione
            elencoPrenotazioni = giocoPadel.getElencoPrenotazioni();
                    System.out.println("Elenco delle prenotazioni:");
                    for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                        System.out.println("Id prenotazione: " + prenotazione.getIdPrenotazione());
                        System.out.println("Richiesta Attrezzatura: " + prenotazione.isAttrezzaturaRichiesta());
                        System.out.println("Giorno prenotazione: " + prenotazione.getGiornoPrenotazione());
                        System.out.println("Ora Inizio: " + prenotazione.getOraInizio());
                        System.out.println("Ora Fine: " + prenotazione.getOraFine());
                        System.out.println("Costo Prenotazione: " + prenotazione.getCostoPrenotazione());
                        System.out.println("Email 1: " + prenotazione.getOrganizzatore());
                        System.out.println("Email 2: " + prenotazione.getPartecipante2());
                        System.out.println("Email 3: " + prenotazione.getPartecipante3());
                        System.out.println("Email 4: " + prenotazione.getPartecipante4());
                        System.out.println("Id campo: " + prenotazione.getCampoPadel());
                        System.out.println("---------------");
                    }    
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
        }
    }   
}

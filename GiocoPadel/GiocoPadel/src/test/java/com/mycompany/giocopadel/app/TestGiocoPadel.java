/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import com.mycompany.giocopadel.app.domain.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
        

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
    int idPrenotazione;
    
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
    
    int numeroRacchette;
    int numeroPalline;
    Map<Integer, Prenotazione> elencoPrenotazioni;
    Map<String, Magazzino> elencoMagazzino;
    Map<Integer, CampoPadel> elencoCampiPadel;
    Magazzino magazzino;
    String idGiorno;
   
    float costoRipristinato;
    
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
       
       email2 = "loredana.blu@example.com";
       email3 = "francesco.marrone@example.com";
       email4 = "luigi.verdi@example.com";
       String dataPrenotazione = "08/10/2023";
       // String dataPrenotazione = "" per verifica rimozione rimborso;
       giornoPrenotazione = sdf.parse(dataPrenotazione);
       String oraInizioString = "15:40";
       LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
       oraInizio = Time.valueOf(localTimeInizio);
       String oraFineString="16:30";
       LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
       oraFine = Time.valueOf(localTimeFine);
       idCampo = 2;
       idPrenotazione=0;
       numeroRacchette = 4;
       numeroPalline = 4;
       magazzino = new Magazzino("0",0,0);
       
      
    }
    
    @AfterEach
    public void clearTest(){
        giocoPadel.rimuoviPrenotazioneDaFile(idPrenotazione);
        giocoPadel.rimuoviPadeleur(email);
        giocoPadel.rimuoviMagazzinoDaFile(idGiorno);
        
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
        idPrenotazione=0;
        
        numeroRacchette = 0;
        numeroPalline = 0;
        magazzino = null;
    }

    @Test
    @DisplayName("Test UC1")
    public void testUC1() {
        try { 
            //Verifica che un padeleur esiste all'interno dell'elenco
            System.out.println("\n1.1 Verifica esistena del padeleur" + personaEsistente.getEmail());
            assertTrue(giocoPadel.verificaEsistenzaPadeleur(personaEsistente.getEmail()), "1.1: Errore nella verifica esistenza padeleur");
            System.out.println("1.1 Il padeleur esiste in elenco");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica che un padeleur non esiste all'interno dell'elenco
            System.out.println("1.2 Verifica di non esistenza del padeleur "+ email);
            assertFalse(giocoPadel.verificaEsistenzaPadeleur(email),"1.2: Errore nella non verifica esistenza padeleur");
            System.out.println("1.2 Il padeleur non esiste in elenco");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica inserimento di un padeleur
            System.out.println("1.3 Verifica l'inserimento corretto di un Padeleur con i seguenti dati: "+ nome + " "+ cognome + " "+ codiceFiscale + " " + dataDiNascita + " " + email);
            giocoPadel.inserisciNuovoPadeleur(nome, cognome, codiceFiscale, dataDiNascita, email);
            System.out.println("1.3 Inserimento Padeleur avvenuto con successo");
            System.out.println("----------------------------------------------------------------------");
            
            //Conferma inserimento di un padeleur
            System.out.println("1.4 Verifica l'inserimento del padeleur precedente in elenco");
            giocoPadel.confermaNuovoPadeleur();
            System.out.println("1.4 Conferma Inserimento Padeleur avvenuto con successo \n");
            
            //Verifica nell'elenco inserimento di Maria De Filippi
            elencoPadeleur = giocoPadel.getElencoPadeleur();
                    System.out.println("Elenco dei Padeleur:");
                    for (Padeleur padeleur : elencoPadeleur.values()) {
                        System.out.println("Codice fiscale: " + padeleur.getCodiceFiscale());
                        System.out.println("Email: " + padeleur.getEmail());
                    }
            System.out.println("----------------------------------------------------------------------");
            
            } catch (Exception e) {
                fail("Errore: " + e.getMessage());
        }
    }   
    
    @Test
    @DisplayName("Test UC2")
    public void testUC2() {
        try {
            //La verifica tramite email che un padeleur esiste o non esiste all'interno dell'elenco è stata effettuata nell'UC1
            
            //Verifica disponibilità di un campo (esito positivo)
            System.out.println("2.1 Verifica se il campo " + idCampo + "è disponibile nel giorno " + sdf.format(giornoPrenotazione) + "dalle " + oraInizio + " alle " + oraFine);
            assertTrue(giocoPadel.ControlloPrenotazione(idCampo, giornoPrenotazione, oraInizio, oraFine), "2.1 Errore: il campo non è disponibile");
            System.out.println("2.1 Il campo è disponibile");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica inserimento di una prenotazione. Caso attrezzatura=true ed attrezzature disponibili
            System.out.println("2.2 Inserimento di una prenotazione: caso attrezzatura richiesta true ed attrezzature disponibili");
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, true, idCampo, numeroRacchette, numeroPalline);
            System.out.println("2.2 Inserimento avvenuto con successo");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica inserimento di una prenotazione. Caso attrezzatura=true ma attrezzature non disponibili con continuazione della prenotazione
            System.out.println("2.3 Inserimento di una prenotazione: caso attrezzatura richiesta true ed attrezzature non disponibili con continuazione della prenotazione");
            // Esegui il test senza richiedere input da tastiera settando il System.in
            String input="1";
            InputStream stream = new ByteArrayInputStream(input.getBytes());
            System.setIn(stream);
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, true, idCampo, 100, 100);
            // Ripristina System.in
            System.setIn(System.in);         
            System.out.println("2.3 Inserimento avvenuto con successo");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica inserimento di una prenotazione. Caso attrezzatura=true ma attrezzature non disponibili con annullamento della prenotazione
            System.out.println("2.4 Inserimento di una prenotazione: caso attrezzatura richiesta true ed attrezzature non disponibili con annullamento della prenotazione");
            // Esegui il test senza richiedere input da tastiera settando il System.in
            input="0";
            stream = new ByteArrayInputStream(input.getBytes());
            System.setIn(stream);
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, true, idCampo, 100, 100);
            // Ripristina System.in
            System.setIn(System.in);         
            System.out.println("2.4 Annullamento della prenotazione");
            System.out.println("----------------------------------------------------------------------");
            
            //Controllo della funzione inserimentoAttrezzatura in caso di attrezzature disponibili
            System.out.println("2.5 Controllo della funzione inserimentoAttrezzatura in caso di attrezzature disponibili");
            assertTrue(giocoPadel.inserimentoAttrezzatura(numeroRacchette, numeroPalline, magazzino), "2.4 Errore: le attrezzature non sono disponibili");
            System.out.println("2.5 Le attrezzature sono disponibili");
            System.out.println("----------------------------------------------------------------------");
            
            //Controllo della funzione inserimentoAttrezzatura in caso di attrezzature non disponibili
            System.out.println("2.6 Controllo della funzione inserimentoAttrezzatura in caso di attrezzature disponibili");
            // Esegui il test senza richiedere input da tastiera settando il System.in
            input="0";
            stream = new ByteArrayInputStream(input.getBytes());
            System.setIn(stream);
            assertFalse(giocoPadel.inserimentoAttrezzatura(100, 100, magazzino), "2.6 Errore: le attrezzature sono disponibili");
            // Ripristina System.in
            System.setIn(System.in);    
            System.out.println("2.6 Le attrezzature non sono disponibili");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica inserimento di una prenotazione. Caso attrezzatura=false
            System.out.println("2.7 Inserimento di una prenotazione: caso attrezzatura richiesta false");
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, false, idCampo, 0, 0);
            System.out.println("2.7 Inserimento Prenotazione avvenuto con successo");
            System.out.println("----------------------------------------------------------------------");
            
            //Conferma inserimento di una prenotazione
            System.out.println("2.8 Inserimento della prenotazione nell'elenco delle prenotazioni");
            giocoPadel.confermaNuovaPrenotazione();
            System.out.println("2.8 Conferma Inserimento Prenotazione avvenuto con successo");
            
            
            //Verifica nell'elenco inserimento della prenotazione
            elencoPrenotazioni = giocoPadel.getElencoPrenotazioni();
            
            
            System.out.println("Elenco delle prenotazioni:");
                for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                    System.out.println("Id prenotazione: " + prenotazione.getIdPrenotazione());      
                    idPrenotazione=prenotazione.getIdPrenotazione();
                }   
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica disponibilità di un campo (esito negativo)
            System.out.println("2.9 Verifica se il campo" + idCampo + "è disponibile nel giorno" + sdf.format(giornoPrenotazione) + "dalle" + oraInizio + " alle" + oraFine);
            assertFalse(giocoPadel.ControlloPrenotazione(idCampo, giornoPrenotazione, oraInizio, oraFine), "2.2 Errore: il campo è disponibile");
            System.out.println("2.9 Il campo non è disponibile");
            System.out.println("----------------------------------------------------------------------");
            
            } catch (Exception e) {
                fail("Errore: " + e.getMessage());
        }
    }   
    
    @Test
    @DisplayName("Test UC3")
    public void testUC3() {
        try {
            //La verifica tramite email che un padeleur esiste o non esiste all'interno dell'elenco è stata effettuata nell'UC1
            //La verifica che la prenotazione sia stata effettuata in maniera corretta è stata effettuata nell'UC2
            //La verifica del corretto inserimento di una nuova prenotazione è stata effettuata nell'UC2
            //La verifica della conferma di una nuova prenotazione è stata effettuata nell'UC2
            
            //Verifica della modifica di una prenotazione esistente 
            elencoPrenotazioni = giocoPadel.getElencoPrenotazioni();
            
            for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                if(prenotazione.getOrganizzatore().getEmail().compareTo(email)==0)
                    idPrenotazione=prenotazione.getIdPrenotazione();
            } 
            
            System.out.println("3.1 Verifica della possibilità di modifica di una prenotazione esistente e con id: " + idPrenotazione);  
            String input=Integer.toString(idPrenotazione);
            InputStream stream = new ByteArrayInputStream(input.getBytes());       
            System.setIn(stream);           
            //Verifica della modifica del prezzo
            System.out.println("3.1 Il prezzo prima della modifica e': " + elencoPrenotazioni.get(idPrenotazione).getCostoPrenotazione());
            assertTrue(giocoPadel.modificaPrenotazione(),"3.1: Errore: id non esistente");
            System.setIn(System.in);    
            System.out.println("3.1 La modifica può essere effettuata poiché esiste l'id prenotazione");
            giocoPadel.inserisciNuovaPrenotazione(idPrenotazione, giornoPrenotazione, oraInizio, oraFine, email, email2, email3, email4, true, idCampo, 5, 5);
            giocoPadel.confermaNuovaPrenotazione();
            //Verifica se il prezzo è stato modificato:
            elencoPrenotazioni = giocoPadel.getElencoPrenotazioni();
            
            for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                idPrenotazione=prenotazione.getIdPrenotazione();
            }   
                    
            System.out.println("3.1 Il prezzo aggiuntivo da versare risulta essere: " +elencoPrenotazioni.get(idPrenotazione).getCostoPrenotazione());
            
            //Verifica inserimento delle attrezzature nell'elenco magazzino, test non effettuato in UC2 in quanto infine abbiamo inserito una prenotazione con attrezzaturaRichiesta = false
            idGiorno=giocoPadel.creazioneidGiorno(elencoPrenotazioni.get(idPrenotazione));
            System.out.println("3.1 Controllo se esiste la seguente stringa nell'elencoMagazzino: "+idGiorno);
            elencoMagazzino=giocoPadel.getElencoMagazzino();
            for(Magazzino magazzino: elencoMagazzino.values()){
                System.out.println("IdMagazzino: "+ magazzino.getidGiorno());
                if(magazzino.getidGiorno().compareTo(idGiorno)==0){
                    System.out.println("3.1 Comparazione trovata");
                }
            }
            System.out.println("----------------------------------------------------------------------");
                      
            //Verifica della rimozione di una prenotazione [La prova di politica di rimborso da settare ad inizio codice a seconda del giorno dell'esame]
            
            for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                if(prenotazione.getOrganizzatore().getEmail().compareTo(email)==0)
                    idPrenotazione=prenotazione.getIdPrenotazione();
            } 
            
            System.out.println("3.2 Rimozione della prenotazione con id: "+idPrenotazione);
            input=Integer.toString(idPrenotazione);
            stream = new ByteArrayInputStream(input.getBytes());       
            System.setIn(stream);     
            assertTrue(giocoPadel.rimuoviPrenotazione(), "3.2 Errore: rimozione non avvenuta con successo");
            System.setIn(System.in);
            System.out.println("3.2 Rimozione Prenotazione avvenuta con successo");
            System.out.println("----------------------------------------------------------------------");
            
            //Verifica nell'elenco rimozione della prenotazione
            System.out.println("Elenco Id prenotazioni presenti nel Sistema:");
                for (Prenotazione prenotazione : elencoPrenotazioni.values()) {
                    System.out.println("Id prenotazione: " + prenotazione.getIdPrenotazione());   
                    idPrenotazione=prenotazione.getIdPrenotazione();
                }   
            idPrenotazione=-1;
            } catch (Exception e) {
                fail("Errore: " + e.getMessage());
        }
    }   
    
    @Test
    @DisplayName ("Test UC5") 
    public void TestUC5(){
       try{
            elencoPrenotazioni=giocoPadel.getElencoPrenotazioni();
            
            //Verifica conteggio delle partite nei campi da padel
            System.out.println("5.1 Prova del conteggio campo da padel");
            int i=0;
            for(i=1;i<4;i++){
                String input=Integer.toString(i);
                InputStream stream = new ByteArrayInputStream(input.getBytes());
                System.setIn(stream);
                giocoPadel.conteggioPartite();
                System.setIn(System.in);
            }
            System.out.println("5.1 Conteggio avvenuto correttamente");
            System.out.println("----------------------------------------------------------------------");   
       } catch (Exception e){
           fail("Errore: " +e.getMessage());
       }
    }
    
    @Test
    @DisplayName ("Test UC6") 
    public void TestUC6(){
       try{
           // Verifica della modifica prezzo di un campo da padel
           elencoCampiPadel=giocoPadel.getElencoCampiPadel();
           System.out.println("6.1 Modifica prezzo del campo da padel");
           String input="1 15,80";
           costoRipristinato=elencoCampiPadel.get(1).getPrezzo();
           
           InputStream stream = new ByteArrayInputStream(input.getBytes());
           System.setIn(stream);
           giocoPadel.modificaPrezzi();
           System.setIn(System.in);
            
           System.out.println("6.1 Modifica prezzo avvenuto con successo");
           
           for(CampoPadel campopadel: elencoCampiPadel.values()){
               System.out.println("CampoPadel: "+campopadel.getIdCampo()+" al prezzo di: " +campopadel.getPrezzo());
           }
           
           giocoPadel.modificaCostoFile(1, costoRipristinato);
           
           System.out.println("----------------------------------------------------------------------");   
       } catch (Exception e){
           fail("Errore: " +e.getMessage());
       }
    
    }
}

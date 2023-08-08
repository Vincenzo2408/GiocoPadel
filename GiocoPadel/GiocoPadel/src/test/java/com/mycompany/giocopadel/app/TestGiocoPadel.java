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
    
    @BeforeAll
    public static void initTestGiocoPadel() {
        giocoPadel = GiocoPadel.getInstance();
      
    }
    
    @BeforeEach
    public void initTest() throws ParseException{
       nome="Maria";
       cognome="De Filippi";
       email="mariadefilippi@example.com";
       codiceFiscale="MRA789";
       String dataInput="05/12/1961";
       dataDiNascita = sdf.parse(dataInput);   
       personaEsistente = giocoPadel.getPadeleurByEmail("mario.rossi@example.com");
    }
    
    @AfterEach
    public void clearTest(){
        giocoPadel.rimuoviPadeleur(email);
        nome=null;
        cognome=null;
        email=null;
        codiceFiscale=null;
        dataDiNascita=null;
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
}

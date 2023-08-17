/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import static com.mycompany.giocopadel.app.TestGiocoPadel.giocoPadel;
import com.mycompany.giocopadel.app.domain.*;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gerar
 */
public class TestPrenotazione {
    Map<Integer, Prenotazione> elencoPrenotazione;
    Map<String, Padeleur> elencoPadeleur;
    Map<Integer, CampoPadel> elencoCampoPadel;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Time oraInizio;
    Time oraFine;
      
   @BeforeAll
   public static void initTestGiocoPadel() {
       giocoPadel = GiocoPadel.getInstance();
   }
   
    @BeforeEach
    public void initTest() throws ParseException{
       String oraInizioString = "15:00";
       LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
       oraInizio = Time.valueOf(localTimeInizio);
       String oraFineString="16:00";
       LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
       oraFine = Time.valueOf(localTimeFine);
       Prenotazione prenotazione=new Prenotazione(-1, false ,sdf.parse("31/12/9999") ,oraInizio , oraFine ,12);
       elencoPrenotazione=giocoPadel.getElencoPrenotazioni();
       elencoPadeleur=giocoPadel.getElencoPadeleur();
       elencoCampoPadel=giocoPadel.getElencoCampiPadel();
       Padeleur organizzatore=new Padeleur("Paolo","Bonolis","PBN145",sdf.parse("14/06/1961"),"paolobonolis@example.com");
       Padeleur partecipante2=new Padeleur("Gerry","Scotti","GRR123",sdf.parse("07/08/1956"),"gerryscotti@example.com");
       Padeleur partecipante3=new Padeleur("Amadeus","Sebastiani","MDS786",sdf.parse("04/09/1962"),"amadeusamasanremo@example.com");
       Padeleur partecipante4=new Padeleur("Carlo","Conti","CRL538",sdf.parse("13/03/1961"),"carloconti@example.com");
       elencoPadeleur.put("paolobonolis@example.com", organizzatore);
       elencoPadeleur.put("gerryscotti@example.com",partecipante2);
       elencoPadeleur.put("amadeusamasanremo@example.com", partecipante3);
       elencoPadeleur.put("carloconti@example.com", partecipante4);
       prenotazione.setOrganizzatore(elencoPadeleur.get("paolobonolis@example.com"));
       prenotazione.setPartecipante2(elencoPadeleur.get("gerryscotti@example.com"));
       prenotazione.setPartecipante3(elencoPadeleur.get("amadeusamasanremo@example.com"));
       prenotazione.setPartecipante4(elencoPadeleur.get("carloconti@example.com"));
       prenotazione.setCampoPadel(elencoCampoPadel.get(1));
       elencoPrenotazione.put(-1, prenotazione);
    }
    
    @AfterEach
    public void afterTest() throws ParseException{
        elencoPadeleur.remove("paolobonolis@example.com");
        elencoPadeleur.remove("gerryscotti@example.com");
        elencoPadeleur.remove("amadeusamasanremo@example.com");
        elencoPadeleur.remove("carloconti@example.com");
        elencoPadeleur.remove("lucalaurenti@example.com");
        
       elencoPrenotazione.remove(-1);    
    }
 
    @Test
    public void testgetIdPrenotazione() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            int idPrenotazione=-1;
            assertEquals(idPrenotazione,prenotazione.getIdPrenotazione());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }
   
    @Test
    public void testsetIdPrenotazione() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            int idPrenotazione=99;
            prenotazione.setIdPrenotazione(idPrenotazione);
            assertEquals(prenotazione.getIdPrenotazione(),99);
            elencoPrenotazione.remove(99);       
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }
    
    @Test
    public void testgetGiornoPrenotazione() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            Date giornoTest= sdf.parse("31/12/9999");
            assertEquals(giornoTest,prenotazione.getGiornoPrenotazione());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetGiornoPrenotazione() {
       try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            Date giornoTest=sdf.parse("30/12/1999");
            prenotazione.setGiornoPrenotazione(giornoTest);
            assertEquals(giornoTest,prenotazione.getGiornoPrenotazione());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testgetOraInizio() {
      try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            String oraInizioString = "15:00";
            LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
            Time oraInizioTest = Time.valueOf(localTimeInizio);
            assertEquals(oraInizioTest, prenotazione.getOraInizio());
            
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetOraInizio() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            String oraInizioString = "10:00";
            LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
            Time oraInizioTest = Time.valueOf(localTimeInizio);
            prenotazione.setOraInizio(oraInizioTest);
            assertEquals(oraInizioTest, prenotazione.getOraInizio());
            
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testgetOraFine() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            String oraFineString = "16:00";
            LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
            Time oraFineTest = Time.valueOf(localTimeFine);
            assertEquals(oraFineTest, prenotazione.getOraFine());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetOraFine() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            String oraFineString = "11:00";
            LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
            Time oraFineTest = Time.valueOf(localTimeFine);
            prenotazione.setOraFine(oraFineTest);
            assertEquals(oraFineTest, prenotazione.getOraFine());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testgetCostoPrenotazione() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            float prezzoTest=12;
            assertEquals(prezzoTest,prenotazione.getCostoPrenotazione());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetCostoPrenotazione() {
      try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            float prezzoTest=17;
            prenotazione.setCostoPrenotazione(prezzoTest);
            assertEquals(prezzoTest,prenotazione.getCostoPrenotazione());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testgetOrganizzatore() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            String emailOrganizzatore="paolobonolis@example.com";
            assertEquals(emailOrganizzatore, prenotazione.getOrganizzatore().getEmail());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetOrganizzatore() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            Padeleur organizzatore=new Padeleur("Luca","Laurenti","LCL185",sdf.parse("26/04/1963"),"lucalaurenti@example.com");
            elencoPadeleur.put("lucalaurenti@example.com",organizzatore);
            prenotazione.setOrganizzatore(organizzatore);
            assertEquals(organizzatore.getEmail(), prenotazione.getOrganizzatore().getEmail());
            
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

   //Analoghi Test Organizzatore per i partecipanti
    
    @Test
    public void testgetCampoPadel() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            CampoPadel campopadel=elencoCampoPadel.get(1);
            assertEquals(campopadel,prenotazione.getCampoPadel());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testsetCampoPadel() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            prenotazione.setCampoPadel(elencoCampoPadel.get(2));
            assertEquals(2,prenotazione.getCampoPadel().getIdCampo());
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
    }

    @Test
    public void testcalcolaImportoTotale() {
        try{
            Prenotazione prenotazione=elencoPrenotazione.get(-1);
            RichiestaAttrezzatura richiestaAttrezzatura=new RichiestaAttrezzatura(5,5);
            Magazzino magazzino=new Magazzino("0",0,0);
            float costoAttrezzatura = 0.0f;
            
        
             costoAttrezzatura = calcolaCostoAttrezzatura(richiestaAttrezzatura, magazzino);
         

            float costo1=calcolaImporto(prenotazione.getCampoPadel(), prenotazione.getOraInizio(), prenotazione.getOraFine()) + costoAttrezzatura;
            float costo2=calcolaImporto(elencoCampoPadel.get(1), oraInizio, oraFine)+costoAttrezzatura;
            assertEquals(costo1, costo2);
        }catch(Exception e){
            fail("Errore: "+e.getMessage());
        }
        
    }
    
    public float calcolaImporto(CampoPadel campoPadel, Time oraInizio, Time oraFine) {
        double costoCampo=campoPadel.getCostoCampo(oraInizio, oraFine);
       
        return (float)costoCampo;
    }
    
    public float calcolaCostoAttrezzatura(RichiestaAttrezzatura richiestaAttrezzatura, Magazzino magazzino) {
        return richiestaAttrezzatura.getNumeroRacchette() * magazzino.getCostoSingoloAttrezzatura()
            + richiestaAttrezzatura.getNumeroPalline() * magazzino.getCostoSingoloAttrezzatura();
    }
}

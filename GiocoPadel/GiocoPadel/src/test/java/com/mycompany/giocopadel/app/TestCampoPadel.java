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
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author gerar
 */
public class TestCampoPadel {
   
   Time oraInizioTest;
   Time oraFineTest;
   
   Map<Integer, CampoPadel> elencoCampiPadel;
   
   @BeforeAll
   public static void initTestGiocoPadel() {
       giocoPadel = GiocoPadel.getInstance();
   }
   
    @BeforeEach
    public void initTest() throws ParseException{
       elencoCampiPadel=giocoPadel.getElencoCampiPadel();
       String oraInizioString = "15:40";
       LocalTime localTimeInizio = LocalTime.parse(oraInizioString, DateTimeFormatter.ofPattern("HH:mm"));
       oraInizioTest = Time.valueOf(localTimeInizio);
       String oraFineString="16:30";
       LocalTime localTimeFine = LocalTime.parse(oraFineString, DateTimeFormatter.ofPattern("HH:mm"));
       oraFineTest = Time.valueOf(localTimeFine);  
    }
    
   @Test
   public void testgetIdCampo(){
       try{
            int idCampoTest=1;
            assertEquals(idCampoTest, elencoCampiPadel.get(idCampoTest).getIdCampo());
       }
       catch (Exception e) {
            fail("Errore: " + e.getMessage());
       }
   }
   
   @Test
   public void testsetPrezzo(){
       try{
           int idCampoTest=1;
           CampoPadel campoPadelTest=giocoPadel.getCampoPadelById(idCampoTest);
           campoPadelTest.setPrezzo((float) 13.00);
           
           assertEquals((float)campoPadelTest.getPrezzo(),13.00);
          
       }
       catch (Exception e) {
            fail("Errore: " + e.getMessage());
       }
   }
   
   @Test
   public void testgetCostoCampo(){
       try{
           long durataPrenotazioneMillis = oraFineTest.getTime() - oraInizioTest.getTime();
           double durataPrenotazioneOre = durataPrenotazioneMillis / (1000.0 * 60.0 * 60.0); // Conversione da millisecondi a ore
           
           
           CampoPadel campoPadelTest=giocoPadel.getCampoPadelById(1);
           double prezzoCampoTest = durataPrenotazioneOre * campoPadelTest.getPrezzo();
           
           double prezzoCampo = campoPadelTest.getCostoCampo(oraInizioTest, oraFineTest);
           
           assertEquals(prezzoCampoTest,prezzoCampo);
           
           
       }
        catch (Exception e) {
            fail("Errore: " + e.getMessage());
       }
   }
}

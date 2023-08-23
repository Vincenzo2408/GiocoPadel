package com.mycompany.giocopadel.app;

import static com.mycompany.giocopadel.app.TestGiocoPadel.giocoPadel;
import com.mycompany.giocopadel.app.domain.*;
import java.text.ParseException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestMagazzino {
    Map<String, Magazzino> elencoMagazzino;
   
   @BeforeAll
   public static void initTestGiocoPadel() {
       giocoPadel = GiocoPadel.getInstance();
   }
   
    @BeforeEach
    public void initTest() throws ParseException{
       elencoMagazzino=giocoPadel.getElencoMagazzino();
       Magazzino magazzino=new Magazzino("31/12/999910:0011:001",10,10);
       elencoMagazzino.put(magazzino.getidGiorno(), magazzino);
    }
    
    @AfterEach
    public void afterTest() throws ParseException{
        elencoMagazzino.remove("31/12/999910:0011:001");
    }
    
    @Test
    public void testgetpallineTotali(){
      try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        int pallineTotali=20;
        assertEquals(pallineTotali,magazzino.getpallineTotali());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    public void testgetracchetteTotali(){
        try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        int racchetteTotali=20;
        assertEquals(racchetteTotali,magazzino.getracchetteTotali());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    public void testgetracchetteRichieste(){
        try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        int racchetteRichieste=10;
        assertEquals(racchetteRichieste,magazzino.getracchetteRichieste());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    public void testgetpallineRichieste(){
        try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        int pallineRichieste=10;
        assertEquals(pallineRichieste,magazzino.getpallineRichieste());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    public void testgetidGiorno(){
        try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        String idGiorno = "31/12/999910:0011:001";
        assertEquals(idGiorno,magazzino.getidGiorno());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    public void testgetCostoSingoloAttrezzatura() {
       try{
        Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
        float costoSingoloAttrezzatura = 2.0f;
        assertEquals(costoSingoloAttrezzatura,magazzino.getCostoSingoloAttrezzatura());
      }
      catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }

    @Test
    void testsetnumeroRacchette() {
        try{
            Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
            int numeroRacchetteTest=5;
            int numeroRacchetteRichiesteTest=magazzino.getracchetteRichieste();
            numeroRacchetteRichiesteTest=numeroRacchetteRichiesteTest+numeroRacchetteTest;
            magazzino.setnumeroRacchette(numeroRacchetteTest);
            assertEquals(numeroRacchetteRichiesteTest, magazzino.getracchetteRichieste());
        }
        catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }

    @Test
    void testsetnumeroPalline() {
        try{
            Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
            int numeroPallineTest=5;
            int numeroPallineRichiesteTest=magazzino.getpallineRichieste();
            numeroPallineRichiesteTest=numeroPallineRichiesteTest+numeroPallineTest;
            magazzino.setnumeroPalline(numeroPallineTest);
            assertEquals(numeroPallineRichiesteTest, magazzino.getpallineRichieste());
        }
        catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
    @Test
    void testsetRacchetteTotali() {
        try{
            Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
            int numeroRacchetteTest=5;
            int numeroRacchetteTotaliTest=magazzino.getracchetteTotali();
            numeroRacchetteTotaliTest=numeroRacchetteTotaliTest+numeroRacchetteTest;
            magazzino.setracchetteTotali(numeroRacchetteTest);
            assertEquals(numeroRacchetteTotaliTest, magazzino.getracchetteTotali());
        }
        catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
    
     @Test
    void testsetPallineTotali() {
        try{
            Magazzino magazzino = elencoMagazzino.get("31/12/999910:0011:001");
            int numeroPallineTest=10;
            int numeroPallineTotaliTest=magazzino.getpallineTotali();
            numeroPallineTotaliTest=numeroPallineTotaliTest+numeroPallineTest;
            magazzino.setpallineTotali(numeroPallineTest);
            assertEquals(numeroPallineTotaliTest, magazzino.getpallineTotali());
        }
        catch(Exception e) {
            fail("Errore: " + e.getMessage());
       }
    }
}

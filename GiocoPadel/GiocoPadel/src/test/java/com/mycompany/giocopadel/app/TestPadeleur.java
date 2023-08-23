package com.mycompany.giocopadel.app;

import static com.mycompany.giocopadel.app.TestGiocoPadel.giocoPadel;
import com.mycompany.giocopadel.app.domain.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPadeleur {
    
   Map<String, Padeleur> elencoPadeleur;
   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
   @BeforeAll
   public static void initTestGiocoPadel() {
       giocoPadel = GiocoPadel.getInstance();
   }
   
    @BeforeEach
    public void initTest() throws ParseException{
       elencoPadeleur=giocoPadel.getElencoPadeleur(); 
       Padeleur padeleur= new Padeleur("Paolo","Bonolis","PBN145",sdf.parse("14/06/1961"),"paolobonolis@example.com");
       elencoPadeleur.put("paolobonolis@example.com", padeleur);
    }
    
    @AfterEach
    public void afterTest() throws ParseException{
        elencoPadeleur.remove("paolobonolis@example.com");
    }
    
    @Test
    public void testgetEmail() {
        try{
            Padeleur padeleur = elencoPadeleur.get("paolobonolis@example.com");
            String emailTest="paolobonolis@example.com";
            assertEquals(emailTest,padeleur.getEmail());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }

    @Test
    public void testgetNome() {
     try{
         Padeleur padeleur = elencoPadeleur.get("paolobonolis@example.com");
         String nomeTest="Paolo";
         assertEquals(nomeTest,padeleur.getNome());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }

    @Test
    public void testgetCognome() {
     try{
         Padeleur padeleur=elencoPadeleur.get("paolobonolis@example.com");
         String cognomeTest="Bonolis";
         assertEquals(cognomeTest,padeleur.getCognome());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
   }

    @Test
  public void testgetCodiceFiscale() {
     try{
         Padeleur padeleur=elencoPadeleur.get("paolobonolis@example.com");
         String codiceFiscaleTest="PBN145";
         assertEquals(codiceFiscaleTest,padeleur.getCodiceFiscale());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }

    @Test
   public void testgetDataDiNascita() {
      try{
          Padeleur padeleur=elencoPadeleur.get("paolobonolis@example.com");
          Date dataDiNascita=sdf.parse("14/06/1961");
          assertEquals(dataDiNascita, padeleur.getDataDiNascita());
        }catch(Exception e){
            fail("Errore: " + e.getMessage());
        }
    }
}

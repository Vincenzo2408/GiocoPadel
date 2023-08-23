package com.mycompany.giocopadel.app;

import static com.mycompany.giocopadel.app.TestGiocoPadel.giocoPadel;
import com.mycompany.giocopadel.app.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestRichiestaAttrezzatura {
   
   RichiestaAttrezzatura richiestaAttrezzatura=new RichiestaAttrezzatura(5,5);
   
   @BeforeAll
   public static void initTestGiocoPadel() {
       giocoPadel = GiocoPadel.getInstance();
   }
   
    @Test
     public void testgetNumeroRacchette() {
         try{
            int numeroRacchetteTest=5;
            assertEquals(numeroRacchetteTest,richiestaAttrezzatura.getNumeroRacchette());
         }catch (Exception e){
             fail("Errore: " +e.getMessage());
         }
    }

    @Test
    public void testgetNumeroPalline() {
        try{
            int numeroPallineTest=5;
            assertEquals(numeroPallineTest, richiestaAttrezzatura.getNumeroPalline());
        } catch (Exception e){
            fail("Errore: "+e.getMessage());
        }
    }
}

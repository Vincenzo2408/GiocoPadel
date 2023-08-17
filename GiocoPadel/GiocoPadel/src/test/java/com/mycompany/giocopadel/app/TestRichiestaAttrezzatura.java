/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app;

import static com.mycompany.giocopadel.app.TestGiocoPadel.giocoPadel;
import com.mycompany.giocopadel.app.domain.*;
import java.text.ParseException;
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

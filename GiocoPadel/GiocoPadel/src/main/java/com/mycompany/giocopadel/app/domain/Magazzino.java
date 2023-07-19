/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

/**
 *
 * @author gerar
 */

import java.util.Date;
import java.sql.Time;

public class Magazzino {
    private String idGiorno;
    public int racchetteTotali=20;
    public int pallineTotali=20;
    public int racchetteRichieste;
    public int pallineRichieste;
    public float costoSingoloAttrezzatura=2.0f;

    public Magazzino(String idGiorno, int racchetteRichieste, int pallineRichieste) {
        this.idGiorno=idGiorno;
        this.racchetteRichieste=racchetteRichieste;
        this.pallineRichieste=pallineRichieste;
        this.racchetteTotali=20;
        this.pallineTotali=20;
        this.costoSingoloAttrezzatura=2.0f;
    }  
 
    public int getpallineTotali(){
        return pallineTotali;
    }
    
    public int getracchetteTotali(){
        return racchetteTotali;
    }
    
    public int getracchetteRichieste(){
        return racchetteRichieste;
    }
    
    public int getpallineRichieste(){
        return pallineRichieste;
    }
    
    public String getidGiorno(){
        return idGiorno;
    }

    // Altri metodi per la gestione del magazzino (es. caricamento da file, salvataggio su file, ecc.)

    public float getCostoSingoloAttrezzatura() {
       return costoSingoloAttrezzatura; //To change body of generated methods, choose Tools | Templates.
    }

    void setnumeroRacchette(int numeroRacchette) {
        racchetteRichieste=racchetteRichieste+numeroRacchette; }

    void setnumeroPalline(int numeroPalline) {
        pallineRichieste=pallineRichieste+numeroPalline; 
    }
}

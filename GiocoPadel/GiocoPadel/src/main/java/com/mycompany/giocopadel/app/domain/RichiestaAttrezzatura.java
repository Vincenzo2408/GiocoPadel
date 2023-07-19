/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class RichiestaAttrezzatura {
    private int numeroRacchette;
    private int numeroPalline;
    private float costoTotaleAttrezzatura;
    private Magazzino magazzino;

    public RichiestaAttrezzatura(int numeroRacchette, int numeroPalline) {
        this.numeroRacchette = numeroRacchette;
        this.numeroPalline = numeroPalline;    
    }

    public int getNumeroRacchette() {
        return numeroRacchette;
    }

    public int getNumeroPalline() {
        return numeroPalline;
    }

    public float getCostoTotaleAttrezzatura() {
        return costoTotaleAttrezzatura;
    }
    
    public boolean verificaDisponibilitaAttrezzatura(int numeroRacchette, int numeroPalline, int racchetteTotali, int pallineTotali) { //Rif. UC2 1.1.1.1 VerificaDisponibilita(...) Rif. 3.SD InserimentoAttrezzatura
        if (racchetteTotali >= numeroRacchette && pallineTotali>= numeroPalline) {
            System.out.println("Attrezzature disponibili");
            return true; // Attrezzature disponibili
        } else {
            System.out.println("Attrezzature non disponibili");
            return false; // Attrezzature non disponibili
        }
    }
}


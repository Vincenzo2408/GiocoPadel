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
}


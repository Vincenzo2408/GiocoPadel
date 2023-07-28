/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.sql.Time;

// Interfaccia per definire le strategie di calcolo costo senza attrezzatura
interface StrategiaStandard{
    float calcolaImporto(CampoPadel campoPadel, Time oraInizio, Time oraFine);
}

// Interfaccia per definire le strategie di calcolo costo attrezzatura
interface StrategiaAttrezzatura {
    float calcolaCostoAttrezzatura(RichiestaAttrezzatura richiestaAttrezzatura, Magazzino magazzino);
}
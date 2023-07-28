/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.sql.Time;

/**
 *
 * @author gerar
 */

//Implementazione della strategia di calcolo pagamento standard (senza attrezzatura)
class PagamentoStandard implements StrategiaStandard {
    @Override
    public float calcolaImporto(CampoPadel campoPadel, Time oraInizio, Time oraFine) {
        double costoCampo=campoPadel.getCostoCampo(oraInizio, oraFine);
       
        return (float)costoCampo;
    }
}

// Implementazione della strategia di calcolo pagamento con attrezzatura
class PagamentoAttrezzatura implements StrategiaAttrezzatura {
    @Override
    public float calcolaCostoAttrezzatura(RichiestaAttrezzatura richiestaAttrezzatura, Magazzino magazzino) {
        return richiestaAttrezzatura.getNumeroRacchette() * magazzino.getCostoSingoloAttrezzatura()
            + richiestaAttrezzatura.getNumeroPalline() * magazzino.getCostoSingoloAttrezzatura();
    }
}



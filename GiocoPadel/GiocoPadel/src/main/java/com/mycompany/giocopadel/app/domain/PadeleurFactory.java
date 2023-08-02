/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.util.Date;

/**
 *
 * @author gerar
 */
public interface PadeleurFactory {
     Padeleur createPadeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email);
}

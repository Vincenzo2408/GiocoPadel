/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.giocopadel.app.domain;

import java.util.*;

public class Padeleur {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private Date dataDiNascita;
    private String email;


  public Padeleur(String nome, String cognome, String codiceFiscale, Date dataDiNascita, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
  }
  
  public String getEmail() {
    return email;
    }

  public String getNome() {
     return nome;
    }

  public String getCognome() {
     return cognome;
   }

  public String getCodiceFiscale() {
      return codiceFiscale;
    }

   public Date getDataDiNascita() {
      return dataDiNascita;
    }
}

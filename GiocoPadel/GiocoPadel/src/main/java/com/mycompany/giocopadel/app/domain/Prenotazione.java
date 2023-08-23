package com.mycompany.giocopadel.app.domain;

import java.sql.Time;
import java.util.Date;

public class Prenotazione {
    private int idPrenotazione;
    private boolean attrezzaturaRichiesta;
    private Date giornoPrenotazione;
    private Time oraInizio;
    private Time oraFine;
    private float costoPrenotazione;
    private Partecipante organizzatore;
    private Partecipante partecipante2;
    private Partecipante partecipante3;
    private Partecipante partecipante4;
    private CampoPadel campoPadel;
    private RichiestaAttrezzatura richiestaAttrezzatura;
    
    private StrategiaStandard strategiaStandard;
    private StrategiaAttrezzatura attrezzaturaStrategy;
      
    public Prenotazione(int idPrenotazione, boolean attrezzaturaRichiesta, Date giornoPrenotazione, Time oraInizio, Time oraFine, float costoPrenotazione) {
        this.idPrenotazione = idPrenotazione;
        this.attrezzaturaRichiesta = attrezzaturaRichiesta;
        this.giornoPrenotazione = giornoPrenotazione;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.costoPrenotazione = costoPrenotazione;
    }

    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(int idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public boolean isAttrezzaturaRichiesta() {
        return attrezzaturaRichiesta;
    }

    public void setAttrezzaturaRichiesta(boolean attrezzaturaRichiesta) {
        this.attrezzaturaRichiesta = attrezzaturaRichiesta;
    }

    public Date getGiornoPrenotazione() {
        return giornoPrenotazione;
    }

    public void setGiornoPrenotazione(Date giornoPrenotazione) {
        this.giornoPrenotazione = giornoPrenotazione;
    }

    public Time getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Time oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Time getOraFine() {
        return oraFine;
    }

    public void setOraFine(Time oraFine) {
        this.oraFine = oraFine;
    }

    public float getCostoPrenotazione() {
        return costoPrenotazione;
    }

    public void setCostoPrenotazione(float costoPrenotazione) {
        this.costoPrenotazione = costoPrenotazione;
    }

    public Partecipante getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Padeleur padeleur) {
        this.organizzatore = new Partecipante(padeleur);
    }

    public Partecipante getPartecipante2() {
        return partecipante2;
    }

    public void setPartecipante2(Padeleur padeleur) {
        this.partecipante2 = new Partecipante(padeleur);
    }

    public Partecipante getPartecipante3() {
        return partecipante3;
    }

    public void setPartecipante3(Padeleur padeleur) {
        this.partecipante3 = new Partecipante(padeleur);
    }

    public Partecipante getPartecipante4() {
        return partecipante4;
    }

    public void setPartecipante4(Padeleur padeleur) {
        this.partecipante4 = new Partecipante(padeleur);
    }

    public CampoPadel getCampoPadel() {
        return campoPadel;
    }

    public void setCampoPadel(CampoPadel campoPadel) {
        this.campoPadel = campoPadel;
    }
    
     public void setRichiestaAttrezzatura(RichiestaAttrezzatura richiestaAttrezzatura) {
        this.richiestaAttrezzatura = richiestaAttrezzatura;
    }
      
    public class Partecipante {
        private Padeleur padeleur;

        public Partecipante(Padeleur padeleur) {
            this.padeleur = padeleur;
        }

        public String getEmail() {
            return padeleur.getEmail();
        }
    }  
    
    // Metodi per impostare le strategie, Pattern GoF Strategy
    
    /*Questo metodo viene utilizzato per impostare la strategia di pagamento standard per la prenotazione.*/
    public void setPagamentoStrategy(StrategiaStandard strategiaStandard) {
        this.strategiaStandard = strategiaStandard;
    }

    /*Questo metodo viene utilizzato per impostare la strategia di calcolo del costo dell'attrezzatura per la prenotazione.*/
    public void setAttrezzaturaStrategy(StrategiaAttrezzatura attrezzaturaStrategy) {
        this.attrezzaturaStrategy = attrezzaturaStrategy;
    }

    // Metodo per calcolare l'importo totale della prenotazione
    
    /*Questo metodo calcola l'importo totale della prenotazione utilizzando la strategia di pagamento standard e, se applicabile, aggiunge il costo dell'attrezzatura calcolato utilizzando la strategia di attrezzatura.*/
    public float calcolaImportoTotale(Magazzino magazzino) {
         
         float costoAttrezzatura = 0.0f;
         if (attrezzaturaStrategy != null) {
             costoAttrezzatura = attrezzaturaStrategy.calcolaCostoAttrezzatura(richiestaAttrezzatura, magazzino);
         }

         return strategiaStandard.calcolaImporto(campoPadel, oraInizio, oraFine) + costoAttrezzatura;
     }
}


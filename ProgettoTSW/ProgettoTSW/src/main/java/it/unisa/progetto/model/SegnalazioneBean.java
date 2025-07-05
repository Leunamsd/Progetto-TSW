package it.unisa.progetto.model;

public class SegnalazioneBean {
    private int idSegnalazione;
    private int idUtenteSegnalante;
    private int idUtenteSegnalato;
    private java.sql.Timestamp dataSegnalazione;
    private String descrizione;
    private String stato;

    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(int idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public int getIdUtenteSegnalante() {
        return idUtenteSegnalante;
    }

    public void setIdUtenteSegnalante(int idUtenteSegnalante) {
        this.idUtenteSegnalante = idUtenteSegnalante;
    }

    public int getIdUtenteSegnalato() {
        return idUtenteSegnalato;
    }

    public void setIdUtenteSegnalato(int idUtenteSegnalato) {
        this.idUtenteSegnalato = idUtenteSegnalato;
    }

    public java.sql.Timestamp getDataSegnalazione() {
        return dataSegnalazione;
    }

    public void setDataSegnalazione(java.sql.Timestamp dataSegnalazione) {
        this.dataSegnalazione = dataSegnalazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}


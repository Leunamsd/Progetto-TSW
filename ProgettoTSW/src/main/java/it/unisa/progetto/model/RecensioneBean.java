package it.unisa.progetto.model;

public class RecensioneBean {
    private int idRecensione;
    private int idUtente;
    private int idTransazione;
    private java.sql.Timestamp dataCreazione;
    private int voto;
    private String commento;

    public int getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(int idRecensione) {
        this.idRecensione = idRecensione;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getIdTransazione() {
        return idTransazione;
    }

    public void setIdTransazione(int idTransazione) {
        this.idTransazione = idTransazione;
    }

    public java.sql.Timestamp getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(java.sql.Timestamp dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}

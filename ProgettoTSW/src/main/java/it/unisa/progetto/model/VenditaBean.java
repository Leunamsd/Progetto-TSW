package it.unisa.progetto.model;

public class VenditaBean {
    private int idVendita;
    private int idUtente;
    private int idCarta;
    private java.sql.Timestamp dataVendita;
    private String statoVendita;

    public int getIdVendita() {
        return idVendita;
    }

    public void setIdVendita(int idVendita) {
        this.idVendita = idVendita;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public java.sql.Timestamp getDataVendita() {
        return dataVendita;
    }

    public void setDataVendita(java.sql.Timestamp dataVendita) {
        this.dataVendita = dataVendita;
    }

    public String getStatoVendita() {
        return statoVendita;
    }

    public void setStatoVendita(String statoVendita) {
        this.statoVendita = statoVendita;
    }
}


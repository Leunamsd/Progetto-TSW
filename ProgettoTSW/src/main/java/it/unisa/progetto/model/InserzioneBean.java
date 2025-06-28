package it.unisa.progetto.model;

public class InserzioneBean {
    private int idInserzione;
    private int idUtenteInserzionista;
    private String nome;
    private String serie;
    private String rarita;
    private java.sql.Timestamp dataPubblicazione;
    private int quantita;
    private String condizione;
    private java.math.BigDecimal prezzo;

    public int getIdInserzione() {
        return idInserzione;
    }

    public void setIdInserzione(int idInserzione) {
        this.idInserzione = idInserzione;
    }

    public int getIdUtenteInserzionista() {
        return idUtenteInserzionista;
    }

    public void setIdUtenteInserzionista(int idUtenteInserzionista) {
        this.idUtenteInserzionista = idUtenteInserzionista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getRarita() {
        return rarita;
    }

    public void setRarita(String rarita) {
        this.rarita = rarita;
    }

    public java.sql.Timestamp getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(java.sql.Timestamp dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    public java.math.BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(java.math.BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
}


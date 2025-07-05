package it.unisa.progetto.model;

public class UtenteBean {
    private int idUtente;
    private String nome;
    private String indirizzo;
    private String telefono;
    private String username;
    private String email;
    private String password;
    private java.sql.Timestamp dataRegistrazione;
    private Integer mediaVoti;

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.sql.Timestamp getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(java.sql.Timestamp dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Integer getMediaVoti() {
        return mediaVoti;
    }

    public void setMediaVoti(Integer mediaVoti) {
        this.mediaVoti = mediaVoti;
    }
}

// Seguiranno le altre classi: Segnalazione, Carta, Inserzione, Wishlist, Carrello, Vendita, Recensione con getter e setter

// Vuoi che prosegua con tutte le altre classi in un unico file oppure separati?



package it.unisa.progetto.model;

public class WishlistBean {
    private int idWishlist;
    private int idUtente;
    private int idCarta;
    private java.sql.Timestamp dataAggiunta;

    public int getIdWishlist() {
        return idWishlist;
    }

    public void setIdWishlist(int idWishlist) {
        this.idWishlist = idWishlist;
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

    public java.sql.Timestamp getDataAggiunta() {
        return dataAggiunta;
    }

    public void setDataAggiunta(java.sql.Timestamp dataAggiunta) {
        this.dataAggiunta = dataAggiunta;
    }
}


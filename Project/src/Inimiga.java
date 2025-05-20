public class Inimiga {
    private String fala;
    private boolean viva;
    private String nome;
    private Item fraqueza;

    public Inimiga(String nome, String fala, Item fraqueza){
        this.nome = nome;
        this.fala = fala;
        this.fraqueza = fraqueza;
        viva = true;
    }

    public String getFala() {
        return fala;
    }

    public String getNome() {
        return nome;
    }

    public Item getFraqueza(){
        return fraqueza;
    }

    public boolean viva(){
        return viva;
    }

    public void morrer(){
        setViva(false);
    }

    public void droparItem(Ambiente ambiente){
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }
}

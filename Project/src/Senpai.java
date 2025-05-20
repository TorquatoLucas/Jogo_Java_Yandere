public class Senpai {
    private String fala;
    private static boolean apaixonado;
    private String nome;
    private Item fraqueza;

    public Senpai(Item fraqueza){
        nome = "Yuta";
        fala = "Olá Yandere, você está linda hoje!, por acaso você viu meu *livro* por aí?";
        this.fraqueza = fraqueza;
        apaixonado = false;
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

    public static boolean apaixonado(){
        return apaixonado;
    }

    public static void setApaixonado(boolean apaixonado) {
        Senpai.apaixonado = apaixonado;
    }
}

public class Saida {

    private Ambiente paraOndeLeva;
    private boolean trancada;
    private String nomeDaChave;

    public Saida(Ambiente paraOndeLeva, String nomeDaChave, boolean trancada){

        this.paraOndeLeva = paraOndeLeva;
        this.nomeDaChave = nomeDaChave;
        this.trancada = trancada;

    }

    public String getNomeDaChave() {
        return nomeDaChave;
    }

    public Ambiente getParaOndeLeva() {
        return paraOndeLeva;
    }

    public boolean trancada(){
        return trancada;
    }

    public void desbloquear(){
        trancada = false;
    }






}

public class AmbienteComInimigo extends Ambiente {

    private Inimiga inimiga;
    private String descricaoSecundaria;

    public AmbienteComInimigo(String nome, String descricao,String descricaoSecundaria,Inimiga inimiga){
        super(nome, descricao);
        this.inimiga = inimiga;
        this.descricaoSecundaria = descricaoSecundaria;
    }

    public AmbienteComInimigo(String nome, String descricao,String descricaoSecundaria,Item item,Inimiga inimiga){
        super(nome, descricao, item);
        this.inimiga = inimiga;
        this.descricaoSecundaria = descricaoSecundaria;
    }

    @Override
    public String getDescricaoLongaComObservar() {
        String desc = "Voce esta " + getDescricao() + "\n";
        if (hasItem()){
            desc += "Voce ve tambem " + super.getItem().getDescricao() + "\n";
        }
        if (inimiga.viva()){
            desc += inimiga.getNome() + " diz: " + inimiga.getFala() + "\n";

            if (inimiga.getNome() == "Naomi"){
                desc += "Você sente *raiva* \n"; 
            }
            
        }
        
        desc += "Saidas: " + direcoesDeSaida();
        return desc;
    }

    @Override
    public boolean usarArma(Item arma){
        
            if(arma == inimiga.getFraqueza()){
                inimigaMorreu();
                return true;
            }
        
        return false;
    }


    public void inimigaMorreu(){
        inimiga.morrer();
        inimiga.droparItem(this);
        super.setDescricao(descricaoSecundaria);
        
    }
}

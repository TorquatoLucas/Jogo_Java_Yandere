public class AmbienteComSenpai extends Ambiente{
    
    private Senpai senpai;
    private String descricaoSecundaria;

    public AmbienteComSenpai(String nome, String descricao,String descricaoSecundaria,Senpai senpai){
        super(nome, descricao);
        this.senpai = senpai;
        this.descricaoSecundaria = descricaoSecundaria;
    }

    @Override
    public String getDescricaoLongaComObservar() {
        String desc = "Voce esta " + getDescricao() + "\n";
        if (!Senpai.apaixonado()){
            desc += senpai.getNome() + " diz: " + senpai.getFala() + "\n"; 
        }else{
            desc += "Seu senpai está te seguindo! " + "\n";
        }
        
        desc += "Saidas: " + direcoesDeSaida();
        return desc;
    }

    public boolean usarItem(Item livro){
        
            if(livro == senpai.getFraqueza()){
                Senpai.setApaixonado(true);
                super.setDescricao(descricaoSecundaria);
                return true;
            }
        
        return false;
    }

}

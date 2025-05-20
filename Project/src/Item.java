public class Item {

    private String nome;
    private String descricao;
    private boolean visivel;



    public Item(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
        visivel = true;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public boolean getVisivel(){
        return visivel;
    }



}

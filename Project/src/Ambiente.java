import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Ambiente  {
    // nome do ambiente
    private String nome;
    // descrição do ambiente
    private String descricao;
    // ambientes vizinhos de acordo com a direção
    private HashMap<String, Saida> saidas;

    private Item item;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas. 
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * 
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String nome, String descricao)  {
        this.descricao = descricao;
        saidas = new HashMap<>();
        this.nome = nome;
    }


    public Ambiente(String nome, String descricao, Item item)  {
        this.descricao = descricao;
        saidas = new HashMap<>();
        this.item = item;
    }

    public String getNome() {
        return nome;
    }


    /**
     * Define uma saída do ambiente.
     * 
     * @param direcao A direção daquela saída.
     * @param saida O ambiente para o qual a direção leva.
     */
    public void ajustarSaida(String direcao, Ambiente saida) {
        
        Saida saidaNova = new Saida(saida,null,false);
        saidas.put(direcao, saidaNova);    

    }

    public void ajustarSaidaBloqueada(String direcao, Ambiente saida, String nomeDaChave) {
        
        Saida saidaNova = new Saida(saida,nomeDaChave,true);
        saidas.put(direcao, saidaNova);    
        
    }

    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna uma saída do ambiente, dada uma direção (null se não existir)
     * 
     * @param direcao Direção à qual a saída se refere
     * @return Ambiente de saída naquela direção
     */
    public Ambiente getSaida(String direcao) {
        if(saidas.get(direcao) != null){
            if(saidas.get(direcao).trancada()){
                return null;
            }
            return saidas.get(direcao).getParaOndeLeva();
        }

        return null;
    }

    /**
     * Texto montado com todas as saídas disponíveis
     * 
     * @return Texto com as saídas
     */
    public String direcoesDeSaida() {
        String texto = "";
        for (String direcao : saidas.keySet()) {
            texto += direcao + " ";
        }
        return texto;
    }

    /**
     * Retorna uma descrição longa do ambiente. A ideia é que, quando o ambiente
     * evoluir e tiver mais coisas (como itens ou monstros) não seja necessário
     * alterar a classe Jogo para informar o que existe no ambiente.
     * 
     * @return Uma descrição longa do ambiente, incluindo saídas.
     */
    public String getDescricaoLonga() {
        String desc = "Voce esta " + getDescricao() + "\n";
        desc += "Saidas: " + direcoesDeSaida();
        return desc;
    }

    public String getDescricaoLongaComObservar() {
        String desc = "Voce esta " + getDescricao() + "\n";
        if (Senpai.apaixonado()){
            desc += "Seu senpai está te seguindo! " + "\n";
        }
        if (hasItem()){
            desc += "Voce ve tambem " + item.getDescricao() + "\n";
        }
        desc += "Saidas: " + direcoesDeSaida();
        return desc;
    }
    
    public boolean hasItem(){
        return item!=null;
    }

    public Item getItem(){
        return item;
    }

    public Item takeItem(){
        Item item = this.item;
        this.item = null;
        return item;
    }
    

    public boolean usarChave(Item item){
        for (String saida : saidas.keySet()) {
            if(item.getNome().equalsIgnoreCase(saidas.get(saida).getNomeDaChave())){
                saidas.get(saida).desbloquear();
                return true;
            }
        }
        return false;
    }

    public boolean usarArma(Item item){
        return false;
    }

    public boolean usarItem(Item item){
        return false;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void addItem(Item item){
        this.item = item;
    }

    
}

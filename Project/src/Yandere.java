import java.util.HashMap;

public class Yandere {

    private Ambiente ambienteAtual;
    private HashMap<String,Item> inventario;

    public Yandere(){
        inventario = new HashMap<>();
    }

    public Ambiente getAmbienteAtual() {
        return ambienteAtual;
    }

    public void setAmbienteAtual(Ambiente ambienteAtual){
        this.ambienteAtual = ambienteAtual;
    }

    public void putItem(Item item){
        inventario.put(item.getNome(), item);
    }

    public void removerItem(String item){
        inventario.remove(item);
    }

    public Item getItem(String nomeItem){
        for (String item : inventario.keySet()) {
            if(item.equalsIgnoreCase(nomeItem)){
                return inventario.get(item);
            }
        }
        return null;
    }
    
    public String listaDeItens(){
        String itens = " ";
        for (String item : inventario.keySet()) {
            if(item!="raiva"){
                itens += item + " ";
            }
        }

        return itens;
    }

    public boolean containsItem(String itemProcurado){
        for (String item : inventario.keySet()) {
            if(item.equalsIgnoreCase(itemProcurado)){
                return true;
            }
        }

        return false;
    }


}
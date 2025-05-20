public class InimigaComDrop extends Inimiga{
    Item drop;

    public InimigaComDrop(String nome, String fala, Item fraqueza, Item drop){
        super(nome,fala,fraqueza);
        this.drop = drop;
    }

    public Item getDrop() {
        return drop;
    }

    @Override
    public void droparItem(Ambiente ambiente){
        ambiente.addItem(drop);
    }



}

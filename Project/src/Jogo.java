/**
 * Essa é a classe principal da aplicacao "Yandere".
 * "Yandere" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os ambientes, 
 * cria o analisador e começa o jogo. Ela também avalia e  executa os comandos que 
 * o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves e adaptado novamente por Lucas Torquato)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;

    private boolean zerou;

    // Contagem de garotas mortas
    private int count;

    private Yandere yandere;
        
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo()  {
        yandere = new Yandere();
        criarAmbientes();
        analisador = new Analisador();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        AmbienteComSenpai entradaDaEscola;
        Ambiente salaDosProfessores, cantina, porao;
        AmbienteComInimigo salaDeAula,banheiro,terraco;

        Item faca = new Item("Faca", "uma *faca* de cozinha comum dentro de uma bolsa");
        Item livro = new Item("Livro", "o *livro* do seu senpai com ela");
        Item chave = new Item("Chave", "uma *chave* ensanguentada");

        Item raiva = new Item("raiva", "uma raiva descontrolada vinda de você");
        yandere.putItem(raiva);

        InimigaComDrop aiko = new InimigaComDrop("Aiko", "Você nunca vai ficar com o senpai HAHAHAHHA",faca, chave);
        InimigaComDrop emi = new InimigaComDrop("Emi", "Sai pra lá, menina estranha",faca, livro);
        Inimiga naomi = new Inimiga("Naomi", "Nem acredito que o senpai me comprimentou hoje",raiva);

        Senpai senpai = new Senpai(livro);
      
        // cria os ambientes
        salaDeAula = new AmbienteComInimigo("salaDeAula","em uma sala entediante, várias carteiras e um quadro, tem apenas uma menina na sala", "em uma sala não tão entediante", emi);
        salaDosProfessores = new Ambiente("salaDosProfessores","em uma sala sem ninguém, somente uma bolsa deixada para trás", faca);
        cantina = new Ambiente("cantina","na cantina da escola, tem alguns guardas te observando");
        banheiro = new AmbienteComInimigo("banheiro","no banheiro com apenas uma garota segurando uma chave","no banheiro", aiko);
        entradaDaEscola = new AmbienteComSenpai("entradaDaEscola","na entrada da escola com corredores e uma escada para seguir, você vê seu senpai","na entrada da escola com corredores e uma escada para seguir",senpai);
        terraco = new AmbienteComInimigo("terraco","no topo do prédio da escola, tem apenas uma garota observando a paisagem.","no topo do prédio da escola", naomi);
        porao = new Ambiente("porao","um porão abandonado com uma cadeira no centro");
        
        // inicializa as saidas dos ambientes
        entradaDaEscola.ajustarSaida("cima", terraco);
        entradaDaEscola.ajustarSaida("leste", salaDosProfessores);
        entradaDaEscola.ajustarSaida("oeste", salaDeAula);
        
        
        terraco.ajustarSaida("baixo", entradaDaEscola);
        
        salaDosProfessores.ajustarSaida("leste", cantina);   
        salaDosProfessores.ajustarSaida("oeste", entradaDaEscola);   
        
        salaDeAula.ajustarSaida("leste", entradaDaEscola);
        salaDeAula.ajustarSaida("oeste", banheiro);
        
        cantina.ajustarSaida("oeste", salaDosProfessores);

        banheiro.ajustarSaida("leste", salaDeAula);
        banheiro.ajustarSaidaBloqueada("baixo",porao, "Chave");

        porao.ajustarSaida("cima", banheiro);

        yandere.setAmbienteAtual(entradaDaEscola);  // o jogo comeca em frente à escola
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
        zerou = false;
        boolean terminado = false;
        while (! terminado) {
            
            if(zerou){
                terminado = true;
                System.out.println("pressione ENTER");
                analisador.pegarComando();
            }else{
                Comando comando = analisador.pegarComando();
                terminado = processarComando(comando);
            }

        }
        System.out.println("Obrigado por jogar. Até mais!");
    }


    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo a Yandere!");
        System.out.println("Yandere e um jogo onde a protagonista Yandere tenta matar todas que olharem para seu senpai.");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();
        
        imprimirLocalizacaoAtual();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando)  {
        boolean querSair = false;

        if(comando.ehDesconhecido()) {
            System.out.println("Eu nao entendi o que voce disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("observar")) {
            observar(comando);
        }
        else if (palavraDeComando.equals("pegar")) {
            pegar(comando);
        }
        else if (palavraDeComando.equals("inventario")) {
            imprimirInventario();
        }
        else if (palavraDeComando.equals("usar")) {
            usarItem(comando);
        }
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }

        return querSair;
    }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    private void imprimirAjuda()  {
        System.out.println("Voce esta em uma escola. Voce caminha pelos corredores.");
        System.out.println();
        System.out.println("Digite 'ir' + a direção que deseja se mover");
        System.out.println("Palavras entre *asterisco* podem ser utilizadas junto de palavras de comando");
        System.out.println();
        System.out.println("Suas palavras de comando sao:");
        System.out.println("   " + analisador.getComandosValidos());
    }

    /**
     * Trata o comando "observar", exibindo as informações da localização atual do jogador
     * 
     * @param comando Objeto comando a ser tratado
     */
    private void observar(Comando comando) {
        // se há segunda palavra, explica para o usuário que não pode...
        if(comando.temSegundaPalavra()) {            
            System.out.println("Não é possível observar detalhes de alguma coisa");
            return;
        }

        imprimirLocalizacaoAtualDetalhada();
    }

    private void pegar(Comando comando){

    
            if(comando.temSegundaPalavra()){
                if(yandere.getAmbienteAtual().getItem() != null){
                    String itemNome = yandere.getAmbienteAtual().getItem().getNome();
                    if(comando.getSegundaPalavra().equalsIgnoreCase(itemNome)){
                        System.out.println("Voce pegou " + itemNome);
                        yandere.putItem(yandere.getAmbienteAtual().takeItem());
                        return;
                    }
                }
                System.out.println("Yandere não encontrou " + comando.getSegundaPalavra());
                return;
            }
            System.out.println("Pegar o que?");
            return;
        
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando)  {
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = yandere.getAmbienteAtual().getSaida(direcao);

        if (proximoAmbiente == null) {
            System.out.println("Nao ha passagem!");
        }
        else {

            if(proximoAmbiente.getNome() != null){
                if(Senpai.apaixonado() & proximoAmbiente.getNome().equals("porao") & count == 3){
                    zerarJogo();
                    return;
                }
            }

            yandere.setAmbienteAtual(proximoAmbiente);
            
            imprimirLocalizacaoAtual();
        }
    }

    /**
     * Exibe as informações da localização atual para o jogador
     */
    private void imprimirLocalizacaoAtual() {
    	System.out.println(yandere.getAmbienteAtual().getDescricaoLonga());
        System.out.println();
    }

    private void imprimirLocalizacaoAtualDetalhada() {
    	System.out.println(yandere.getAmbienteAtual().getDescricaoLongaComObservar());
        System.out.println();
    }

    private void imprimirInventario(){
        System.out.println("Seus itens são: [" + yandere.listaDeItens() + "]");
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando)  {
        if(comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }



    private void usarItem(Comando comando){
        if(comando.temSegundaPalavra()){
            String item = comando.getSegundaPalavra();

            if(yandere.containsItem(comando.getSegundaPalavra())){

                switch (item) {
                    case "chave":
                        usarChave(comando);
                        return;
                    case "livro":
                        usarLivro(comando);
                        return;
                    case "faca":
                        usarArma(comando,"faca");
                        return;
                    case "raiva":
                        usarArma(comando,"raiva");
                        return;                     
                    default:
                        System.out.println("Esqueceu de adicionar o item no swich!");
                        return;
                }

            }

            System.out.println("Yandere não possui este item");
            return;
            
        }

        System.out.println("Usar o que?");

    }


    private void usarArma(Comando comando, String nomeArma){
        if(yandere.getAmbienteAtual().usarArma(yandere.getItem(comando.getSegundaPalavra()))){
                switch (nomeArma) {
                    case "faca":
                        System.out.println("Você faz algo terrível... mas se sente bem");
                        count++;
                        break;
                    case "raiva":
                        System.out.println("Você empurra a menina da beira do prédio... mas se sente bem");
                        count++;
                        break;
                    default:
                        break;
                }
                    return;
                };
                System.out.println("Nada aconteceu.");
                return;

    }

    private void usarLivro(Comando comando){
        if(yandere.getAmbienteAtual().usarItem(yandere.getItem(comando.getSegundaPalavra()))){
            System.out.println("Você fez algo apaixonante... seu senpai está te seguindo!");
            return;
        }
        System.out.println("Nada aconteceu.");
        return;
    }


    private void usarChave(Comando comando){
        if(yandere.getAmbienteAtual().usarChave(yandere.getItem(comando.getSegundaPalavra()))){
                    System.out.println("Passagem aberta...");
                    return;
                };
                System.out.println("Nada aconteceu.");
                return;
    }

    private void zerarJogo(){
        System.out.println("                                      mmMMMM::::mm::++++++::++::::::::++::::++++::::::::::::::::::::++::::++++::::::::::::::++::            \r\n" + //
                        "                                      ..mm@@MM++++MMMMMMmmMMmmmmmmmmmmMMMMMMMMMM++++++++++++++mmmmmmMMmmmm++++mmmm++++++++++mmMM++          \r\n" + //
                        "                                      ########MMMMMMMM##@@MMmm##@@mm@@mm@@MMMMMMMM@@MMmmmmmm@@mmmmmmmmMM##MM##@@mmMMmmmmmmmmmm@@##::        \r\n" + //
                        "                                    ##MMMM##@@@@@@@@MM######@@MM@@@@MM@@MMMM@@@@@@@@MMMMMMMM@@@@@@@@MMMMMM@@@@@@MM@@MM@@@@@@MMMM::--        \r\n" + //
                        "                                  mm@@  --##@@MM####@@##::  ##@@  ##@@##....mm####@@##@@@@@@####  MM    ################@@@@MM@@##          \r\n" + //
                        "                                ::##..MM@@################..::@@  --mm@@    ++################      --    ############@@##@@@@##            \r\n" + //
                        "                                  MM--##############::--##    ##    @@##      MMmm@@########MM        --  mm####################            \r\n" + //
                        "                ....            MM  ################::..@@##        --        ....@@########                ##################++            \r\n" + //
                        "                    ----  ..    mm::######MMmm######--    ##..          ..            ####@@  ++..    --    ##################--            \r\n" + //
                        "          ::++..--::MM@@mmmm      --####++::MM  --##--    ..MM                          @@::    ::..        ..################::            \r\n" + //
                        "    mmmm----::MM@@..####@@@@####++@@##++::++--    ##                                                        mm::##############++            \r\n" + //
                        "++--..--####@@##mm::@@########MM####..##..------  ##        ..                                      ++....        MM..--########            \r\n" + //
                        "......++  ##@@##++++######mm++MM..MM@@--MM--..@@  @@    mm                                            --  ::              MM####            \r\n" + //
                        "--..::##mm######--mmMM@@..::::--......++..::--::  --  ##                                              ..                                    \r\n" + //
                        "..--::mm######@@--mmMM::::::##mm++  ....  ::++      MM    ::                                        --                          ##          \r\n" + //
                        "..mm--..##@@##++::::::..------@@--++::++mm..--::  --MM      ..                                                                    ..        \r\n" + //
                        "..@@##..##++##mm::::--..--..::mmMM..mm::::mm++--++##      ++                                            ++                          ..      \r\n" + //
                        "..MM##..mm++##MM::--::--::..------::  ::MM::++@@MMmm--..@@                                            @@--                            --    \r\n" + //
                        "::mm++--::--##MM::--::--::..::--  ::    ..MM--++--mm    @@MM                                        ..----                                ++\r\n" + //
                        "MM--::..mm--++mm::--++..::  --::  mm....  ::++--MM                                                    ..--                                  \r\n" + //
                        "MM::----++----++----mm++++--::--....        mm  ##....                                                ..::                                  \r\n" + //
                        "++------..----::....mm@@++--::::            ..    ::--                                                                                      \r\n" + //
                        "------::  ..--..  ..MM##mm++mm++                  ::--                                                    ..                                \r\n" + //
                        "----  --    ..  ..--####MMmmMM            ..--      ..                                                                                    ++\r\n" + //
                        "--..  ++      ::::::MM##@@##@@            ++####    ....                                                                                mm  \r\n" + //
                        "--    mm--..----mm::++####--mm        mm::                ....                                                            ..          ++    \r\n" + //
                        "----  mm++::--::++::mm##++  ::                                    ++                                                                @@      \r\n" + //
                        "::::::mm++++--++--::++##++  @@--                                  ::                                        ..                    ++        \r\n" + //
                        "::::::MM::++::::::::::##mm--MMMM                                  --                                        --                  ..          \r\n" + //
                        "++++::MM++++::::::::::mm@@MM::                                                                              ::                    --..      \r\n" + //
                        "MMMM::MMmm++--::++::++mmmm..                                        --                                    ..--                --  ++        \r\n" + //
                        "MMMM++MM##++::::MM--mmMM..  ..                                                                                              --    ..        \r\n" + //
                        "MM@@++@@##++++::mm::++mm..  mm                                                                                                          --::\r\n" + //
                        "####mm..  ++@@::::::++::::  ++--                                                                                          ..          --....\r\n" + //
                        "mm##MM  @@++mm::++mm::++MM    mm                                --                                                                  ..  mm  \r\n" + //
                        "mm##      ++MM++++@@++mm@@    mm                                ..                                              --      ::          ----    \r\n" + //
                        "####      MMMM++++MM++++++    MM                                                        --                                                  \r\n" + //
                        "MM##..  --mmmmMM++++++mmmmmm  @@                                  ----                --        ..                    ..                --  \r\n" + //
                        "mm@@##  ::--@@@@mm++@@mmmm@@  MM..                                                  --        ++--                            ..            \r\n" + //
                        "MM@@##    ::mm@@mm++##mmMMmm  MM++                                    mm          ..      ----..                    ..                      \r\n" + //
                        "@@@@####    MM@@MMmmmmMMmmMM..MMMM                --                                    ::    --..                      ..                  \r\n" + //
                        "MMMM########@@@@@@mm++##MM@@@@MM                ..                      ++          ..                            ..  ..                    \r\n" + //
                        "MM@@@@##@@########@@MM@@MMMMmmMM                    ..                  ..        --              ::              ..                        \r\n" + //
                        "@@@@@@##########@@##MMMMMMMM@@mm  ::              ++                      MM--::..                  --          ..                          \r\n" + //
                        "####@@############@@##MM##MM@@@@  @@                          --      --mm                              ..++  ..        --    ..            \r\n" + //
                        "##################@@##@@##@@@@##..MM                            ..@@--....                                    ..            ..    --        \r\n" + //
                        "##############MM##@@##@@@@##@@@@@@::                            ..----..  ..                                ++                              \r\n" + //
                        "##############++####@@######@@##@@--                    MM        ..        --                            ::  ..        ..      ..          \r\n" + //
                        "##############++########@@########@@                  ::::          --                                  ++                                  \r\n" + //
                        "##############mmmm############@@####                ++----++          ..      mm                      mm                                    \r\n" + //
                        "##############MM..##..##############mm              --....                ..                          ++                                    \r\n" + //
                        "##############MM  ##@@######@@########              ....  --::                                        --                                    \r\n" + //
                        "##############MM  ++##::################                ::                      ++                    --                                    \r\n" + //
                        "##############mm  ..##  ############mm##MM        --        --::                                    ..                                      \r\n" + //
                        "##############mm    ##@@  ############::##--      MM        --                    ..                    ....----                            \r\n" + //
                        "##############mm--  ::##++MM##########MM..##..    ++      mm--                                    mm--                                      \r\n" + //
                        "##############MM      ##++..############++  ##              ..                                                    --                        \r\n" + //
                        "##############--      mmmm..  ##########++::  ##--      --..                        ++          ..                  --                      \r\n" + //
                        "##############@@  ::  ..@@  ::++##########....--@@@@                                ++                                                      ");

                        System.out.println();
                        System.out.println("\t\t\t\t\t\t\t\t\t\t Jogo desenvolvido por Lucas Torquato.");
                        zerou = true;
    }
}

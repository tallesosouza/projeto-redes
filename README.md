# Meu Jogo Espacial

Bem-vindo ao **Meu Jogo Espacial**! Embarque nesta jornada emocionante pelo espaço, onde sua missão é destruir inimigos e conquistar a pontuação máxima.

## 🚀 Como Jogar

1. **Objetivo:** Aitre contra os inimigos e não seja atingido, quanto mais inigimo abater mais ponto você acumulará.

2. **Controles:**
   - Enter Inicia o jogo.
   - Enter (Na tela de Game Over): Reinicia o jogo.
   - P: Pausa o jogo.
   - &larr; &uarr; &rarr; &darr; Setas direcionais: movimenta a nave.
   - Barra de espaço: Dispara mísseis.

## 📂 Estrutura do Projeto

O projeto é organizado em duas partes client e server. A arvore da estrutura do projeto foi parecida em ambos, para que o cliente possa receber o objeto AllElementsModel visto que para comunicação socket entre cliente e servidor a liguagem java serializa o objeto para envio e deserealiza no lado do servidor e para que isso seja possivel tanto o pacote quanto o nome do objeto deve ser identicos tanto do lado do servidor quanto do lado do cliente:

1. **Client:**
- Lógica principal do jogo usando libGDX.
```bash
│   │   └── src
│   │       └── com
│   │           └── terminalroot
│   │               └── game
│   │                   ├── engine
│   │                   │   ├── AllElements.java
│   │                   │   ├── ClientPlayer.java
│   │                   │   ├── ClientPlayerObservable.java
│   │                   │   ├── Element.java
│   │                   │   └── models
│   │                   │       ├── ActionPlayerEnum.java
│   │                   │       ├── AllElementsModel.java
│   │                   │       ├── DirectionEnum.java
│   │                   │       ├── ElementModel.java
│   │                   │       ├── ScreenGame.java
│   │                   │       └── StatusGameEnum.java
│   │                   ├── MyGdxGame.java
│   │                   └── socket
│   │                       └── ClientSocket.java
```
* 
   <details>
   <summary> Detalhamento dos arquivos</summary>
   <h5>📂 engine ( Pasta que contem toda lógicas, modelos, enums do jogo )</h5>
   <ul>
         <li>AllElements - Todos elementos do jogo está nessa classe junto</li> 
         <li>ClientPlayer - Contem a lógica de captura de tecla do usuário para envio ao servidor e possui algumas verificações para evitar envio desnecessário de comandos que não são possiveis no momento</li> 
         <li>ClientPlayerObservable - Tread que possui o loop de recebimento do objeto ( AllElementsModel ) de retorno do servidor</li> 
         <li>Element - Classe com as propriedades dos objetos do jogo, posição e tamanho</li> 
         <li>Models - ( Pasta que contem todas os modelos e enums utilizado no jogo )</li> 
   </ul>
   <h5>📂 MyGdxGame ( Classe utilizada pela lib GDX para poder fazer a renderização e inicialização dos componentes visuais do jogo )</h5>
   <h5>📂 Socket </h5>
   <ul>
         <li>ClientScoket - Lógica de conexão socket do lado do cliente </li> 
   </ul>
   </details>


2. **Server:**
- Lógica do servidor para processar comandos e manter o estado do jogo.
```bash
└── server
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── terminalroot
        │   │           ├── game
        │   │           │   ├── engine
        │   │           │   │   ├── AllElements.java
        │   │           │   │   ├── Element.java
        │   │           │   │   ├── models
        │   │           │   │   │   ├── ActionPlayerEnum.java
        │   │           │   │   │   ├── AllElementsModel.java
        │   │           │   │   │   ├── DirectionEnum.java
        │   │           │   │   │   ├── ElementModel.java
        │   │           │   │   │   ├── ScreenGame.java
        │   │           │   │   │   └── StatusGameEnum.java
        │   │           │   │   └── Player.java
        │   │           │   └── socket
        │   │           │       └── ServerSocket.java
        │   │           └── ServerTCP.java
        │   └── resources
        └── test
            └── java
```
* 
   <details>
   <summary> Detalhamento dos arquivos</summary>
   <h5>📂 engine ( Pasta que contem toda lógicas, modelos, enums do jogo )</h5>
   <ul>
         <li>AllElements - Todos elementos do jogo está nessa classe junto</li> 
         <li>Element - Classe com as propriedades dos objetos do jogo, posição e tamanho</li> 
         <li>Player -  Contem toda logica do jogo com ação do usuário tais como inicialização do jogo, pause, verificação de pontuação, movimentação da nave, lógica de criação do novas posições dos inimigos, movimentação do míssel, checagem de game over, checagem de colisão tanto do inimigo quanto dos misseis.</li> 
   </ul>
   <h5>📂 MyGdxGame ( Classe utilizada pela lib GDX para poder fazer a renderização e inicialização dos componentes visuais do jogo )</h5>
   <h5>📂 Socket </h5>
   <ul>
         <li>ServerSocket - Lógica de conexão socket do lado do servidor </li> 
   </ul>
   <h5>📂 Arquivo de inicialização e abertura de conexão do servidor </h5>
   </details>

## 🚀 Protocolo de Comunicação Cliente-Servidor

O protocolo de comunicação entre o cliente e o servidor é essencial para a sincronização do estado do jogo. Aqui está uma visão mais detalhada do fluxo de comunicação:

* Fluxo completo

![socketexample](https://github.com/tallesosouza/projeto-redes/assets/82471731/a274bff8-54af-4a3e-96df-1744e386ed50)

* Alternativa game over

![socketexamplegameover](https://github.com/tallesosouza/projeto-redes/assets/82471731/13a62ccb-9570-4dc2-854e-4afad945130e)

* Alternativa pause

![pausaopcao](https://github.com/tallesosouza/projeto-redes/assets/82471731/34a952aa-ed73-40a2-a9f1-8abf33d3a61d)

* O cliente só pode enviar para o servidor ações do tipo String como: ENTER, UP, DOWN, LEFT, RIGHT, PAUSE e EXIT;

1. 👨💻 **Estabelecendo Conexão:**
   - O servidor inicia o socket e aguarda conexões do cliente.
   - O cliente solicita a conexão com o servidor.
   - O servidor aceita a conexão do cliente e informa ao cliente que a conexão foi aceita.

2. 👨 **Envio de Comando do Cliente:**
      - ENTER - Iniciar, reiniciar ou retirar pause: somente aceito se o usuário estiver com status WAITING, GAMEOVER ou PAUSE;
      - UP, DOWN, LEFT, RIGHT - Direção para que a nave se mova, caso seja enviado um comando e a nave se encontre já no limite da tela o servidor retorna o objeto AllElements só que com o mesmo valor do frame anterior: somente aceito com status de IN_PROGRESS;
      - PAUSE - Solicita que o servidor entre em modo de pause: somente aceito com status de IN_PROGRESS;
      - SPACE - Informa ao servidor que deve disparar o missel: somente aceito com status de IN_PROGRESS;

3. 💻 **Processamento no Servidor:**
   - Ao receber os comandos o servidor irá processa-lo e responder ao cliente:
      - ENTER - Somente aceito se o usuário estiver com status WAITING, GAMEOVER ou PAUSE;
      - UP, DOWN, LEFT, RIGHT - Somente aceito com status de IN_PROGRESS;
      - PAUSE - Somente aceito com status de IN_PROGRESS;
      - SPACE - Somente aceito com status de IN_PROGRESS;
      - EXIT - Aceito em qualquer momento;
      
   - Caso seja enviado pelo cliente alguma ação que não seja as informadas acima o servidor irá responder o cliente com o mesmo objeto anterior sem nenhuma atualização;
   - Caso o comando que foi enviado não seja adequado com o status necessário para execução da ação o servidor irá responder o cliente com o mesmo objeto anterior sem nenhuma atualização;

4. **Envio de Estado Atualizado para o Cliente:**
   - O servidor devolve ao cliente o AllElements com as coordenadas atualizadas e os status do jogo:
      - WAITING - Aguardando usuário iniciar, servidor aguarda o cliente enviar ENTER;
      - IN_PROGRESS - Jogo está em andamento, servidor sempre envia e aceita atualização do cliente;
      - PAUSE - Jogo se encontra em pause, não é enviado nenhuma atualização do lado do servidor;
      - GAMEOVER - Servidor informa ao cliente que deu gameover e para de enviar atualizações;
      - ERROR - Servidor informa ao cliente que possui algum erro ou de conexão ou algum bug;

5. **Renderização no Cliente:**
   - O cliente usa as informações recebidas para renderizar os elementos do jogo no lado do cliente.

6. **Repetição do Processo:**
   - O processo de envio de comandos, processamento no servidor e atualização do estado continua enquanto o jogo está em execução.

7. **Fechamento da Conexão:**
   - Quando o cliente decide fechar o jogo, envia para o servidor o comando EXIT e fecha a conexão com o mesmo.

Este protocolo simples permite uma comunicação eficiente entre o cliente e o servidor, garantindo uma experiência de jogo suave.

## ▶️ Executando o Jogo

### Pré-requisitos
#### &nbsp; Client
- Java 17;
- Gradle;
- [libGDX](https://libgdx.com/) ( Biblioteca utilizada para renderização dos elementos );
   - [Como instalar o libGDX](https://libgdx.com/wiki/start/project-generation)
   - Configurações de instalação:
      - Coloque no package name: com.terminalroot.game
      - Desmarque as opções (Opcional): Android, IOS, HTML


#### &nbsp; Server
- Java 17;
- Mavem;

### Configuração e Execução

1. **Servidor:**
   - Navegue até o diretório `server` pelo terminal.
   - Execute `./gradlew run` para iniciar o servidor.

2. **Cliente:**
   - Navegue até o diretório `client` pelo terminal.
   - Execute `./gradlew run` para iniciar o cliente.

3. **Configurações adicionais:**
   - As configurações de rede do lado do cliente está no arquivo ```MyGdxGame.java```;
      ```java
         this.clientPlayer = new ClientPlayer("127.0.0.1", 4000);
      ```
      - ***Configurações para mesmo computador:***
      Configuração padrão do projeto, está no host localhost e porta 4000;
      - ***Configurações para mesma rede ( computadores diferentes ):***
      Verifique qual a porta que está sendo utilizada pelo computador que está rodando o servidor, e no arquivo do cliente substitua o 127.0.0.1 pelo endereço de IP interno da máquina;
      - ***Configurações para computadores em redes separadas ( Não recomendado para este teste ):***
      Precisará fazer algumas configurações de redirecionamento no seu roteador para que a porta e o ip de sua máquina que está o servidor seja vísivel para na rede externa, talvez seja necessário alterar a porta tanto do lado do servidor quanto do lado do cliente, então necessitará que substitua o 4000 pela porta disponível;

## Telas

* Tela inicial
![tela_inicial](https://github.com/tallesosouza/projeto-redes/assets/82471731/8ed92aee-95d2-4b09-b152-dcbb65da774b)

* Tela game over
![game_over](https://github.com/tallesosouza/projeto-redes/assets/82471731/2dcf726a-7a0e-4203-a72c-bad7f9c8f369)

* Tela error conexão
![error_conexao](https://github.com/tallesosouza/projeto-redes/assets/82471731/e3ef1849-7d5a-470e-9014-7f0027c5295d)

* Tela jogo
![tela_jogo](https://github.com/tallesosouza/projeto-redes/assets/82471731/7ac31785-f0c6-45e5-8656-1ac995233521)

* Tela jogo (video)
![gravacao](https://github.com/tallesosouza/projeto-redes/assets/82471731/ca14748e-e190-4862-8436-c05feb5b3085)
sets/82471731/e3ef1849-7d5a-470e-9014-7f0027c5295d)


## 🚧 Desenvolvimento

- Este projeto foi desenvolvido usando Java 17 e libGDX.
- Será implementadas novas funcionalidades como multiplayers ( 2 clientes jogando entre si );

## 🤝 Contribuindo

Contribuições são bem-vindas! Abra uma issue ou envie um pull request para colaborar.

## 🎮 Capturas de Tela

[Adicione capturas de tela do jogo aqui]

## 📚 Recursos Adicionais

- [Link para a documentação da libGDX]

Divirta-se explorando o universo em **Meu Jogo Espacial**!
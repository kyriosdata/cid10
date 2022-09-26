## CID-10 CLI 

Aplicação (console) ou CLI (_Command Line Interface_) para localização de
 entradas na CID-10 que faz uso do projeto [cid10-core](https://github.com/kyriosdata/cid10/tree/master/projetos/cid-core). 
 
A execução desta aplicação depende da disponibilidade da Máquina Virtual Java.

## Como gerar e executar jar file?

- `mvn package -P executavel-unico` (gerar .jar executável em um único arquivo)
- `java -jar target/cid10-console.jar 0 shig` (localiza entradas que possuem a sequência "shig"). 

## Como executar a partir do Maven (sem gerar jar executável)?

- `mvn exec:java -Dexec.args="0 shig"`


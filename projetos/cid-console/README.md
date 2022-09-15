## CID-10 CLI 

Aplicação (console) ou CLI (_Command Line Interface_) para localização de
 entradas na CID-10. 
 
A execução desta aplicação depende da disponibilidade da Máquina Virtual Java.

## Primeiros passos

- `mvn package -P executavel-unico` (gerar .jar executável em um único arquivo)

## Uso 

```java -jar target/cid10-console.jar 0 shig```  
Recupera todas as entradas que possuem a sequência "shig". 

## Como executar a partir do Maven (sem gerar jar executável)?

- `mvn exec:java -Dexec.args="0 shig"`


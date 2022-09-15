## CID-10 CLI 

Aplicação (console) ou CLI (_Command Line Interface_) para localização de
 entradas na CID-10. 
 
A execução desta aplicação depende da disponibilidade da Máquina Virtual Java.

## Primeiros passos

- `mvn package -P executavel-unico` (gerar .jar executável em um único arquivo)

## Uso 

```java -jar target/cid10-console.jar 0 shig```  
Recupera todas as entradas que possuem a sequência "shiq". 

## Como executar a partir do Maven (sem gerar jar executável)?

- `mvn exec:java -Dexec.args="0 dengue"`

## Melhorias 

- Eliminar necessidade da JVM na máquina do usuário:
  - Gerando instalador nativo por plataforma: [jpackager](http://openjdk.java.net/jeps/343)
  - Gerando wrapper que inclui a JVM: [jlink](https://www.baeldung.com/jlink
  ). Neste caso a aplicação e a JVM é empacotada e oferecida por meio de
  script .bat ou linux. 
  - [launch4j](http://launch4j.sourceforge.net/) é uma alternativa para a
  opção acima.




## CID-10 CLI 

Aplicação (console) ou CLI (_Command Line Interface_) para localização de
 entradas na CID-10. 
 
A execução desta aplicação depende da disponibilidade da Máquina Virtual Java.

## Uso 

```java -jar target/cid10-console.jar 0 shig```  
Recupera todas as entradas que possuem a sequência "shiq". 

## Melhorias 

- Eliminar necessidade da JVM na máquina do usuário:
  - Gerando instalador nativo por plataforma: [jpackager](http://openjdk.java.net/jeps/343)
  - Gerando wrapper que inclui a JVM: [jlink](https://www.baeldung.com/jlink
  ). Neste caso a aplicação e a JVM é empacotada e oferecida por meio de
  script .bat ou linux. 
  - [launch4j](http://launch4j.sourceforge.net/) é uma alternativa para a
  opção acima.




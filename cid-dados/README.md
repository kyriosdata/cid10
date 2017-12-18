## Geração de dados em formato alternativo

O conteúdo da CID-10 (versão 2008), obtido do DATASUS, é processado e um 
formato alternativo, produzido com foco na eficiência da busca, é utilizado
para produzir uma versão correspondente. 

### Estratégia (ações realizadas)
1. Descrição abreviada foi ignorada.
1. A busca admite o uso ou não de acentos, ou seja, "infecção" e "infeccao" são tratadas igualmente. 
1. O uso de maiúscula ou minúscula é ignorado, ou seja, "HIV" e "hIv" são iguais.

### Geração dos aplicativos (e uso via linha de comandos)
1. Execute `mvn package` 
1. Na linha de comandos `java -jar cid10-dados-2008.1.0.jar dengue` fará com que todas as entradas contendo "dengue" sejam exibidas na saída padrão. Experimente com outros argumentos e o resultado deverá incluir todos os termos fornecidos.

### Como usar (desenvolvedores)

A documentação via [![Javadocs](https://img.shields.io/badge/javadoc-2008--1.0-brightgreen.svg)](http://javadoc.io/doc/com.github.kyriosdata.cid10/cid10-dados)
fornece detalhes para desenvolvedores que desejam empregar a CID-10 em seus projetos.

Além da documentação será necessário acesso ao software e dados pertinentes. 
Obtenha o arquivo **jar** correspondente à versão mais recente ou via Maven conforme a dependência indicada 
abaixo. 

```
<dependency>
  <groupId>com.github.kyriosdata.cid10</groupId>
  <artifactId>cid10-dados</artifactId>
  <version>2008.1.0</version>
</dependency>
```



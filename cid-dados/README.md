## Projeto CID-DADOS

O presente projeto visa produzir uma solução que permite consultas aos
códigos da CID-10. 

A solução permite que cada entrada da CID possa ser acrescida de "texto" 
adicional que também pode ser considerado na busca. Por exemplo, a busca por
 "Síndrome do desfiladeiro" também irá retornar a CID de código G540.  

### Requisitos
- _A busca pode ser feita apenas por parte do código e/ou parte da descrição_. 
A saída produzida deve ser uma entrada da CID-10 que contém todos os
 elementos fornecidos na entrada. Por exemplo, a busca por "dengue" deve trazer
 todas as entradas da CID-10 que contêm "dengue" como parte da descrição.
 Nesse caso, as entradas cujos códigos são A90 e A91. Observe que se a
 consulta for realizada apenas por "engue" (sem o d), o resultado também
 deve incluir aqueles oferecidos para "dengue". A busca por "90" e por "dengue"
 apenas produz a entrada de código A90.

- _Acentos podem ou não ser fornecidos_. Por exemplo, a busca por "infecção" e
 "infeccao" vão produzir o mesmo resultado.
  
- _Maiúsculas e minúsculas são tratadas igualmente_. Ou seja, "dengue" e 
"Dengue" produzem o mesmo resultado.

- _Busca estendida_. A busca por código e/ou descrição da CID pode ser
 estendida por textos (sequências de caracteres) associados às entradas da
  CID. Por exemplo, o texto "Síndrome do desfiladeiro" pode ser associado ao
  código CID G540 e, neste caso, a busca por "desfilad", por exemplo, incluir
  á a CID G540, apesar de não incluir este texto em sua descrição. Vários
  textos podem ser associados a uma mesma entrada.

### Projeto (design) da solução

_Decisão 1_. As categorias e as subcategorias da CID-10 serão reunidas
em uma única lista e a busca será realizada sequencialmente nesta lista.
Dado que a busca deve ignorar o uso ou não da acentuação, uma lista
adicional deverá ser confeccionada e sobre a qual a busca será, de
fato, realizada. A lista original será mantida para a montagem da resposta.

_Decisão 2_. O formato original dos dados de entrada servem de entrada para
processo automático que produz dados usados pela solução. Ou seja, nenhum
processo manual deverá ser utilizado para a produção da estrutura de dados
usada pela solução.

_Decisão 3_. Classe específica deverá encapsular a obtenção (carga) dos dados,
ou seja, deve haver separação entre o código que realiza a consulta e o 
código que obtém os dados. Desta forma, o armazenamento dos dados pode 
mudar de origem sem impacto na classe que realiza a busca.

### Estrutura de dados para a busca (lista adicional)
Tendo em vista os requisitos, é necessário processar os dados da 
CID-10 conforme abaixo:

1. Eliminar colunas não utilizadas, por exemplo, "descrição abreviada".
1. Eliminar acentos. 
1. Substituir terminações de plural pela palavra completa, ou seja, aceleração(oes) por "aceleração" e "acelerações", por exemplo.
1. Eliminar duplicidades.
1. Unir conteúdo de categorias, subcategorias e categorias para oncologia e ordenar a união pelo código. 
   
### Geração dos aplicativos (e uso via linha de comandos)
1. Obtenha o código fonte `git clone https://github.com/kyriosdata/cid10`
1. Execute `cd cid10/cid-dados`
1. Execute `mvn package` 
1. Na linha de comandos `java -jar cid10-dados-2008.1.0.jar 0 dengue` fará 
com que todas as entradas contendo "dengue" sejam exibidas na saída padrão.
Ou seja, duas linhas serão fornecidas. Se a chamada for 
`java -jar cid10-dados-2008.1.0.jar 1 dengue` apenas a última entrada será
fornecida, dado que a ordem inicial das entradas a serem fornecidas é 
a segunda (aquela de índice 1).
 

### Como usar (desenvolvedores)

A documentação fornece detalhes para desenvolvedores que desejam empregar a CID-10 em seus projetos.<br>
[![Javadocs](https://img.shields.io/badge/javadoc-2008--1.0-brightgreen.svg)](http://javadoc.io/doc/com.github.kyriosdata.cid10/cid10-dados)

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



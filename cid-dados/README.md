## CID-10 (inclusive oncologia) (Versão 2008)

A CID-10 é um instrumento empregado para associar a doenças códigos 
únicos. Desta forma, de posse de um código e da versão da CID-10
empregada, sabe-se exatamente qual a doença em questão, independente da
língua ou cultura onde a consulta é realizada, por exemplo.

### Versão original e copyright (licença) da CID-10
Os dados da CID-10 empregados pelo presente projeto encontram-se disponíveis
no portal do DATASUS 
([CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)).
 
Esta versão é de 2008 e os arquivos originais fazem uso do _encoding_ 
ISO-8859-1. 

Detalhes da licença estão disponíveis 
[aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm).

### Organização da CID-10
A CID-10 está organizada conforme a hierarquia ilustrada abaixo. 
Cada capítulo reúne grupos. Cada grupo reúne categorias que,
por sua vez, estão dividas em sub-categorias.

<pre>
CID-10
|
-- Capítulo 
   |
   -- Grupo
      |
      -- Categoria
         |
         -- Sub-categoria
</pre>

A hierarquia acima pode ser ilustrada, por exemplo, para o capítulo 1, 
que trada de "Algumas doenças infecciosas e parasitárias". Este capítulo contém vários grupos, dentre eles o grupo "Doenças infecciosas intestinais" que, por sua vez, inclui as categorias que vão da **A00** até **A09**, inclusive. A categoria **A00** é denominada de "Cólera", enquanto a categoria **A09** é "Diarréia e gastroenterite de origem infecciosa presumível". Conforme acima, cada uma destas categorias está organizada em sub-categorias. A categoria **A00** está subdividida
em três subcategorias: **A000** ("Cólera devida a Vibrio cholerae 01, biótipo 
cholerae"); **A001** ("Cólera devida a Vibrio cholerae 01, biótipo El Tor") e 
**A009** ("Cólera não especificada"). 


## Solução
O presente projeto visa produzir uma solução que permite consultas aos
códigos da CID-10. 

### Requisitos
- _A busca pode ser feita apenas por parte do código e/ou parte da descrição_. A saída produzida deve ser uma entrada da CID-10 que contém todos os elementos fornecidos na entrada, conforme ilustrado abaixo. A busca por "dengue" deve trazer todas as entradas da CID-10 que contêm "dengue" como parte da descrição. Nesse caso, as entradas cujos códigos são A90 e A91. Observe que se a consulta for realizada apenas por "engue" (sem o d), o resultado também deve incluir aqueles oferecidos para "dengue". A busca por "90" e por "dengue" apenas produz a entrada de código A90.

- _Acentos podem ou não ser fornecidos_. Por exemplo, a busca por "infecção" e "infeccao" vão produzir o mesmo resultado. 
- _Maiúsculas e minúsculas são tratadas igualmente_. Ou seja, "HIV" e "hIv" produzem o mesmo resultado.

### Projeto (design) da solução

_Decisão 1_. As categorias e as subcategorias da CID-10 serão reunidas em uma única lista e a busca será realizada sequencialmente nesta lista. Dado que a busca deve ignorar o uso ou não da acentuação, uma lista adicional deverá ser confeccionada e sobre a qual a busca será, de fato, realizada. A lista original será mantida para a montagem da resposta.

_Decisão 2_. O formato original dos dados de entrada servem de entrada para processo automático que produz dados usados pela solução. Ou seja, nenhum processo manual deverá ser utilizado para a produção da estrutura de dados usada pela solução.

### Estrutura de dados para a busca (lista adicional)
Tendo em vista os requisitos, é necessário processar os dados da 
CID-10 conforme abaixo:

1. Eliminar colunas não utilizadas, por exemplo, "descrição abreviada".
1. Eliminar acentos. 
1. Substituir terminações de plural pela palavra completa, ou seja, aceleração(oes) por "aceleração" e "acelerações", por exemplo.
1. Eliminar duplicidades.
1. Unir conteúdo de categorias, subcategorias e categorias para oncologia e ordenar a união pelo código. 
   
### Geração dos aplicativos (e uso via linha de comandos)
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



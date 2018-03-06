## CID-10 (inclusive oncologia) (Versão 2008)

O presente projeto processa o conteúdo da CID-10 (versão 2008), obtido do DATASUS ([CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)), visando a
produção de um formato alternativo ao original com foco na eficiência da busca.

## Versão original e copyright (licença) da CID-10
A versão utilizada encontra-se disponível pelo portal do 
DATASUS 
([CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)). 
Em particular, trata-se da versão de 2008. Arquivos fazem uso 
de ISO-8859-1. 

Detalhes da licença estão disponíveis 
[aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm).

## Organização da CID-10
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

---

### Requisitos
- A busca pode ser feita apenas por parte do código, parte da descrição ou de ambos. 
- A busca por "dengue" deve trazer todas as entradas da CID-10 que contêm "dengue" como parte da descrição. Nesse caso, as entradas cujos códigos são A90 e A91. Observe que se a consulta for realizada apenas por "engue" (sem o d), o resultado também deve incluir aqueles oferecidos para "dengue".
- A busca por "90" e por "dengue" apenas produz a entrada de código A90.

### Projeto (design) da solução

As categorias e as subcategorias da CID-10 serão reunidas em uma única lista e a busca será realizada sequencialmente nesta lista. Dado que a busca deve ignorar o uso ou não da acentuação, uma lista adicional deverá ser confeccionada e sobre a qual a busca será, de fato, realizada. A lista original será mantida para a montagem da resposta.

### Orientação principal

Formato original dos dados de entrada deverão servir de entrada para processo automático que produz dados usados pela solução. Ou seja, nenhum processo manual deverá ser utilizado para a produção da estrutura de dados usada pela solução.

### Produção dos dados para busca (lista adicional)

1. Produzir dados que serão empregados pela solução a partir dos arquivos originais.
   - Eliminar colunas não utilizadas.
   - Eliminar acentos. 
   - Substituir terminações de plural pela palavra completa, ou seja, aceleração(oes) por "aceleração" e "acelerações", por exemplo.
   - Eliminar duplicidades.
   - Unir conteúdo de categorias, subcategorias e categorias para oncologia e ordenar a união pelo código. 
   
### Estratégia (ações realizadas)
1. Descrição abreviada foi ignorada.
1. A busca admite o uso ou não de acentos, ou seja, "infecção" e "infeccao" são tratadas igualmente. 
1. O uso de maiúscula ou minúscula é ignorado, ou seja, "HIV" e "hIv" são iguais.

### Geração dos aplicativos (e uso via linha de comandos)
1. Execute `mvn package` 
1. Na linha de comandos `java -jar cid10-dados-2008.1.0.jar dengue` fará com que todas as entradas contendo "dengue" sejam exibidas na saída padrão. Experimente com outros argumentos e o resultado deverá incluir todos os termos fornecidos.

### Como usar (desenvolvedores)

A documentação fornece detalhes para desenvolvedores que desejam empregar a CID-10 em seus projetos.
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



# Acesso à classificação internacional de doenças (CID-10)

O presente projeto visa produzir um serviço por meio do qual
usuários possam ter acesso ao conteúdo da CID-10, inclusive
por meio de mecanismo de busca. 

## Versão original e copyright (licença) da CID-10
A versão utilizada encontra-se disponível pelo portal do 
DATASUS 
([CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)). 
Em particular, trata-se da versão de 2008. Arquivos fazem uso 
de ISO-885901. 

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
que trada de "Algumas doenças infecciosas e parasitárias". Este capítulo contém 
vários grupos, um deles é o grupo que vai das categorias **A00** até **A09**, inclusive.
Este grupo é descrito por "Doenças infecciosas intestinais". 
O capítulo 1, contudo, inclui as categorias que vão de **A00** ("Cólera") até **B99** ("Doenças infecciosas, 
outras e as não especificadas"). A categoria **A00**, por sua vez, está subdividida
em três subcategorias: **A000** ("Cólera devida a Vibrio cholerae 01, biótipo 
cholerae"); **A001** ("Cólera devida a Vibrio cholerae 01, biótipo El Tor") e 
**A009** ("Cólera não especificada"). 

Convém esclarecer que a busca é realizada exclusivamente sobre as subcategorias e categorias. 

## Projeto (design) da solução

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
   
 
### Organização em projetos

- Produção das estruturas de dados ([cid-dados](https://github.com/kyriosdata/cid10/tree/master/cid-dados)).
- Micro-serviço que oferece busca e informações sobre a CID-10 ([cid-busca](https://github.com/kyriosdata/cid10/tree/master/cid-busca)).
- Web App para consulta e acesso ao conteúdo da CID-10 ([cid-gui](https://github.com/kyriosdata/cid10/tree/master/cid-gui)).


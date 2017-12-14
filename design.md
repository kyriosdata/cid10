# Acesso à classificação internacional de doenças (CID-10)

O presente projeto visa produzir um serviço por meio do qual
usuários possam ter acesso ao conteúdo da CID-10, inclusive
por meio de mecanismo de busca. 

## CID-10
A Classificação Internacional de Doenças (CID) empregada pelo Brasil é a CID-10. Profissionais de saúde, em geral, empregam os códigos presentes nessa classificação para se referirem a doenças de forma não ambígua, por exemplo, usam o código **A90** em vez de "dengue". 

O presente projeto implementa dois módulos: (a) um para acesso à CID-10 por meio de uma interface gráfica via browser (apenas com o propósito de ilustração) e (b) um para fornecer um serviço de busca aos códigos da CID-10. Uma [Web App](http://www.icd10codesearch.com/) similar pode ser consultada para ilustrar a intenção do presente projeto.

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

---

### Requisitos
- A busca pode ser feita apenas por parte do código, parte da descrição ou de ambos. 
- A busca pela descrição por "dengue" deve trazer todas as entradas da CID-10 que contêm "dengue" como parte da descrição. Nesse caso, as entradas cujos códigos são A90 e A91. Observe que se a consulta for realizada apenas por "engue" (sem o d), o resultado também deve incluir aqueles oferecidos para "dengue".
- A busca por "90" pelo código e por "dengue" pela descrição, apenas produz a entrada de código A90.
- O módulo **cid10-2008.jar** deve incluir o código que implementa a busca e os dados propriamente ditos sobre os quais a busca é realizada. Ou seja, todas as informações necessárias para oferecer as funcionalidades acima devem estar contidas nesse único arquivo **cid10-2008.jar**.
- O módulo **cid10-2008.jar** deve incluir a classe CID10 que deve conter os métodos públicos **load** e **unload**. O primeiro carrega e oferece oportunidade para iniciar o componente, enquanto o segundo indica que o componente não mais será utilizado. 
- A principal operação dessa interface é **search**, que recebe como argumento um vetor de sequências de caracteres (**String[]**). Esse método retorna **null** caso a entrada fornecida não identifique código algum correspondente e, caso contrário, um vetor de sequências de caracteres, contendo tantas entradas quanto os códigos localizados. A resposta sempre é o código seguido de um espaço em branco seguido da descrição. Por exemplo, um retorno possível é **A90 Dengue (dengue clássico)**, ou seja, o código A90 seguido de espaço que é seguido da descrição correspondente ao código A90.

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
   
 
### Organização em projetos

- Produção das estruturas de dados ([cid-dados](https://github.com/kyriosdata/cid10/tree/master/cid-dados)).
- Micro-serviço que oferece busca e informações sobre a CID-10 ([cid-busca](https://github.com/kyriosdata/cid10/tree/master/cid-busca)).
- Web App para consulta e acesso ao conteúdo da CID-10 ([cid-gui](https://github.com/kyriosdata/cid10/tree/master/cid-gui)).


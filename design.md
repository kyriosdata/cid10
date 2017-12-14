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


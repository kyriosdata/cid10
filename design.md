## Projeto (design) da solução

As categorias e as subcategorias da CID-10 serão reunidas em uma única lista e a busca será realizada sequencialmente nesta lista. 

### Orientação principal

Formato original dos dados de entrada deverão servir de entrada para processo automático que produz dados usados pela solução. Ou seja, nenhum processo manual deverá ser utilizado para a produção da estrutura de dados usada pela solução.

### Produção dos dados para busca

1. Produzir dados que serão empregados pela solução a partir dos arquivos originais.
   - Eliminar colunas não utilizadas.
   - Eliminar acentos. 
   - Substituir terminações de plural pela palavra completa, ou seja, aceleração(oes) por "aceleração" e "acelerações", por exemplo.
   - Eliminar duplicidades.
   - Quando busca for por código realizar busca binária. 

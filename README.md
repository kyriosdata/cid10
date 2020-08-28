[![Build Status](https://travis-ci.com/kyriosdata/cid10.svg?branch=master)](https://travis-ci.com/kyriosdata/cid10)

## Classificação Internacional de Doenças 

Objetivo:

> Biblioteca auto contida, compacta, e rápida, escrita em Java,  para acesso e localização do conteúdo da CID-10.

Códigos da CID-10 são empregados para se referir a doenças de forma não ambígua, por exemplo, 
o código **A90** é usado para se referir a "dengue". Desta forma, em vez de usar "dengue", o código
**A90** é empregado.

Componentes do presente projeto:

- Informações gerais, dados e as estruturas de dados para agilizar a consulta aos códigos da CID-10 ([cid-dados](https://github.com/kyriosdata/cid10/tree/master/cid-dados)).
- Micro-serviço que oferece busca e informações sobre a CID-10 ([cid-busca](https://github.com/kyriosdata/cid10/tree/master/cid-busca)).
- Web App para consulta e acesso ao conteúdo da CID-10 ([cid-gui](https://github.com/kyriosdata/cid10/tree/master/cid-gui)).

Observe que o primeiro e segundo componentes acima podem ser empregados por outros softwares, enquanto o terceiro é para uso humano.


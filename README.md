# Acesso à classificação internacional de doenças (CID-10)

Profissionais de saúde empregam os códigos da CID-10 para se referirem a doenças de forma não ambígua, por exemplo, usam o código **A90** em vez de "dengue". O presente projeto visa oferecer acesso ao conteúdo da CID-10 tanto para profissionais de saúde (consulta) quanto para desenvolvedores de software (dado e programas). 

A solução proposta inclui os seguintes componentes:

- Informações gerais sobre a CID-10 e produção de estruturas de dados para agilizar a consulta aos códigos ([cid-dados](https://github.com/kyriosdata/cid10/tree/master/cid-dados)).
- Micro-serviço que oferece busca e informações sobre a CID-10 ([cid-busca](https://github.com/kyriosdata/cid10/tree/master/cid-busca)).
- Web App para consulta e acesso ao conteúdo da CID-10 ([cid-gui](https://github.com/kyriosdata/cid10/tree/master/cid-gui)).

Observe que o primeiro e segundo componentes acima podem ser empregados por outros softwares, enquanto o terceiro é para uso humano.


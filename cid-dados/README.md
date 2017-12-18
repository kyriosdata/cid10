## Geração de dados em formato alternativo

O conteúdo da CID-10 (versão 2008), obtido do DATASUS, é processado e um 
formato alternativo, produzido com foco na eficiência da busca, é utilizado
para produzir uma versão correspondente. 

### Estratégia (ações realizadas)
1. Descrição abreviada foi ignorada.
1. A busca admite o uso ou não de acentos, ou seja, "infecção" e "infeccao" são tratadas igualmente. 
1. O uso de maiúscula ou minúscula é ignorado, ou seja, "HIV" e "hIv" são iguais.

### Geração dos aplicativos
1. Execute `mvn package` 




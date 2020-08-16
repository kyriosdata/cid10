## Componente REACT para acesso à CID-10

### Requisitos

- Selecionar e exibir entradas da CID-10 que apresentam todas as "palavras" presentes na sequência de busca.
- A sequência de busca está dividida em palavras separadas por um ou mais espaços em branco.
- Uma busca remota é realizada apenas a partir da primeira palavra disponível com pelo menos 3 caracteres.
- Antes que o terceiro caractere seja digitado, informar o usuário de
  que é preciso pelo menos 3 caracteres.
- Quando nova "palavra" de busca é fornecida, então busca será realizada
  apenas no conteúdo já disponível localmente. Por exemplo, se está
  disponível o resultado para a busca da palavra p1, então quando o
  primeiro caractere da palavra p2 é fornecido, a busca será realizada
  sobre o conteúdo disponível localmente.

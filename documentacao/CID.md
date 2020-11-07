### CID-10 (inclusive oncologia) (Versão 2008)

A CID-10 é um instrumento que associa códigos
únicos a doenças. Desta forma, de posse de um código e da versão da CID-10
empregada, sabe-se exatamente qual a doença em questão, independente da
língua ou cultura onde a consulta é realizada, por exemplo.

### Exemplos da CID-10

Neste trabalho uma entrada da CID-10 é representada pelo código,
pela restrição de sexo e pela descrição em questão. A tabela abaixo ilustra
três das entradas disponíveis da CID-10, apenas para ilustrar uma entrada que
não apresenta restrição de sexo e outras duas, cada uma delas específica de um
sexo.

| Código | Sexo | Descrição            |
| ------ | :--: | -------------------- |
| A038   |  -   | Outras shigueloses   |
| B260   |  M   | Orquite por caxumba  |
| E280   |  F   | Excesso de estrógeno |

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

A hierarquia exibida acima pode ser ilustrada, por exemplo, para o capítulo 1,
que trada de _Algumas doenças infecciosas e parasitárias_, conforme abaixo. Em tempo,
ao todo são 22 capítulos.

O capítulo 1, _Algumas doenças infecciosas e parasitárias_, está subdividido em 21 grupos. 
O primeiro deles é o grupo de _Doenças infecciosas intestinas_. O grupo seguinte é _Tuberculose_.
O último dos grupos que faz parte do capítulo 1 é o grupo _Outras doenças infecciosas_. 

O grupo _Doenças infecciosas intestinais_ é subdividido em 9 categorias: 
- cólera; 
- febres tifóide e paratifóide; 
- outras infecções por Salmonella; 
- shiguelose;
- outras infecções instestinais bacterianas;
- outras intoxicações alimentares bacterianas, não classificadas em outra parte; 
- amebíase; 
- outras doenças intestinais por protozoários; 
- infecções intestinais virais, outras e as não especificadas; e, 
- diarréia e gastroenterite de origem infecciosa presumível.

Cada categoria possui um código único, por exemplo, a categoria _cólera_ possui o
código **A00**, enquanto _diarréia e gastroenterite de origem infecciosa presumível_ tem
como código **A09**. Noutras palavras, o grupo _Doenças infecciosas intestinais_
reúne as categorias que vão do código **A00** até **A09**, inclusive. 

As subcategorias subdividem as categorias. Por exemplo, a categoria _cólera_, **A00**,
possui três subcategorias: (a) _cólera devida a Vibrio cholerae 01, biótipo cholerae_; (b) _cólera devida a Vibrio cholerae 01 biótipo El Tor_ e, por fim, (c) _cólera não especificada_. Respectivamente os
códigos únicos destas subcategorias são **A000**, **A001** e **A009**. Observe que o
código único de uma subcategoria possui como prefixo o código da categoria da qual faz parte.

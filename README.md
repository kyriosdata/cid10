
[![Build Status](https://app.travis-ci.com/kyriosdata/cid10.svg?branch=master)](https://app.travis-ci.com/kyriosdata/cid10)

## Classificação Internacional de Doenças (CID-10 versão 2008)

Objetivo:

> Biblioteca de conveniência para acesso fácil e rápido à CID-10 (versão 2008). 

### Importante

- A CID-10 é um sistema de codificação ([aqui](https://www.hl7.org/fhir/r4/icd.html)) identificado por
http://hl7.org/fhir/sid/icd-10-2008, dado que a versão empregada é de 2008. O OID da CID-10 é	2.16.840.1.113883.6.3.

- A versão 2008 não é a mais recente. Portanto, não encontrará, por exemplo, **A92.8**, dentre outros. 

Organização:

- [Informações](./documentacao/CID.md) sobre a CID-10
- [Documentação](documentacao/requisitos.md) do projeto


### Projetos

- [Console](./projetos/cid-console) para consulta via linha de comandos. Ilustra uso.
- [AWS Lambda](./projetos/cid-lambda) disponibiliza recurso de consulta via função AWS Lambda.
- [CID-10](./projetos/cid-core) implementa biblioteca que oferece consulta sobre a CID-10 (versão 2008). Inclui tratamento de dados para agilizar a consulta. Solução "caseira". 
- [cid-lucene](./projetos/cid-lucene) implementação usando a biblioteca Apache [Lucene](https://lucene.apache.org/).
- 



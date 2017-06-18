 Microserviço
Implementação de microserviço que responde requisições de consulta por
entradas da CID-10.

# Uso
O microserviço responde a uma única requisição POST cujo corpo fornece
o parâmetro *q*, conforme ilustrada abaixo para o utilitário _curl_. 

Via linha de comandos uma requisição pode ser disparada conforme abaixo,
onde a entrada da CID-10 procurada deve incluir **engue** assim 
como **90**, seja na descrição ou no código.

```
curl http://localhost:8080/cid -d "q=engue 90"
```

# Microserviço
Implementação de _endpoint_ para busca em códigos da CID-10.

# Uso
Via linha de comandos uma requisição pode ser disparada conforme abaixo,
onde a entrada da CID-10 procurada deve incluir **engue** assim como **90**, seja na descrição ou no código.

```
curl -G "http://localhost:8080/cid" --data-urlencode "q=engue 90"
```

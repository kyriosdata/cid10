Implementação de microserviço que responde requisições de consulta por
entradas da CID-10.

# Uso
O microserviço responde a uma única requisição POST cujo corpo fornece
o parâmetro *q*. Por exemplo, a busca de um código
que contém **engue** e **90**, a consulta correspondente é

```
curl http://localhost:8080/cid -d "q=engue 90"
```

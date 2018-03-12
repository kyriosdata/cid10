
Microserviço que fornece informações sobre a CID-10, inclusive consultas.

# Configuração
Defina a variável de ambiente **SPRING_BOOT_KEYSTORE_PASSWORD** com a senha empregada para criar
o _keystore_. Detalhes em https://www.thomasvitale.com/https-spring-boot-ssl-certificate/. 

# Uso

```
curl https://localhost:8443/capitulos -k
curl https://localhost:8443/busca/a923/0
```

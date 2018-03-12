
Microserviço que fornece informações sobre a CID-10, inclusive consultas.

# Configuração
Defina a variável de ambiente **SPRING_BOOT_KEYSTORE_PASSWORD** com a senha empregada para criar
o _keystore_. Detalhes em https://www.thomasvitale.com/https-spring-boot-ssl-certificate/, caso
o _profile_ ativo seja **https** e usa a porta 8443. O _profile_ padrão usa http (porta 8080).

# Para executar

```
java -jar cid-busca-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
```

ou simplesmente 
```
java -jar cid-busca-0.0.1-SNAPSHOT.jar
```

ou para uso de https, 

```
java -jar cid-busca-0.0.1-SNAPSHOT.jar --spring.profiles.active=https
```


# Uso

```
curl https://localhost:8443/capitulos -k
curl https://localhost:8443/busca/a923/0
```

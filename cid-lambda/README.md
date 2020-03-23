## CID-10 CLI 

Aplicação (console) ou CLI (_Command Line Interface_) para localização de
 entradas na CID-10.

### Executando função (AWS Lambda)

- `aws configure` (caso ainda não configurado). Será necessário um usuário
com conta apta a executar operações no AWS Lambda e API Gateway. 

- `aws lambda invoke --cli-binary-format raw-in-base64-out --function-name
 cid10 --payload "0 dengue" saida.txt` (observe que o nome da função pode ser
  outro e não necessariamente 'cid10', assim como a consulta)



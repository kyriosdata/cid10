## CID-10 (AWS Lambda)

Oferece serviço de consulta aos códigos da CID 10 por meio de uma Lambda function (AWS).

### Como disponibilizar função?

- Crie um usuário no AWS para esta finalidade específica, por exemplo, 
**ms-cid10**. Naturalmente, seu 
projeto pode adotar várias outras alternativas. 
- Atribua a este usuário a política (_policy_), acrescente a política para
permitir **update-function-code**. 
 
- `mvn package` (arquivo fat jar será gerado no diretório **target**, neste
 momento o tamanho deste arquivo jar é 322K)
- `aws lambda update-function-code --function-name cid10 --zip-file fileb
://./target/cid10-lambda-2008.1.1.jar`
- Via AWS Console, faça _upload_ e a função já estará disponível. 

### Executando função (AWS Lambda) (AWS CLI)

- `aws configure` (caso ainda não configurado). Será necessário um usuário
com conta apta a executar operações no AWS Lambda e API Gateway. 

- `aws lambda invoke --cli-binary-format raw-in-base64-out --function-name
 cid10 --payload '"0 dengue"' saida.txt` (observe que o nome da função pode ser
  outro e não necessariamente 'cid10', assim como a consulta. Adicionalmente, as aspas entre 0 dengue é para indicar que se trata de uma sequência de caracteres, string, que está sendo enviada.)

### Executando função (AWS Lambda) (curl)
Neste caso, por meio de API Gateway (AWS) configurado para oferecer _endpoint_ correspondente à função (Lambda Function).

- `curl -X POST --data '"0 dengue"' https://<id da funcao>.execute-api.sa-east-1
.amazonaws.com/padrao`

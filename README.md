# twitter-api

Backend: Java + Spring  -  H2  -  Vue.js

- O banco pode ser acessado pelo http://localhost:8080/h2 - username: sa | password:
- Para atualizar as configurações do banco, precisa alterar o arquivo application.properties
- Através do endpoint http://localhost:8080/init dois usuários pré-cadastrados são inseridos no banco


# Endpoints
  ## Users
    http://localhost:8080/users
   - Criar User
   - Método: POST
   - Body: { "name": "nome" }
   
   ##
    http://localhost:8080/users
   - Recuperar Users
   - Método: GET
   
   ##
    http://localhost:8080/users/{id}
   - Recuperar User por ID
   - Método: GET
   
   ##
    http://localhost:8080/users/follow
   - Dar follow em determinado user
   - Método: PUT
   - Body: { "userId": 1, "anotherUserId": 2 }
   
   ##
    http://localhost:8080/users/follow
   - Dar unfollow em determinado user
   - Método: PUT
   - Body: { "userId": 1, "anotherUserId": 2 }
   
  ## Tweeters    
    http://localhost:8080/tweeters
   - Criar User
   - Método: POST
   - Body: { "message": "mensagem", "userId", 1 }
   
   ##
    http://localhost:8080/tweeters
   - Recuperar todos tweeters (independente de usuário)
   - Método: GET
   
   ##
    http://localhost:8080/tweeters/feed
   - Recuperar tweeters de um determinado usuário e dos que ele segue
   - Método: GET
   - Body: { "userId": 2 }
   
   ##
    http://localhost:8080/tweeters/{userId}
   - Recuperar tweeters de um determinado usuário e dos que ele segue
   - Método: GET
   
   
Executar o comando "mvn clean package" para fazer o build do backend e gerar o arquivo .jar para hospedagem.

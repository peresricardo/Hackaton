# Hackaton

<hr>
Todos os nossos micro-serviços foram desenvolvidos utlizando a versão <b>21 do Java - AmazonCorretto.</b>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql&logoColor=white)

[![My Skills](https://skillicons.dev/icons?i=docker,&perline=3)](https://skillicons.dev)

## 🔧 Instalação

```shell
git clone https://github.com/peresricardo/Hackaton.git
```
### Docker

#### Limpar, compilar e gerar imagem para o docker

Na raiz do projeto existe um arquivo chamado buildAll.bat, executando esse arquivo ele irá
fazer o build, gerar a imagem docker automaticamente de todos os serviços.

Para executar o arquivo buildAll.bat execute um prompt de command "cmd"
vá para a pasta onde baixou o projeto e execute o comando:
```sh
buildAll
```

Para executar os serviços de uma única vez execute o docker compose,
vá para pasta do projeto e execute o comando:
```sh
docker-compose up -d
```


## 🧪 Execução de testes no projeto
<hr>

- Para executar os testes unitários:

```sh
mvn test
```
- Para executar os testes integrados:
```sh
mvn test -P integration-test
```
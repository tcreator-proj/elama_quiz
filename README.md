# Getting Started

## Describe
This is a telegram bot project for the Elama company. 
Passing the quiz, you select recommendations for yourself on starting and running advertising companies.

## Start bot

### Use DockerHub

Pull image from Dockerhub
```shell
 docker pull retrocreator882/elama_quiz_elama:1.0.0
```
and run with .env environment (exercise in env.sample)
```shell
 docker run -itd --env-file .env -P retrocreator882/elama_quiz_elama:1.0.0
```

### Use Docker-compose
Build image with docker-compose
```shell
docker-compose build
```
and run with .env environment (exercise in env.sample)
```shell
docker run -itd --env-file .env -P elama_quiz_elama
```
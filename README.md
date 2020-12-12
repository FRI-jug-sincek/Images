# Cimr: Images microservice

## Prerequisites

```bash
mvn clean package
//poženi docker image images
java -jar images-api-1.0.0-SNAPSHOT.jar

//docker building
docker build -f Dockerfile_with_maven_build -t frijugsincek/images:latest .
docker push frijugsincek/images:latest   

docker network create cimr

docker run -d --name images -e POSTGRES_USER=dbimage -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=images -p 5432:5432 --network cimr postgres:13
```

geslo za povezavo na elephantdb bazo je treba ročno nastavit v k8s configuraciji dokler ne uspostavimo secretov

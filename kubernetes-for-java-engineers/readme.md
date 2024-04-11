## Build the application

 ```sh
 ./mvnw clean package
 ```

## Build Docker images

 ```sh
 docker build -t song-service .  --build-arg baseDir=./song-service
 docker build -t resource-service .  --build-arg baseDir=./resource-service
 ```

## Apply Kubernetes files
```sh
kubectl apply -f .
```

## Delete Kubernetes instances 
```sh
kubectl delete -f .
```
Uporabni ukazi:

kubectl apply -f images-deployment.yaml
kubectl logs -f images-deployment-6679d6bb76-lhtgc
kubectl get pods

kubectl scale deployment images-deployment --replicas=0
kubectl scale deployment images-deployment --replicas=1

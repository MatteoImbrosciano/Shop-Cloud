#!/bin/bash

# Start Minikube
minikube start 

cd Shop-Cloud

# Apply Kubernetes configurations
kubectl apply -f Shop-Cloud/product-shop-client-deployment.yaml 
kubectl apply -f Shop-Cloud/product-shop-server-deployment.yaml
kubectl apply -f Shop-Cloud/product-shop-session-deployment.yaml
kubectl apply -f Shop-Cloud/product-shop-client-service.yaml 
kubectl apply -f Shop-Cloud/product-shop-server-service.yaml 
kubectl apply -f Shop-Cloud/product-shop-session-service.yaml
#!/bin/bash

# Install Git
sudo yum install git -y

# Clone the repository
git clone https://github.com/MatteoImbrosciano/Shop-Cloud.git

# Move into the cloned repository directory
cd Shop-Cloud

# Install Docker
sudo yum install docker -y
sudo systemctl start docker
sudo usermod -aG docker ec2-user
newgrp docker

# Pull Docker images
docker pull matteo16/product-shop:client
docker pull matteo16/product-shop:server
docker pull matteo16/product-shop:session

# Install Minikube
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64

# Start Minikube
minikube start

# Install kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo mv kubectl /usr/local/bin/

# Apply Kubernetes configurations
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-client-deployment.yaml
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-server-deployment.yaml
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-session-deployment.yaml
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-client-service.yaml
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-server-service.yaml
kubectl apply -f Progetto_cloud/Shop-Cloud/product-shop-session-service.yaml

#!/bin/bash

echo "Iniciando limpeza completa do ambiente Docker..."

echo "Parando containers..."
docker-compose down --remove-orphans

echo "Removendo volumes..."
docker volume rm literalura_postgres_data 2>/dev/null || true

echo "Removendo redes..."
docker network rm literalura_literalura-network 2>/dev/null || true

echo "Removendo imagens do projeto..."
docker rmi literalura-app:latest 2>/dev/null || true
docker rmi $(docker images -f "dangling=true" -q) 2>/dev/null || true

echo "Removendo containers orfaos..."
docker container prune -f --filter "label=com.docker.compose.project=literalura" 2>/dev/null || true

echo "Verificando recursos restantes..."
docker system prune -f --volumes

echo "Limpeza completa concluida!"
echo "Espaco recuperado:"
docker system df
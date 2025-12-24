#!/bin/bash

echo "Iniciando LiterAlura..."

if ! docker info > /dev/null 2>&1; then
    echo "Docker nao esta rodando. Por favor, inicie o Docker e tente novamente."
    exit 1
fi

echo "Iniciando banco de dados..."
docker-compose up -d postgres

echo "Aguardando PostgreSQL ficar pronto..."
sleep 5

echo "Iniciando aplicacao interativa..."
echo "-------------------------------------------------------"
# Usa 'run' em vez de 'up' para garantir interatividade correta do console Java
docker-compose run --rm --service-ports app
echo "-------------------------------------------------------"

echo "Aplicacao encerrada."

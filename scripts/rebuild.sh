#!/bin/bash

set -e

echo "RECONSTRUCAO COMPLETA DO AMBIENTE LITERALURA"
echo ""

./scripts/clean.sh

echo ""
echo "Aguardando estabilizacao do Docker..."
sleep 3

echo "Reconstruindo imagens Docker..."
docker-compose build --no-cache

echo "Iniciando PostgreSQL..."
docker-compose up -d postgres

echo "Aguardando PostgreSQL ficar pronto..."
for i in {1..30}; do
    if docker-compose exec -T postgres pg_isready -U literalura_user -d literalura > /dev/null 2>&1; then
        echo "PostgreSQL pronto!"
        break
    fi
    echo -n "."
    sleep 2
done

echo ""
echo "Iniciando aplicacao LiterAlura (Modo Interativo)..."
echo "-------------------------------------------------------"
# --service-ports permite mapear portas se necessario
# --rm remove o container apos o uso
docker-compose run --rm --service-ports app
echo "-------------------------------------------------------"

echo ""
echo "AMBIENTE ENCERRADO."

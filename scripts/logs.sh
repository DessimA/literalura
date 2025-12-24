#!/bin/bash
# scripts/logs.sh

echo "📋 Logs do LiterAlura"
echo "===================="
echo ""
echo "Escolha uma opção:"
echo "1) Logs da aplicação"
echo "2) Logs do PostgreSQL"
echo "3) Logs de ambos"
echo ""
read -p "Opção [1-3]: " option

case $option in
    1)
        docker-compose logs -f app
        ;;
    2)
        docker-compose logs -f postgres
        ;;
    3)
        docker-compose logs -f
        ;;
    *)
        echo "Opção inválida"
        exit 1
        ;;
esac

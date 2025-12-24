#!/bin/bash

echo "Executando testes automatizados (Modo Silencioso)..."

docker-compose run --rm --entrypoint "mvn test -q -Dorg.slf4j.simpleLogger.defaultLogLevel=warn" test

STATUS=$?

if [ $STATUS -eq 0 ]; then
    echo ""
    echo "SUCESSO: Todos os testes passaram!"
else
    echo ""
    echo "FALHA: Alguns testes falharam."
    exit $STATUS
fi

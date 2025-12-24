#!/bin/bash
# scripts/test-rebuild.sh

echo "🧪 Teste de Idempotência - LiterAlura"
echo "====================================="
echo ""
echo "Este script vai executar 3 ciclos completos de rebuild"
echo "para garantir que o ambiente é 100% idempotente."
echo ""
read -p "Continuar? (s/n) " -n 1 -r
echo ""

if [[ ! $REPLY =~ ^[Ss]$ ]]; then
    echo "Teste cancelado."
    exit 0
fi

for i in {1..3}; do
    echo ""
    echo "🔄 ================================"
    echo "🔄 CICLO $i de 3"
    echo "🔄 ================================"
    echo ""
    
    ./scripts/rebuild.sh
    
    if [ $? -ne 0 ]; then
        echo "❌ Falha no ciclo $i"
        exit 1
    fi
    
    if [ $i -lt 3 ]; then
        echo ""
        echo "⏳ Aguardando 5 segundos antes do próximo ciclo..."
        sleep 5
    fi
done

echo ""
echo "✅ ================================"
echo "✅ TESTE DE IDEMPOTÊNCIA CONCLUÍDO"
echo "✅ Todos os 3 ciclos foram bem-sucedidos!"
echo "✅ ================================"

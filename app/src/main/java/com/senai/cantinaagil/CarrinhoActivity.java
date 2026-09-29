package com.senai.cantinaagil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class CarrinhoActivity extends AppCompatActivity implements CarrinhoAdapter.OnCartChangedListener {

    private TextView tvSubtotal;
    private TextView tvDesconto;
    private TextView tvTotal;
    private MaterialButton btnAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Referências das views do painel de resumo
        tvSubtotal = findViewById(R.id.tv_carrinho_subtotal_valor);
        tvDesconto = findViewById(R.id.tv_carrinho_desconto_valor);
        tvTotal = findViewById(R.id.tv_carrinho_total_valor);
        btnAvancar = findViewById(R.id.btn_avancar_pagamento);

        // Botão Voltar
        ImageButton btnVoltar = findViewById(R.id.btn_voltar_carrinho);
        btnVoltar.setOnClickListener(v -> finish());

        // Configuração do RecyclerView do Carrinho
        RecyclerView rvItens = findViewById(R.id.rv_itens_carrinho);
        rvItens.setLayoutManager(new LinearLayoutManager(this));
        
        CarrinhoAdapter adapter = new CarrinhoAdapter(MockData.getCarrinho(), this);
        rvItens.setAdapter(adapter);

        // Calcula valores iniciais
        onCartChanged();

        // Ação do Botão de Finalização / Pagamento
        btnAvancar.setOnClickListener(v -> {
            if (MockData.getCarrinho().isEmpty()) {
                Toast.makeText(this, "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show();
                return;
            }
            double total = MockData.calcularTotal();
            double desconto = total > 20.0 ? 2.0 : 0.0;
            double totalFinal = Math.max(0.0, total - desconto);

            Intent intent = new Intent(CarrinhoActivity.this, PagamentoActivity.class);
            intent.putExtra("total_pagar", totalFinal);
            startActivity(intent);
        });
    }

    @Override
    public void onCartChanged() {
        double subtotal = MockData.calcularTotal();
        double desconto = subtotal > 20.0 ? 2.0 : 0.0;
        double total = Math.max(0.0, subtotal - desconto);

        tvSubtotal.setText(String.format(Locale.getDefault(), "R$ %.2f", subtotal));
        tvDesconto.setText(String.format(Locale.getDefault(), "- R$ %.2f", desconto));
        tvTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", total));
    }
}

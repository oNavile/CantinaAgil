package com.senai.cantinaagil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class PagamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Recebe o total a pagar
        double totalPagar = getIntent().getDoubleExtra("total_pagar", 0.0);

        // Exibe o total formatado de forma expandida
        TextView tvTotal = findViewById(R.id.tv_pagamento_valor_total);
        tvTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", totalPagar));

        // Botão Voltar
        ImageButton btnVoltar = findViewById(R.id.btn_voltar_pagamento);
        btnVoltar.setOnClickListener(v -> finish());

        // Botão Finalizar e Confirmar Pagamento
        MaterialButton btnConfirmar = findViewById(R.id.btn_confirmar_pagamento);
        btnConfirmar.setOnClickListener(v -> {
            // Limpa o carrinho após finalizar o pedido
            MockData.limparCarrinho();

            // Abre a tela de sucesso
            Intent intent = new Intent(PagamentoActivity.this, SucessoActivity.class);
            startActivity(intent);
        });
    }
}

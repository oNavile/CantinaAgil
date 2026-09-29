package com.senai.cantinaagil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Random;

public class SucessoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucesso);

        // Gera um número de senha aleatório para exibição
        int numeroSenha = new Random().nextInt(900) + 100;
        TextView tvSenha = findViewById(R.id.tv_sucesso_senha_numero);
        tvSenha.setText(String.format(java.util.Locale.getDefault(), "#%d", numeroSenha));

        // Botão Novo Pedido (limpa a pilha de atividades e volta ao início)
        MaterialButton btnNovoPedido = findViewById(R.id.btn_novo_pedido);
        btnNovoPedido.setOnClickListener(v -> {
            Intent intent = new Intent(SucessoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}

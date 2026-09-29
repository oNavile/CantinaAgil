package com.senai.cantinaagil;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardapioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        // Recebe o nome da categoria selecionada
        String categoriaNome = getIntent().getStringExtra("categoria_nome");
        if (categoriaNome == null) {
            categoriaNome = "Salgados Vitrine";
        }

        // Configura o título da categoria na Toolbar
        TextView tvTitulo = findViewById(R.id.tv_cardapio_categoria_titulo);
        tvTitulo.setText(categoriaNome);

        // Botão Voltar
        ImageButton btnVoltar = findViewById(R.id.btn_voltar_categoria);
        btnVoltar.setOnClickListener(v -> finish());

        // RecyclerView de Produtos
        RecyclerView rvProdutos = findViewById(R.id.rv_produtos);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        
        ProdutoAdapter adapter = new ProdutoAdapter(this, MockData.getProdutosPorCategoria(categoriaNome));
        rvProdutos.setAdapter(adapter);
    }
}

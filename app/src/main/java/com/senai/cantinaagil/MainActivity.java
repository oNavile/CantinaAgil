package com.senai.cantinaagil;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuração do RecyclerView de Categorias
        RecyclerView rvCategorias = findViewById(R.id.rv_categorias);
        rvCategorias.setLayoutManager(new GridLayoutManager(this, 3));
        
        CategoriaAdapter adapter = new CategoriaAdapter(this, MockData.getCategorias());
        rvCategorias.setAdapter(adapter);

        // FAB de Acesso ao Carrinho
        FloatingActionButton fabCarrinho = findViewById(R.id.fab_carrinho);
        fabCarrinho.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CarrinhoActivity.class);
            startActivity(intent);
        });

        MaterialButton btnGerenciar = findViewById(R.id.btn_gerenciar_produtos);
        btnGerenciar.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, new GerenciarProdutosActivity.class);

            startActivity(intent);

        });
    }
}

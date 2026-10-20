
package com.senai.cantinaagil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class GerenciarProdutosActivity extends AppCompatActivity {

    private RecyclerView rvProdutos;

    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerenciar_produtos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDataBase.getDataBase(this);

        rvProdutos = findViewById(R.id.rv_gerenciar_produtos);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnVoltar = findViewById(R.id.btn_voltar_gerenciar);
        btnVoltar.setOnClickListener(view -> finish());

        MaterialButton btnNovo = findViewById(R.id.btn_novo_produto);
        btnNovo.setOnClickListener(view -> {
            Intent intent = new Intent(GerenciarProdutosActivity.this, CadastroProdutoActivity.class);

            startActivity(intent);

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();
    }

    private void carregarProdutos(){
        List<Produto> produtos = db.produtoDao().listarTodos();

        ProdutoCrudAdapter adapter = new ProdutoCrudAdapter(produtos, new ProdutoCrudAdapter.OnProdutoActionListener() {
            @Override
            public void onEditar(Produto produto) {
                Intent intent = new Intent(
                        GerenciarProdutosActivity.this, CadastroProdutoActivity.class
                );
                intent.putExtra("produtoId", produto.getId());
                startActivity(intent);
            }

            @Override
            public void onExcluir(Produto produto) {
            db.produtoDao().excluir(produto);
            carregarProdutos();
            }
        }
        );
        rvProdutos.setAdapter(adapter);
    }
}
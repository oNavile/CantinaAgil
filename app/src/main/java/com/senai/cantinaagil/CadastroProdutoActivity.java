package com.senai.cantinaagil;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroProdutoActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private TextInputEditText edtNome, edtDescricao, edtPreco, edtQuantidade;
    private Spinner spinnerCategoria;
    private MaterialButton btnSalvar;

    private AppDataBase db;
    private Produto produtoEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitulo = findViewById(R.id.tv_titulo_formulario);
        edtNome = findViewById(R.id.edt_produto_nome);
        edtDescricao = findViewById(R.id.edt_produto_descricao);
        edtPreco = findViewById(R.id.edt_produto_preco);
        edtQuantidade = findViewById(R.id.edt_produto_quantidade);
        spinnerCategoria = findViewById(R.id.spinner_produto_categoria);
        btnSalvar = findViewById(R.id.btn_salvar_produto);

        ImageButton btnVoltar = findViewById(R.id.btn_voltar_formulario);

        btnVoltar.setOnClickListener(V -> finish());

        db = AppDataBase.getDatabase(this);

        int produtoId = getIntent().getIntExtra("produtoId", -1);

        if (produtoId != -1){

            produtoEditando = db.produtoDao().buscarPorId(produtoId);

            if (produtoEditando != null){

                carregarDadosParaEdicao();

            }

        }

        btnSalvar.setOnClickListener(v -> salvarProduto());

    }

    private void carregarDadosParaEdicao(){

        tvTitulo.setText("Editar Produto");

        edtNome.setText(produtoEditando.getNome());
        edtDescricao.setText(produtoEditando.getDescricao());
        edtPreco.setText(String.valueOf(produtoEditando.getPreco()));
        edtQuantidade.setText(String.valueOf(produtoEditando.getQuantidade()));
        selecionarCategoria(produtoEditando.getCategoria());

    }

    private void selecionarCategoria(String categoria){

        for (int i = 0; i < spinnerCategoria.getCount(); i++){
            String item = spinnerCategoria.getItemAtPosition(i).toString();

            if (item.equals(categoria)){
                spinnerCategoria.setSelection(i);
                break;
            }
        }

    }

    private void salvarProduto() {

        String nome = edtNome.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String precoTexto = edtPreco.getText().toString().trim();
        String quantidadeTexto = edtQuantidade.getText().toString().trim();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (nome.isEmpty()) {
            edtNome.setError("Informe o nome");
            return;
        }

        if (descricao.isEmpty()) {
            edtDescricao.setError("Informe a descrição");
        }

        if (precoTexto.isEmpty()) {
            edtPreco.setError("Informe o Preço");
            return;
        }

        if (quantidadeTexto.isEmpty()) {
            edtQuantidade.setError("Informe a Quantidade");
            return;
        }

        double preco;
        int quantidade;

        try {
            preco = Double.parseDouble(precoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            edtPreco.setError("Preço inválido");
            return;
        }

        try {
            quantidade = Integer.parseInt(quantidadeTexto);
        } catch (NumberFormatException e) {
            edtQuantidade.setError("Quantidade inválida");
            return;
        }

        if (preco <= 0) {
            edtPreco.setError("O preço deve ser maior que zero");
            return;
        }

        if (quantidade <= 0) {
            edtQuantidade.setError("A quantidade não pode ser negativa");
            return;
        }

        if (produtoEditando == null) {

            Produto novoProduto = new Produto(nome, descricao, preco, quantidade, categoria);

            db.produtoDao().inserir(novoProduto);

            Toast.makeText(this, "Produto Cadastrado com sucesso", Toast.LENGTH_LONG).show();

        } else {

            produtoEditando.setNome(nome);
            produtoEditando.setDescricao(descricao);
            produtoEditando.setPreco(preco);
            produtoEditando.setQuantidade(quantidade);
            produtoEditando.setCategoria(categoria);

            db.produtoDao().atualizar(produtoEditando);

            Toast.makeText(this, "Produto Atualizado com sucesso", Toast.LENGTH_SHORT).show();

        }

        finish();
    }

}
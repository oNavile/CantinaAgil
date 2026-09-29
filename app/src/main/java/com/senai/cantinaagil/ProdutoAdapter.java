package com.senai.cantinaagil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.Locale;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    private final List<MockData.Produto> produtos;
    private final Context context;

    public ProdutoAdapter(Context context, List<MockData.Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MockData.Produto prod = produtos.get(position);
        holder.tvTitulo.setText(prod.getNome());
        holder.tvDescricao.setText(prod.getDescricao());
        holder.tvPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", prod.getPreco()));
        holder.ivFoto.setImageResource(prod.getFotoRes());

        holder.btnAdicionar.setOnClickListener(v -> {
            MockData.adicionarAoCarrinho(prod);
            Toast.makeText(context, prod.getNome() + " adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTitulo;
        TextView tvDescricao;
        TextView tvPreco;
        FloatingActionButton btnAdicionar;

        ViewHolder(View view) {
            super(view);
            ivFoto = view.findViewById(R.id.iv_produto_foto);
            tvTitulo = view.findViewById(R.id.tv_produto_titulo);
            tvDescricao = view.findViewById(R.id.tv_produto_descricao);
            tvPreco = view.findViewById(R.id.tv_produto_preco);
            btnAdicionar = view.findViewById(R.id.btn_adicionar_produto);
        }
    }
}

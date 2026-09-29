package com.senai.cantinaagil;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private final List<MockData.Categoria> categorias;
    private final Context context;

    public CategoriaAdapter(Context context, List<MockData.Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MockData.Categoria cat = categorias.get(position);
        holder.tvNome.setText(cat.getNome());
        holder.ivIcone.setImageResource(cat.getIconeRes());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CardapioActivity.class);
            intent.putExtra("categoria_nome", cat.getNome());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcone;
        TextView tvNome;

        ViewHolder(View view) {
            super(view);
            ivIcone = view.findViewById(R.id.iv_categoria_icone);
            tvNome = view.findViewById(R.id.tv_categoria_nome);
        }
    }
}

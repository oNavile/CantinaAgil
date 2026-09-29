package com.senai.cantinaagil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    private final List<MockData.CarrinhoItem> itens;
    private final OnCartChangedListener listener;

    public CarrinhoAdapter(List<MockData.CarrinhoItem> itens, OnCartChangedListener listener) {
        this.itens = itens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MockData.CarrinhoItem item = itens.get(position);
        MockData.Produto prod = item.getProduto();

        holder.tvNome.setText(prod.getNome());
        holder.tvPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", prod.getPreco() * item.getQuantidade()));
        holder.tvQuantidade.setText(String.valueOf(item.getQuantidade()));

        // Regra de Negócio: Se o produto for personalizado, exibe opções de agendamento.
        // Se for pronto (como refrigerante, salgados prontos, etc.), oculta o agendamento.
        if (prod.isPersonalizado()) {
            holder.llRetiradaContainer.setVisibility(View.VISIBLE);
            if (item.isAgendado()) {
                holder.rbAgendar.setChecked(true);
            } else {
                holder.rbRetirarAgora.setChecked(true);
            }
        } else {
            holder.llRetiradaContainer.setVisibility(View.GONE);
        }

        holder.rgOpcaoRetirada.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_agendar_retirada) {
                item.setAgendado(true);
            } else {
                item.setAgendado(false);
            }
        });

        // Controles de quantidade
        holder.btnAumentar.setOnClickListener(v -> {
            item.setQuantidade(item.getQuantidade() + 1);
            notifyItemChanged(position);
            if (listener != null) {
                listener.onCartChanged();
            }
        });

        holder.btnDiminuir.setOnClickListener(v -> {
            if (item.getQuantidade() > 1) {
                item.setQuantidade(item.getQuantidade() - 1);
                notifyItemChanged(position);
            } else {
                itens.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
            if (listener != null) {
                listener.onCartChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        TextView tvPreco;
        TextView tvQuantidade;
        ImageButton btnDiminuir;
        ImageButton btnAumentar;
        LinearLayout llRetiradaContainer;
        RadioGroup rgOpcaoRetirada;
        RadioButton rbRetirarAgora;
        RadioButton rbAgendar;

        ViewHolder(View view) {
            super(view);
            tvNome = view.findViewById(R.id.tv_carrinho_item_nome);
            tvPreco = view.findViewById(R.id.tv_carrinho_item_preco);
            tvQuantidade = view.findViewById(R.id.tv_carrinho_quantidade);
            btnDiminuir = view.findViewById(R.id.btn_carrinho_diminuir);
            btnAumentar = view.findViewById(R.id.btn_carrinho_aumentar);
            llRetiradaContainer = view.findViewById(R.id.ll_retirada_container);
            rgOpcaoRetirada = view.findViewById(R.id.rg_opcao_retirada);
            rbRetirarAgora = view.findViewById(R.id.rb_retirar_agora);
            rbAgendar = view.findViewById(R.id.rb_agendar_retirada);
        }
    }
}

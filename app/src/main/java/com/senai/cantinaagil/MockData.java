package com.senai.cantinaagil;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static class Categoria {
        private String nome;
        private int iconeRes;

        public Categoria(String nome, int iconeRes) {
            this.nome = nome;
            this.iconeRes = iconeRes;
        }

        public String getNome() { return nome; }
        public int getIconeRes() { return iconeRes; }
    }

    public static class Produto {
        private String nome;
        private String descricao;
        private double preco;
        private int fotoRes;
        private boolean personalizado;

        public Produto(String nome, String descricao, double preco, int fotoRes, boolean personalizado) {
            this.nome = nome;
            this.descricao = descricao;
            this.preco = preco;
            this.fotoRes = fotoRes;
            this.personalizado = personalizado;
        }

        public String getNome() { return nome; }
        public String getDescricao() { return descricao; }
        public double getPreco() { return preco; }
        public int getFotoRes() { return fotoRes; }
        public boolean isPersonalizado() { return personalizado; }
    }

    public static class CarrinhoItem {
        private Produto produto;
        private int quantidade;
        private boolean agendado;

        public CarrinhoItem(Produto produto, int quantidade, boolean agendado) {
            this.produto = produto;
            this.quantidade = quantidade;
            this.agendado = agendado;
        }

        public Produto getProduto() { return produto; }
        public int getQuantidade() { return quantidade; }
        public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
        public boolean isAgendado() { return agendado; }
        public void setAgendado(boolean agendado) { this.agendado = agendado; }
    }

    private static List<Categoria> categorias;
    private static List<Produto> produtos;
    private static List<CarrinhoItem> carrinho = new ArrayList<>();

    public static List<Categoria> getCategorias() {
        if (categorias == null) {
            categorias = new ArrayList<>();
            categorias.add(new Categoria("Salgados Vitrine", R.drawable.ic_cat_salgados));
            categorias.add(new Categoria("Bebidas", R.drawable.ic_cat_bebidas));
            categorias.add(new Categoria("Doces & Sobremesas", R.drawable.ic_cat_doces));
            categorias.add(new Categoria("Lanches Naturais", R.drawable.ic_cat_lanches));
            categorias.add(new Categoria("Preparados na Hora", R.drawable.ic_cat_personalizados));
        }
        return categorias;
    }

    public static List<Produto> getProdutosPorCategoria(String categoria) {
        List<Produto> filtrados = new ArrayList<>();
        if (produtos == null) {
            produtos = new ArrayList<>();
            // Salgados Vitrine
            produtos.add(new Produto("Coxinha de Frango", "Coxinha frita recheada com frango desfiado e catupiry.", 7.50, R.drawable.ic_cat_salgados, false));
            produtos.add(new Produto("Pastel de Forno", "Pastel assado integral com recheio de palmito e ricota.", 8.00, R.drawable.ic_cat_salgados, false));
            produtos.add(new Produto("Empada de Alho Poró", "Empada cremosa de alho poró com massa podre.", 6.50, R.drawable.ic_cat_salgados, false));

            // Bebidas
            produtos.add(new Produto("Refrigerante Lata", "Lata de Coca-Cola ou Guaraná Antarctica 350ml bem gelado.", 6.00, R.drawable.ic_cat_bebidas, false));
            produtos.add(new Produto("Suco Natural Laranja", "Suco natural da fruta espremido na hora, copo de 400ml.", 9.00, R.drawable.ic_cat_bebidas, true));
            produtos.add(new Produto("Água Mineral", "Garrafa de água mineral 500ml sem gás.", 3.50, R.drawable.ic_cat_bebidas, false));

            // Doces
            produtos.add(new Produto("Brigadeiro Gourmet", "Brigadeiro tradicional feito com chocolate belga.", 4.50, R.drawable.ic_cat_doces, false));
            produtos.add(new Produto("Pudim de Leite", "Fatia de pudim de leite condensado com calda de caramelo.", 6.50, R.drawable.ic_cat_doces, false));

            // Lanches & Preparados na Hora
            produtos.add(new Produto("Misto Quente Especial", "Pão de forma, presunto, queijo muçarela derretido na chapa.", 12.90, R.drawable.ic_cat_personalizados, true));
            produtos.add(new Produto("Pão com Ovo", "Pão francês crocante com dois ovos na chapa e manteiga.", 8.50, R.drawable.ic_cat_personalizados, true));
            produtos.add(new Produto("Sanduíche Natural", "Pão de forma integral, frango cremoso, alface e cenoura.", 10.50, R.drawable.ic_cat_lanches, false));
        }

        for (Produto p : produtos) {
            if (categoria.equalsIgnoreCase("Salgados Vitrine") && (p.getNome().contains("Coxinha") || p.getNome().contains("Pastel") || p.getNome().contains("Empada"))) {
                filtrados.add(p);
            } else if (categoria.equalsIgnoreCase("Bebidas") && (p.getNome().contains("Refrigerante") || p.getNome().contains("Suco") || p.getNome().contains("Água"))) {
                filtrados.add(p);
            } else if (categoria.equalsIgnoreCase("Doces & Sobremesas") && (p.getNome().contains("Brigadeiro") || p.getNome().contains("Pudim"))) {
                filtrados.add(p);
            } else if (categoria.equalsIgnoreCase("Lanches Naturais") && p.getNome().contains("Sanduíche")) {
                filtrados.add(p);
            } else if (categoria.equalsIgnoreCase("Preparados na Hora") && p.isPersonalizado()) {
                filtrados.add(p);
            }
        }
        
        if (filtrados.isEmpty()) {
            for (Produto p : produtos) {
                if (p.isPersonalizado() && categoria.equalsIgnoreCase("Preparados na Hora")) {
                    filtrados.add(p);
                } else if (!p.isPersonalizado() && !categoria.equalsIgnoreCase("Preparados na Hora")) {
                    filtrados.add(p);
                }
            }
        }
        return filtrados;
    }

    public static List<CarrinhoItem> getCarrinho() {
        if (carrinho.isEmpty()) {
            carrinho.add(new CarrinhoItem(
                new Produto("Misto Quente Especial", "Pão de forma, presunto, queijo muçarela derretido na chapa.", 12.90, R.drawable.ic_cat_personalizados, true),
                1,
                false
            ));
            carrinho.add(new CarrinhoItem(
                new Produto("Suco Natural Laranja", "Suco natural da fruta espremido na hora, copo de 400ml.", 9.00, R.drawable.ic_cat_bebidas, true),
                1,
                true
            ));
            carrinho.add(new CarrinhoItem(
                new Produto("Refrigerante Lata", "Lata de Coca-Cola ou Guaraná Antarctica 350ml bem gelado.", 6.00, R.drawable.ic_cat_bebidas, false),
                2,
                false
            ));
        }
        return carrinho;
    }

    public static void adicionarAoCarrinho(Produto produto) {
        for (CarrinhoItem item : carrinho) {
            if (item.getProduto().getNome().equals(produto.getNome())) {
                item.setQuantidade(item.getQuantidade() + 1);
                return;
            }
        }
        carrinho.add(new CarrinhoItem(produto, 1, false));
    }

    public static double calcularTotal() {
        double total = 0;
        for (CarrinhoItem item : carrinho) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        return total;
    }

    public static void limparCarrinho() {
        carrinho.clear();
    }
}

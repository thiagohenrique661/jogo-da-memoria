package regras;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ControleBotoesSelecionados {

    private Map<String, String> frases = new HashMap<String, String>();

    private String nmBotao;
    private boolean found;
    private Map<JButton, EstadosBotoes> referenciaBotoes;

    public ControleBotoesSelecionados() {
        this.referenciaBotoes = new HashMap<>();
        this.found = false;
        frases.put("1", "Descarte de papéis em lixo azul!");
        frases.put("2", "Não jogue pilha no lixo, procure o centro para descarte correto!");
        frases.put("3", "Jamais reutilize uma seringa, descarte em um lixo correto!");
        frases.put("4", "Descarte tudo que for vidro no lixo verde!");
        frases.put("5", "Descarte produto de plástico sempre no lixo vermelho!");
        frases.put("6", "Descarte os metais sempre na lata amarela!");
        frases.put("7", "O Brasil é um dos países que menos reciclam!");
        frases.put("8", "Não jogue pilha no lixo, procure o centro para descarte correto");
    }

    public void executarAcaoBotoes(JButton botao, EstadosBotoes estado) {
        alterarSelecao(botao, estado);
        Image img = new ImageIcon(this.getClass().getResource("/imgs/" + this.getNmBotao() + ".jpg")).getImage().getScaledInstance(85, 75, Image.SCALE_DEFAULT);
        botao.setIcon(new ImageIcon(img));
        if (this.isTodasSelecionadas()) {
            JOptionPane.showMessageDialog(null, this.frases.get(this.nmBotao));
            alterarEstadoTodosBotoes(EstadosBotoes.PARES_ENCONTRADOS);
        } else {
            alterarVisualizacaoBotao(botao);
        }

    }

    private void alterarEstadoTodosBotoes(EstadosBotoes estado) {
        for (JButton botao : this.referenciaBotoes.keySet()) {
            alterarSelecao(botao, estado);
            alterarVisualizacaoBotao(botao);
        }
    }

    public String getNmBotao() {
        return nmBotao;
    }

    public void setNmBotao(String nmBotao) {
        this.nmBotao = nmBotao;
    }

    public Map<JButton, EstadosBotoes> getReferenciaBotoes() {
        return referenciaBotoes;
    }

    public void setReferenciaBotoes(Map<JButton, EstadosBotoes> referenciaBotoes) {
        this.referenciaBotoes = referenciaBotoes;
    }

    public void adicionarBotao(JButton botao) {
        this.referenciaBotoes.put(botao, EstadosBotoes.NORMAL);
    }

    public void alterarSelecao(JButton botao, EstadosBotoes selecionado) {
        this.referenciaBotoes.put(botao, selecionado);
    }

    private void alterarVisualizacaoBotao(JButton botao) {
        EstadosBotoes selecionado = this.referenciaBotoes.get(botao);
        switch (selecionado) {
            case NORMAL: // Cinza, não exibe texto
                Image img = new ImageIcon(this.getClass().getResource("/imgs/9.jpg")).getImage().getScaledInstance(85, 75, Image.SCALE_DEFAULT);
                botao.setIcon(new ImageIcon(img));
                this.found = false;
                break;
            case SELECIONADO: // Exibir texto, mudar a cor
                botao.setText(this.nmBotao);
                break;
            case PARES_ENCONTRADOS: // Mudar a cor, exibir o texto
                this.found = true;
                break;
        }
    }

    public void zerarSelecoes() {
        alterarEstadoTodosBotoes(EstadosBotoes.NORMAL);
    }

    public Boolean isTodasSelecionadas() {
        for (EstadosBotoes b : this.referenciaBotoes.values()) {
            if (b != EstadosBotoes.SELECIONADO) {
                // Não foram todos selecionados
                return false;
            }
        }
        return true;
    }

    public boolean isFound() {
        return found;
    }

}

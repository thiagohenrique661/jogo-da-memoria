package telas;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import regras.ControleBotoesSelecionados;
import regras.EstadosBotoes;
import javax.swing.JLabel;

public class TelaPrincipal extends JFrame {

    private static final int QUANTIDADE_JOGADAS = 2;
    private int jogadas = 0;

    private JPanel painel;

    private List<ControleBotoesSelecionados> listaControle;

    private List<ControleBotoesSelecionados> listaSelecionados;

    private ActionListener acaoBotoes;

    public TelaPrincipal() {
        super("Jogo da Memoria");

        listaControle = new ArrayList<>();
        listaSelecionados = new ArrayList<>();

        // Evento dos botões
        acaoBotoes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton botao = (JButton) e.getSource();
                for (ControleBotoesSelecionados btn : listaControle) {
                    if (btn.getReferenciaBotoes().get(botao) != null && !btn.isFound()) {
                        jogadas++;
                        btn.executarAcaoBotoes((JButton) e.getSource(), EstadosBotoes.SELECIONADO);
                        // Controle de inclusão
                        if (!listaSelecionados.contains(btn)) {
                            listaSelecionados.add(btn);
                        }
                        if (jogadas == QUANTIDADE_JOGADAS) {
                            if (listaSelecionados.size() > 1) {
                                // Deixar os botoes com estado inicial
                                for (ControleBotoesSelecionados cbs : listaSelecionados) {
                                    int delay = 500;
                                    int interval = 1000000000;
                                    Timer timer = new Timer();

                                    timer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            // colocar tarefas aqui ...
                                            cbs.zerarSelecoes();
                                        }
                                    }, delay, interval);
                                }
                            }
                            jogadas = 0;
                            listaSelecionados.clear();
                        }
                        break;
                    }

                }
            }
        };

        painel = new JPanel();
        this.add(painel);
        painel.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        criarJogo(8);
        this.setBounds(500, 200, 350, 370);

        this.setVisible(true);
    }

    private void criarJogo(int qtPares) {
        // Quantidade de botões
        ControleBotoesSelecionados controle = null;

        List<Rectangle> posicionamentos = new ArrayList<>();
        int posX = 10;
        int posY = 10;

        Random rand = new Random();
        int j = 0;
        int i = 0;

        for (i = 0; i < (qtPares * 2); i++) {
            // Randomizar o posicionamento dos botões
            Rectangle rec = new Rectangle(posX, posY, 75, 75);
            posicionamentos.add(rec);
            if ((i + 1) % 4 == 0 && i > 0) {
                posY += 80;
                posX = 10;
            } else {
                posX += 80;
            }
        }

        for (i = 0; i < (qtPares * 2); i++) {
            if (i % 2 == 0) {
                // Quantidade de controladores
                j++;
                controle = new ControleBotoesSelecionados();
                controle.setNmBotao(String.valueOf(j));
                this.listaControle.add(controle);
            }
            JButton botao = new JButton("Jogo");
            Image img = new ImageIcon(this.getClass().getResource("/imgs/9.jpg")).getImage().getScaledInstance(85, 75, Image.SCALE_DEFAULT);
            botao.setIcon(new ImageIcon(img));
            // Colocar os botões na tela
            this.painel.add(botao);
            botao.addActionListener(this.acaoBotoes);
            // Adicionar posição
            int pos = rand.nextInt(((posicionamentos.size() - 1) > 0) ? posicionamentos.size() - 1 : 1);
            botao.setBounds(posicionamentos.get(pos));
            posicionamentos.remove(pos);

            controle.adicionarBotao(botao);
        }
        // Adaptar o tamanho da tela

    }

}

package br.com.paripassu.avaliacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FrameExecucao extends JFrame {

	private static final long serialVersionUID = 5023859484614782786L;
	
	private static final int PANEL_WIDTH = 400;
	private static final int PANEL_HEIGHT = 180;
	
	private JTextField tfPathFirefox;

	private Executar executar;
	
	public FrameExecucao() {
		this.setTitle("Avaliação");
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.addWindowListener(createCloseListener());
		
		criarFieldPathFirefox();
		criarBotaoIniciar();
		
		this.setVisible(true);
	}

	private void criarBotaoIniciar() {
		JButton bt = new JButton("Iniciar");
		bt.setBounds(140, 80, 80, 30);
		bt.addActionListener(criarAcionListener());
		this.add(bt);
	}
	
	private ActionListener criarAcionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) { 
                iniciar();
            }
        };
    }
	
	private void iniciar() {
		if (executar == null) {
			executar = new Executar(tfPathFirefox.getText());
		}
		executar.iniciar();
	}

	private void criarFieldPathFirefox() {
		JLabel label = new JLabel("Path firefox.exe (Versão 47.0.2)");
		label.setBounds(10, 10, 200, 15);
		this.add(label);
		
		tfPathFirefox = new JTextField();
		tfPathFirefox.setBounds(10, 25, 365, 25);
		tfPathFirefox.setText("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		this.add(tfPathFirefox);
	}

	private WindowListener createCloseListener() {
		return new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finalizar();
			}
		};
	}

	private void finalizar() {
		if (executar != null) {
			executar.fecharDriver();
		}
		
		this.dispose();
		System.exit(0);
	}
}
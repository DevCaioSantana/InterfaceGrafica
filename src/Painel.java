import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Painel {
    public JPanel painelPrincipal;
    public JTextField nome;
    public JRadioButton btnMasculino;
    public JRadioButton btnFeminino;
    public JTextField profissao;
    public JLabel labelNome;
    public JLabel labelCpf;
    public JLabel labelSexo;
    public JLabel labelProfissao;
    public JFormattedTextField dataNascimento;
    public JFormattedTextField cpf;
    public JComboBox<String> estadoCivil;
    public JLabel labelDataNascimento;
    public JLabel labelEstadoCivil;
    public JButton btnEnviar;

    public Painel() {
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(8, 2));

        labelNome = new JLabel("Nome:");
        nome = new JTextField();

        labelCpf = new JLabel("CPF:");
        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            cpf = new JFormattedTextField(cpfMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        labelSexo = new JLabel("Sexo:");
        btnMasculino = new JRadioButton("Masculino");
        btnFeminino = new JRadioButton("Feminino");
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(btnMasculino);
        grupoSexo.add(btnFeminino);

        labelDataNascimento = new JLabel("Data de Nascimento:");
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            dataNascimento = new JFormattedTextField(dataMask);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        labelEstadoCivil = new JLabel("Estado Civil:");
        estadoCivil = new JComboBox<>(new String[]{"Solteiro(a)", "Casado(a)", "Divorciado(a)", "Viúvo(a)"});

        labelProfissao = new JLabel("Profissão:");
        profissao = new JTextField();

        btnEnviar = new JButton("Enviar");

        painelPrincipal.add(labelNome);
        painelPrincipal.add(nome);
        painelPrincipal.add(labelCpf);
        painelPrincipal.add(cpf);
        painelPrincipal.add(labelSexo);
        painelPrincipal.add(btnMasculino);
        painelPrincipal.add(new JLabel());
        painelPrincipal.add(btnFeminino);
        painelPrincipal.add(labelDataNascimento);
        painelPrincipal.add(dataNascimento);
        painelPrincipal.add(labelEstadoCivil);
        painelPrincipal.add(estadoCivil);
        painelPrincipal.add(labelProfissao);
        painelPrincipal.add(profissao);
        painelPrincipal.add(new JLabel());
        painelPrincipal.add(btnEnviar);

        btnEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCompleto = nome.getText();
                String cpfTexto = cpf.getText();
                String sexo = btnMasculino.isSelected() ? "Masculino" : "Feminino";
                String estadoCivilTexto = (String) estadoCivil.getSelectedItem();
                String profissaoTexto = profissao.getText();
                String dataNascimentoTexto = dataNascimento.getText();

                if (!cpfTexto.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                    JOptionPane.showMessageDialog(painelPrincipal, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nomeCompleto.isEmpty()) {
                    JOptionPane.showMessageDialog(painelPrincipal, "O nome não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (profissaoTexto.isEmpty()) {
                    profissaoTexto = "Desempregado(a)";
                    profissao.setText(profissaoTexto);
                }

                LocalDate dataNascimentoLocal = LocalDate.parse(dataNascimentoTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int idade = Period.between(dataNascimentoLocal, LocalDate.now()).getYears();

                String mensagem = String.format("Nome: %s\nIdade: %d\nSexo: %s\nEstado Civil: %s\nProfissão: %s",
                        nomeCompleto, idade, sexo, estadoCivilTexto, profissaoTexto);

                if (profissaoTexto.equals("Engenheiro") || profissaoTexto.equals("Analista de Sistemas")) {
                    mensagem += "\nVagas disponíveis na área.";
                }

                JOptionPane.showMessageDialog(painelPrincipal, mensagem, "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}

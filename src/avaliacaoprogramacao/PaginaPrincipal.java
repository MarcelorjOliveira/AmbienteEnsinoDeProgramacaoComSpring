/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PaginaPrincipal.java
 *
 * Created on 24/09/2011, 15:07:25
 */

package avaliacaoprogramacao;

/**
 *
 * @author marcelo
 */
public class PaginaPrincipal extends javax.swing.JFrame {

    Exercicio exercicio;
    EscolhedorDeExercicio exercicios;

    /** Creates new form PaginaPrincipal */
    public PaginaPrincipal() {
        initComponents();
        exercicios = new EscolhedorDeExercicio();
        exercicio = exercicios.escolherExercicio();
        enunciado.setText(exercicio.enunciado());
        botaoProximoExercicio.setVisible(false);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        codigoFonte = new javax.swing.JTextArea();
        botaoSalvar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        enunciado = new javax.swing.JTextPane();
        botaoProximoExercicio = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        arquivoMenu = new javax.swing.JMenu();
        sairItemMenu = new javax.swing.JMenuItem();
        sobreMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Avaliação Programação");

        codigoFonte.setColumns(20);
        codigoFonte.setRows(5);
        jScrollPane1.setViewportView(codigoFonte);

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });

        enunciado.setEditable(false);
        jScrollPane2.setViewportView(enunciado);

        botaoProximoExercicio.setText("Próximo Exercício");
        botaoProximoExercicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoProximoExercicioActionPerformed(evt);
            }
        });

        arquivoMenu.setText("Arquivo");

        sairItemMenu.setText("Sair");
        sairItemMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairItemMenuActionPerformed(evt);
            }
        });
        arquivoMenu.add(sairItemMenu);

        jMenuBar1.add(arquivoMenu);

        sobreMenu.setText("Sobre");
        jMenuBar1.add(sobreMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(botaoProximoExercicio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                                .addComponent(botaoSalvar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoSalvar)
                    .addComponent(botaoProximoExercicio))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sairItemMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairItemMenuActionPerformed
        System.exit(0);
    }//GEN-LAST:event_sairItemMenuActionPerformed

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        exercicio.montarAvaliacao(codigoFonte.getText());
        if(exercicio.temErroDeCompilacao != true)
        {
            if(exercicios.fazerProximoExercicio() == true)
            {
                botaoProximoExercicio.setVisible(true);
                botaoSalvar.setVisible(false);
            }
            else
            {
                javax.swing.JOptionPane.showMessageDialog(null, "Parabéns. Você passou no teste!");
            }
        }
        
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoProximoExercicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoProximoExercicioActionPerformed
        exercicio = exercicios.escolherExercicio();
        enunciado.setText(exercicio.enunciado());
        botaoProximoExercicio.setVisible(false);
        botaoSalvar.setVisible(true);
        codigoFonte.setText("");
    }//GEN-LAST:event_botaoProximoExercicioActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaginaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu arquivoMenu;
    private javax.swing.JButton botaoProximoExercicio;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextArea codigoFonte;
    private javax.swing.JTextPane enunciado;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem sairItemMenu;
    private javax.swing.JMenu sobreMenu;
    // End of variables declaration//GEN-END:variables

}

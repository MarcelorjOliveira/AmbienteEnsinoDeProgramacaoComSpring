/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaliacaoprogramacao;

import java.util.ArrayList;

/**
 *
 * @author marcelo
 */
public class EscolhedorDeExercicio {
    ArrayList<Exercicio> exerciciosAFazer = new ArrayList<Exercicio>();
    ArrayList<Exercicio> exerciciosFeitos = new ArrayList<Exercicio>();
    boolean fazerProximoExercicio;
    
    public EscolhedorDeExercicio()
    {
        adicionarExercicios();
    }
    
    public void adicionarExercicios() {
        exerciciosAFazer.add(new ExercicioDivisor());
        exerciciosAFazer.add(new ExercicioLanchonete());
        exerciciosAFazer.add(new ExercicioFibonnaci());
        exerciciosAFazer.add(new ExercicioFatorial());
        //exerciciosAFazer.add(new ExercicioSoma());
        //exerciciosAFazer.add(new ExercicioSomaMultiplos());
    }
    
    public Exercicio escolherExercicio()
    {
        /*double random = Math.random() * exerciciosAFazer.size();
        
        Exercicio exercicio = exerciciosAFazer.get((int) random);
        
        exerciciosAFazer.remove((int)random);*/
        Exercicio exercicio = exerciciosAFazer.get(exerciciosAFazer.size() - 1);
        exerciciosAFazer.remove(exercicio);
        exerciciosFeitos.add(exercicio);
        
        return exercicio;
    } 
    
    private double media() {
        double notaTotal=0,media;
        for(Exercicio exercicio : exerciciosFeitos)
        {
            notaTotal += exercicio.notaExercicio();
        }
        
        media = notaTotal/exerciciosFeitos.size();
        javax.swing.JOptionPane.showMessageDialog(null, "Média : " + media);
        return media;
    }
    
    public boolean fazerProximoExercicio() {
        if(exerciciosFeitos.size() < 3 || media() < 8.0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

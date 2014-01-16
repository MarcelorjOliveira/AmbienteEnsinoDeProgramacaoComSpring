package model;

import static org.junit.Assert.*;

import org.junit.Test;

import avaliacaoprogramacao.Exercicio;
import avaliacaoprogramacao.ExercicioSoma;

public class TesteExercicios {
	
	@Test
	public void testaExercicioSomaCerto() {
		
		String codigo = "int soma(int x, int y) {\n"
				+ "return x + y;\n"
				+ "}\n";
		Exercicio soma = new ExercicioSoma();
		soma.montarAvaliacao(codigo);
		assertEquals(10.0,soma.notaExercicio(), 0.00001);
		
		
		
	}
	
	@Test
	public void testaExercicioSomaErrado() {
		String codigo = "int soma(int x, int y) {\n"
				+ "return x - y;\n"
				+ "}\n";
		Exercicio soma = new ExercicioSoma();
		soma.montarAvaliacao(codigo);
		assertEquals(0,soma.notaExercicio(), 0.00001);
	}

}

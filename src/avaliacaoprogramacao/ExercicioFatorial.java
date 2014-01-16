/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avaliacaoprogramacao;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;

/**
 *
 * @author marcelo
 */
public class ExercicioFatorial extends Exercicio {

    public ExercicioFatorial() {
        super("fatorial", "Escreva uma função com o nome fatorial que tenha um "
                + "argumento que retorne o fatorial desse número.");
    }
    
    @Override
    public void montarAvaliacao(String codigo)
    {
        this.codigo = codigo;
        String testes = "CU_ASSERT(1 == fatorial(0)); \n"
                + "CU_ASSERT(1 == fatorial(1)); \n "
                + "CU_ASSERT(120 == fatorial(5)); \n "
                + "CU_ASSERT(40320 == fatorial(8)); \n"
                + "CU_ASSERT(39916800 == fatorial(11));";
        montarCodigoTestes(codigo,testes);
        /*
         * Fatorial Iterativo
         * 
         int fatorial(int n) 
         {
              int fat, i;
              fat = 1;
              for (i = 1; i <= n; i = i + 1) 
              {
                  fat = fat * i;
              }
              return fat;
         }
         * 
         * Fatorial Recursivo
         * int fatorial(int numero)
                {
                if ((numero==1) || (numero==0))
                {
                return 1;
                }
                else 
                {
                return fatorial(numero-1)*numero;
                }
                }
         */
        this.respostaModelo = "int fatorial(int numero)\n "
                + "{\n"
                + "if ((numero==1) || (numero==0))\n "
                + "{\n"
                + "return 1;\n"
                + "}\n"
                + "else \n"
                + "{\n"
                + "return fatorial(numero-1)*numero;\n"
                + "}\n"
                + "}\n";
        gravarArquivos();
        avaliarCodigoFonte();
    }

    @Override
    protected double fazerAvaliacao(Document doc, XPath xpath) throws XPathExpressionException {
        
       XPathExpression expressao = xpath.compile("/CUNIT_TEST_RUN_REPORT/"
                        + "CUNIT_RUN_SUMMARY/CUNIT_RUN_SUMMARY_RECORD[3]/FAILED");

       double resultadoXMLExpressao = (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
       
       javax.swing.JOptionPane.showMessageDialog(null, resultadoXMLExpressao);
       
       double notaTestesAuxiliar = 10 - 2 * resultadoXMLExpressao;

       return notaTestesAuxiliar;
       
    }
    
}

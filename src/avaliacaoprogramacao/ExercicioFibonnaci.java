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
public class ExercicioFibonnaci extends Exercicio {

    public ExercicioFibonnaci() {
        super("fibonnaci","Escreva uma função com o nome fibonnaci com um "
                + "parâmetro que retorne o valor do termo na posição do parâmetro.");
   }
   
   @Override 
   public void montarAvaliacao(String codigo) 
   {
        this.codigo = codigo;
        String testes = "CU_ASSERT(1 == fibonnaci(1)); \n"
                + "CU_ASSERT(1 == fibonnaci(2));"
                + "CU_ASSERT(5 == fibonnaci(5)); \n"
                + "CU_ASSERT(13 == fibonnaci(7)); \n "
                + "CU_ASSERT(34 == fibonnaci(9)); \n "
                ;
        montarCodigoTestes(codigo,testes);
        /*
         * int fibonnaci(int termo) 
                { 
                int resultado,anterior,i; 
                if((termo == 1) || (termo == 2)) 
                { 
                return 1; 
                } 
                else 
                { 
                resultado = 1; 
                anterior = 1; 
                for(i = 3 ; i <= termo; i++) 
                { 
                resultado = resultado + anterior; 
                anterior = resultado - anterior;  
                } 
                return resultado; 
                } 
                }
         */
        this.respostaModelo = "int fibonnaci(int termo) \n "
                + "{ \n"
                + "int resultado,anterior,i; \n"
                + "if((termo == 1) || (termo == 2)) \n"
                + "{ \n"
                + "return 1; \n"
                + "} \n"
                + "else \n"
                + "{ \n"
                + "resultado = 1; \n"
                + "anterior = 1; \n"
                + "for(i = 3 ; i <= termo; i++) \n"
                + "{ \n"
                + "resultado = resultado + anterior; \n"
                + "anterior = resultado - anterior;  \n"
                + "} \n"
                + "return resultado; \n"
                + "} \n"
                + "}";
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

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
public class ExercicioDivisor extends Exercicio {

    public ExercicioDivisor() {
        super("divisor","Escreva uma função com o nome divisor com dois "
                + "argumentos que retorne 0 se um número é divisor do outro e"
                + " 1 senão.");
    }
    
    @Override
    public void montarAvaliacao(String codigo) 
    {
        this.codigo = codigo;
        String testes = "CU_ASSERT(0 == divisor(1,1)); \n"
                + "CU_ASSERT(1 == divisor(100,1));"
                + "CU_ASSERT(0 == divisor(4,8)); \n"
                + "CU_ASSERT(1 == divisor(2,3)); \n "
                + "CU_ASSERT(0 == divisor(20,80)); \n "
                ;
        montarCodigoTestes(codigo,testes);
        /*
         * int divisor(int divisor, int dividendo) 
              {
        if(dividendo % divisor == 0)
        {
        return 0;
        }
        else
        {
         return 1 ;
        }
        }
         */
        this.respostaModelo = "int divisor(int divisor, int dividendo) \n"
              + "{\n"
        + "if(dividendo % divisor == 0)\n "
        + "{\n"
        + "   return 0;\n"
        + "}\n"
        + "else\n"
        + "{\n"
        + "   return 1 ;\n"
        + "}\n"
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

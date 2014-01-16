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
public class ExercicioSoma extends Exercicio {
    
    public ExercicioSoma()
    {
        super("soma","Escreva uma função com o nome soma que tenha dois "
                + "argumentos inteiros e retorne um número inteiro também." );
        
    }
    
    @Override
    public void montarAvaliacao(String codigo)
    {
        this.codigo = codigo;
        String testes = "CU_ASSERT(5 == soma(2,3)); \n"
                        + "CU_ASSERT(7 == soma(4,3)); \n"
                        + "CU_ASSERT(87 == soma(43,44));\n"
;
        montarCodigoTestes(codigo,testes);
        /*
         * int soma(int x, int y)
                {
                int z;
                z = x + y; 
                return z; 
                }
         */
            this.respostaModelo = "int soma(int x, int y)\n"
                + "{\n"
                + "int z; \n"
                + "z = x + y; \n"
                + "return z; \n"
                + "}\n";
        gravarArquivos();
        avaliarCodigoFonte();
    }
    
    @Override
    protected double fazerAvaliacao(Document doc, XPath xpath) throws XPathExpressionException
    {
        XPathExpression expressao = xpath.compile("/CUNIT_TEST_RUN_REPORT/"
                        + "CUNIT_RUN_SUMMARY/CUNIT_RUN_SUMMARY_RECORD[2]/FAILED");

        double resultadoXMLExpressao = (Double)expressao.evaluate(doc,XPathConstants.NUMBER);

        if(resultadoXMLExpressao == 0)
        {
            return 10;
        }
        else
        {
            return 0;
        }
    }
    
}

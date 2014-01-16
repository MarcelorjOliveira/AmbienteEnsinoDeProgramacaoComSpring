 package avaliacaoprogramacao;


/*
 * @author marcelo
 */

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public abstract class Exercicio
{

 //Criar um construtor
    int DEFAULT_BUFFER_SIZE = 255;
    protected String nome,enunciado;
    protected String codigoTestes;
    protected String codigo;
    protected String respostaModelo;
    protected String nomedoarquivoTestes;
    protected File arquivoTestes;
    protected String nomedoarquivoMetricas;
    protected File arquivoParaMetricas;

    protected File arquivorespostaModelo = new File("respostaModelo.c");

    protected boolean temErroDeCompilacao = false;
    protected int quantidadeTentativas = 1;

    protected double notaMetricasAluno,notaMetricasModelo;

    protected double notaTestes;
    protected double notaMetricas;
    protected double notaExercicio;

    public Exercicio(String nome,String enunciado)
    {
        //TODO o Exercício tem que ter um campo String para enunciado
        this.nome = nome;
        this.enunciado = enunciado;
        nomedoarquivoTestes = "testaMetodo" + nome;
        nomedoarquivoMetricas = "metricaMetodo" + nome;
        String programaTestes = nomedoarquivoTestes + ".c";
        arquivoTestes = new File(programaTestes);
        String programaMetricas = nomedoarquivoMetricas + ".c";
        arquivoParaMetricas = new File(programaMetricas);
    }
    
    public String enunciado() 
    {
        return enunciado;
    }
    
    public void gravarArquivos() 
    {
        gravarArquivo(codigoTestes,arquivoTestes);
        gravarArquivo(codigo,arquivoParaMetricas);
        gravarArquivo(respostaModelo,arquivorespostaModelo);
    }
    
    public abstract void montarAvaliacao(String codigo);
    
    public void montarCodigoTestes(String codigo, String testes)
    {
        this.codigoTestes =
            "#include <stdio.h> \n"
                + "#include <stdlib.h> \n"
                + "#include \"CUnit/Basic.h\" \n"
                + codigo + 
                "int init_suite(void) { \n"
                + "return 0; \n"
                + "} \n"
                + "int clean_suite(void) { \n"
                + "return 0; \n"
                + "} \n"
                + "void testa" + nome + "() { \n"
                + testes
                + "} \n"
                + "int main() { \n"
                + "CU_pSuite pSuite = NULL; \n"
                + "    if (CUE_SUCCESS != CU_initialize_registry()) \n"
                + "return CU_get_error(); \n"
                + "    pSuite = CU_add_suite(\"newcunittest\", init_suite, clean_suite); \n"
                + "if (NULL == pSuite) { \n"
                + "CU_cleanup_registry(); \n"
                + "return CU_get_error(); \n"
                + "} \n"
                + "if (NULL == CU_add_test(pSuite, \"testa"+nome+"\", testa"+nome+")) { \n"
                + "CU_cleanup_registry(); \n"
                + "return CU_get_error(); \n"
                + "} \n"
                + "CU_basic_set_mode(CU_BRM_VERBOSE); \n"
                + "CU_automated_run_tests(); \n"
                + "CU_cleanup_registry(); \n"
                + "return CU_get_error(); \n"
                + "} \n"
                ;
    }

    public void gravarArquivo(String codigo, File arquivo)
    {
        try {
        // Gravando no arquivo
        FileOutputStream fos = new FileOutputStream(arquivo);
        fos.write(codigo.getBytes());
        fos.close();
    }
    catch (Exception ee)
    {
       ee.printStackTrace();
    }
    }
    
    //O Número de linhas do programa
   public void avaliarCodigoFonte()
    {
        try
        {
            //Compilação do código-fonte
            String nomeComando = "gcc " + nomedoarquivoTestes + ".c" + " -lcunit -o " + nomedoarquivoTestes;
            Process processo = Runtime.getRuntime().exec(nomeComando);
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
            String resultado = "";
            String linha = null;
            while ((linha = entrada.readLine()) != null) {
               resultado += linha;
            }
            entrada.close();
            
            javax.swing.JOptionPane.showMessageDialog(null, resultado);

            if(!resultado.equals(""))
            {
                if(quantidadeTentativas <3)
                {
                    notaExercicio -= 0.1;
                    javax.swing.JOptionPane.showMessageDialog(null, "Tem erro de compilação");
                    //javax.swing.JOptionPane.showMessageDialog(null, resultado);
                    temErroDeCompilacao = true;
                    quantidadeTentativas += 1;
                }
                else
                {
                // TODO Colocar outro exercício
                   javax.swing.JOptionPane.showMessageDialog(null, "Já era");
                }
            }
            else
            {
                validacaoDoPrograma();
                calculoDasMetricasEComparacaoComAsDaRespostaModelo();
                calculonotaExercicio();
                temErroDeCompilacao = false;
              
            }   
            
            arquivoTestes.delete();
            arquivoParaMetricas.delete();
            arquivorespostaModelo.delete();
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
   protected abstract double fazerAvaliacao(Document doc, XPath xpath) throws XPathExpressionException;
   
   private void validacaoDoPrograma()
   {
       File resultadoTestes = null;
        try {
            String nomeComando = "./" + nomedoarquivoTestes;
            Process processo = Runtime.getRuntime().exec(nomeComando);
        } catch (IOException ex) {
            Logger.getLogger(Exercicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          resultadoTestes = new File("CUnitAutomated-Results.xml");

            //Porque dava problema de Exceção
            EditorArquivo.removeLinhaDoArquivo("CUnitAutomated-Results.xml",
                        "<!DOCTYPE CUNIT_TEST_RUN_REPORT SYSTEM \"CUnit-Run.dtd\">");

           DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
           fabrica.setNamespaceAware(true);
           DocumentBuilder construtorDocumento;
           try {
                construtorDocumento = fabrica.newDocumentBuilder();
                Document doc = construtorDocumento.parse(resultadoTestes);
                XPath xpath = XPathFactory.newInstance().newXPath();
                
                //TODO se notaTestes = 0 , tem que zerar tudo
                
                notaTestes = fazerAvaliacao(doc, xpath);
                    String nomeArquivo = "./" + nomedoarquivoTestes;
                    File executavel = new File(nomeArquivo);
                    executavel.delete();
                    javax.swing.JOptionPane.showMessageDialog(null, "Nota Avaliação : " + notaTestes);
                    resultadoTestes.delete();
                
           }
           catch (Exception ex) {
               validacaoDoPrograma();
            }
   }

   private void calculoDasMetricasEComparacaoComAsDaRespostaModelo()
   {
      calculoDasMetricas(nomedoarquivoMetricas);
      //TODO Calcular apenas uma vez e colocar a notaMétricasModelo pré-definida
      calculoDasMetricas("respostaModelo");

     double regraDeTres = (10*notaMetricasAluno)/notaMetricasModelo;
     
     javax.swing.JOptionPane.showMessageDialog(null, "Nota Metricas Comparação : " + regraDeTres);
     
     if(regraDeTres > 10)
     {
         notaMetricas = 10;
     }
     else
     {
        notaMetricas = regraDeTres;
     }
     
     
   }

   private void calculoDasMetricas(String nomedoarquivo)
   {
       double numeroDeLinhasDoPrograma,complexidadeCiclomatica,quantidadePalavrasReservadas;
       File resultadoAvaliacao = null;
        try {
            //TODO Salvar arquivo só com o código-fonte
            String nomeComando = "./rsm.lnx -X  -Tf -Tv -Oreporta.xml " + nomedoarquivo + ".c";
            Process processo = Runtime.getRuntime().exec(nomeComando);
        } catch (IOException ex) {
            Logger.getLogger(Exercicio.class.getName()).log(Level.SEVERE, null, ex);
        }

          resultadoAvaliacao = new File("reporta.xml");

           DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
           fabrica.setNamespaceAware(true);
           DocumentBuilder construtorDocumento;
           try {
                construtorDocumento = fabrica.newDocumentBuilder();
                Document doc = construtorDocumento.parse(resultadoAvaliacao);
                XPath xpath = XPathFactory.newInstance().newXPath();

                XPathExpression expressao = xpath.compile("/m2rsm/"
                        + "report/function[1]/loc");

                numeroDeLinhasDoPrograma = (Double)expressao.evaluate(doc,XPathConstants.NUMBER);

                javax.swing.JOptionPane.showMessageDialog(null, "Número de Linhas do Programa : " + numeroDeLinhasDoPrograma);
                
                expressao = xpath.compile("/m2rsm/"
                        + "report/function[1]/cyclomatic_complexity");

                complexidadeCiclomatica = (Double)expressao.evaluate(doc,XPathConstants.NUMBER);

                javax.swing.JOptionPane.showMessageDialog(null, "Complexidade Ciclomática : " + complexidadeCiclomatica);
                
                expressao = xpath.compile("/m2rsm/report[2]/break_count");
                quantidadePalavrasReservadas = (Double)expressao.evaluate(doc,XPathConstants.NUMBER);            
                expressao = xpath.compile("/m2rsm/report[2]/else_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/switch_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/case_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/enum_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/typedef_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/return_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/union_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/const_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/for_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/default_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/goto_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/do_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/if_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);
                expressao = xpath.compile("/m2rsm/report[2]/while_count");
                quantidadePalavrasReservadas += (Double)expressao.evaluate(doc,XPathConstants.NUMBER);

                javax.swing.JOptionPane.showMessageDialog(null, "Quantidade de Palavras Reservadas : " + quantidadePalavrasReservadas);
                
                resultadoAvaliacao.delete();

                if(nomedoarquivo.equals("respostaModelo"))
                {
                    notaMetricasModelo = regressaoLinear(numeroDeLinhasDoPrograma,complexidadeCiclomatica,quantidadePalavrasReservadas);
                    javax.swing.JOptionPane.showMessageDialog(null, "Nota Metricas Modelo : " + notaMetricasModelo);
                }
                else
                {
                    notaMetricasAluno = regressaoLinear(numeroDeLinhasDoPrograma,complexidadeCiclomatica,quantidadePalavrasReservadas);
                    javax.swing.JOptionPane.showMessageDialog(null, "Nota Metricas Aluno : " + notaMetricasAluno);
                }
          }
          catch (Exception ex) {
               calculoDasMetricas(nomedoarquivo);
           }
  }

   private double regressaoLinear(double numeroDeLinhasDoPrograma,double complexidadeCiclomatica, double quantidadePalavrasReservadas)
   {
       return 10.08153 + (-0.046505*numeroDeLinhasDoPrograma) + (-0.021961*quantidadePalavrasReservadas) + (0.063951*complexidadeCiclomatica);

   }

   private void calculonotaExercicio() {
	   if(notaTestes == 0){
		   notaExercicio = 0;
	   }else {
		   notaExercicio += notaTestes*80/100 + notaMetricas*20/100;
	   }
       javax.swing.JOptionPane.showMessageDialog(null,"Nota Exercício : " +notaExercicio);
       javax.swing.JOptionPane.showMessageDialog(null, "Exercicio enviado com sucesso. Vamos fazer o próximo exercicio");
   }
   
   public double notaExercicio() {
       return notaExercicio;
   }
}

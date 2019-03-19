/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp_server1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 * @author zivia
 */
public class TCP_Server1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //Define o local como EUA, deixando o ponto como padrão
        Locale.setDefault(Locale.US);
        //Faz a formatação de números depois da vírgula
        DecimalFormat df = new DecimalFormat("###,##0.00");
        
        ServerSocket serverSocket = new ServerSocket(9001);
	 // waits for a new connection. Accepts connetion from multiple clients
	 while (true) {
	    System.out.println("Esperando conexão na porta 9001");
            Socket s = serverSocket.accept();
	    System.out.println("Conexão estabelecida de " + s.getInetAddress());
			 
	    // create a BufferedReader object to read strings from
	    // the socket. (read strings FROM CLIENT)
	    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String input = br.readLine();
            
            //create output stream to write to/send TO CLINET
            DataOutputStream output = new DataOutputStream(s.getOutputStream());
            
            //Cria um vetor contendo as palavras e/ou letras contidas na string input
            String[] vect = input.split(" ");
            
            //Tenta executar o código, e caso der erro, tratar
            try{
                if (vect.length == 2) {
                    double altura = Double.parseDouble(vect[0]);
                    double peso = Double.parseDouble(vect[1]);
                    double IMC = 0;

                    IMC = peso / (altura * altura);
                    IMC = IMC * 10000;

                    if (IMC <= 19) {
                        input = "IMC: " + df.format(IMC) + ", abaixo do peso";
                    } else if (IMC <= 25) {
                        input = "IMC: " + df.format(IMC) + ", peso ideal";
                    } else if (IMC <= 30) {
                        input = "IMC: " + df.format(IMC) + ", acima do peso";
                    } else if (IMC <= 35) {
                        input = "IMC: " + df.format(IMC) + ", obesidade leve";
                    } else {
                        input = "IMC: " + df.format(IMC) + ", obesidade";
                    }
                    //Como a frase pode retornar
                    output.writeBytes(input + "\n");
                } else {
                    //Como a frase pode retornar
                    output.writeBytes(input.toUpperCase() + " - Mensagem maiuscula" + "\n");
                }
            }
            //Tratamento de exceção
            catch(NumberFormatException e){
                //Como a frase pode retornar caso a pessoa digite uma frase com duas palavras ou letras
                output.writeBytes("Como você digitou duas palavras ao invés de números sua mensagem foi transformada"
                        + " ao invés de ser calculado seu IMC - "
                        + input.toUpperCase() + " - Mensagem maiuscula" + "\n");
            }
	    // close current connection
	    s.close();
         }
    }
}

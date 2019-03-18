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
        Locale.setDefault(Locale.US);
        DecimalFormat df = new DecimalFormat("###,##0.00");
        // TODO code application logic here
        ServerSocket serverSocket = new ServerSocket(9001);
	 // waits for a new connection. Accepts connetion from multiple clients
	 while (true) {
	    System.out.println("Esperando conexão na porta 9000");
            Socket s = serverSocket.accept();
	    System.out.println("Conexão estabelecida de " + s.getInetAddress());
			 
	    // create a BufferedReader object to read strings from
	    // the socket. (read strings FROM CLIENT)
	    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    String input = br.readLine();
            
            //linhas adicionadas
            String[] vect = input.split(" ");
            
            for(int i = 0; i < input.length(); i++){
                if(Integer.parseInt(vect[0]) == 1){
                    double altura = Double.parseDouble(vect[1]);
                    double peso = Double.parseDouble(vect[2]);
                    double IMC = 0;
                    
                    IMC = peso/(altura * altura);
                    
                    if (IMC <= 19){
                        input = "IMC: " + df.format(IMC) + ", abaixo do peso"; 
                    }
                    else
                    if (IMC <= 25){
                        input = "IMC: " + df.format(IMC) + ", peso ideal"; 
                    }
                    else
                    if (IMC <= 30){
                        input = "IMC: " + df.format(IMC) + ", acima do peso"; 
                    }
                    else
                    if (IMC <= 35){
                        input = "IMC: " + df.format(IMC) + ", obesidade leve"; 
                    }
                    else
                        input = "IMC: " + df.format(IMC) + ", obesidade"; 
                }
            }
            //
            
	    //create output stream to write to/send TO CLINET
            DataOutputStream output = new DataOutputStream(s.getOutputStream());
            
            //Mudança de como a frase é retornada
	    output.writeBytes(input + "\n");
	    // close current connection
	    s.close();
         }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author khaled osman
 * @email khaledosman737@gmail.com
 */
import java.io.*;
import java.util.List;
public class TestingClass {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    // TODO code application logic here
    
    // read code file 
    String code = "";
    BufferedReader br = new BufferedReader(new FileReader("testSample2.txt"));
    try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            //sb.append(System.lineSeparator());
            line = br.readLine();
        }
    
        code = sb.toString();
        
    }
     catch (Exception e) {
            System.out.println(e);
     }
    
    finally {
        br.close();
    }
   
    
    // convert code to tokens 
    System.out.println("read code ");
   // code = " gfd\"\"\" ";
    Lexer l  = new Lexer(code);
    List<Token> tokens = l.tokenize();
    
    l.printTokens();
    System.out.println("number of tokens = " +( tokens != null? tokens.size(): 0));
    //System.out.println("\\\\\"");
    }
    
}

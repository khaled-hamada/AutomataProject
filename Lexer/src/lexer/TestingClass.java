/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author khaled osman
 */
import java.util.*;
public class TestingClass {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // TODO code application logic here
    
    Lexer l  = new Lexer("// my name is khaled hamamda \n \t ({[]}) -> - + * / %-- ++ max_8 int float "
            + "double String else if elseif func fun_445  ");
   // l.tokenize();
    l.printTokens();
    String ttt = "\t\t";
    char c;
    //System.out.println("length of original string is %d" + ttt.length());
    String newt ="";
    int pos =0 ;
    for(int i =0; i<ttt.length() ; i++){
       c = ttt.charAt(i) ; 
       if(c == ' ' || c == '\t'){
           pos +=1 ;
           continue;
       }
       else {
           //pos += 1;
           newt += c;
       }
    }
   // int max  =+      50 ;
   // System.out.println("length of new string is %d"+ newt.length() +"and with pos is %d"+
   //        ( ttt.length()-pos) );
   // System.out.println(ttt + "\n"+ newt);
  // System.out.println("//");
    //System.out.println('(' == ')');
    
    
    
//     ArrayList<Character> delimiter =  new ArrayList<Character>()
//    {{
//        add('('); add(')');add('[');add(']');add('{');add('}');add(',');
//        add(':');add(';');
//    }};
//     c = '(';
//    System.out.println(delimiter.contains(c));
//    String te = 'c'+"b";
    int  max ='z';
    String maxs = max+"";
     System.out.println(maxs);
    
    }
    
}

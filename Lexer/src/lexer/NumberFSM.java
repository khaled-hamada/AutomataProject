/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author khaled osman
 * @email  khaledosman737@gmail.com
 */
import java.util.ArrayList ;
public class NumberFSM {
    
    // FSM properities
    private int[] states ;
    private ArrayList<Integer> finalStates = new ArrayList<Integer>()
    {{
       add(2);add(4);add(7);
    }};
    private int curState ,line , column;
    private String input ;
    public NumberFSM(String input, int line , int column){
        this.states =  new int[]{1,2,3,4,5,6,7};
        this.curState = 1; // start state 
        this.input = input ;
        this.line = line ;
        this.column = column ;
    }
    public Token  runFSM(){
        int pos = 0 , nextS;
        char c ;
        String tokenVal = "";
        while( pos < this.input.length()){
            c = this.input.charAt(pos);
            nextS = nextState(c);
           // System.out.println("Curr state is "+nextS+"  while input char is "+ c);
            if(nextS != -1){
                this.curState = nextS ;
                tokenVal += c;
                pos ++ ;
            }
            else{
                //recognize a number 
                if(this.finalStates.contains(this.curState))
                    // determine type of Number 
                     return new Token("Number",tokenVal,this.line,this.column);
                // uncorrect number foramtion break and return Error 
                else 
                    break ;
            }
                
            
        }
        
        // error wrong number formation 
        return new Token("Unrecognized", tokenVal, this.line, this.column);
    }
    /**
    *  return next state or -1 to indicate error or end of number token 
    */
    private int nextState(char c){
        switch(this.curState){
            // state  1 -- Start State 
            case 1: 
                if(Character.isDigit(c) || c == '+' || c == '-')
                    return 2 ;
                else if( c == '.')
                    return 3; 
                else 
                    return -1 ; // error 
            // state 2 -- Digits state
            case 2 :
                if(Character.isDigit(c) )
                    return 2 ;
                else if( c == '.')
                    return 3; 
                else if(Character.toLowerCase(c) == 'e') 
                    return 5 ; 
                else 
                    return -1 ;
            // start of a fraction -- fraction state 
            case 3 :
                if(Character.isDigit(c) )
                    return 4 ;
                else 
                    return -1 ;
            case 4 :
                if(Character.isDigit(c) )
                    return 4 ;
                
                else if(Character.toLowerCase(c) == 'e') 
                    return 5 ; 
                else 
                    return -1 ;
            case 5 :
                if(Character.isDigit(c) )
                    return 7 ;
                else if( c =='+' || c == '-' )
                    return 6;
                else 
                    return -1 ;
            case 6 :
                if(Character.isDigit(c) )
                    return 7 ;
                else 
                    return -1 ;
                
            case 7:
                if(Character.isDigit(c) )
                    return 7 ;
                else 
                    return -1 ;
            
        }
        return -1 ;
    }
}

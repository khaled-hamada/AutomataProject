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
import java.util.ArrayList ;
public class NumberFSM {
    
    // FSM properities 
    private ArrayList<Integer> states = new ArrayList<Integer>()
    {{
       
    }};
    private ArrayList<Integer> finalStates = new ArrayList<Integer>()
    {{
       add(2);add(4);add(7);
    }};
    private int curState ;
    private String input ;
    public NumberFSM(String input){
        this.curState = 1; // start state 
        this.input = input ;
    }
    public Token  runFSM(){
        int pos = 0 , nextS;
        char c ;
        while( pos < this.input.length()){
            c = this.input.charAt(pos);
            nextS = nextState(c);
            
            
        }
        return new Token("Unrecognized Token","",0,0);
    }
    private int nextState(char c){
        switch(curState){
            
        }
        return 0 ;
    }
}

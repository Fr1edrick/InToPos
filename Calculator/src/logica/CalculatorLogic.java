package logica;

import java.util.Iterator;
import java.util.Stack;

/* Logica preliminar para operaciones de la calculadora */

public class CalculatorLogic {

	Stack operators = new Stack(); //Declaración de la pila para operadores
	Stack operands = new Stack(); //Declaración de la pila de operandos
	private int [][] m = new int[7][7];
	private int i;
	String res;
		
	public void calculate(char[] input) {

		// llega un input desde el main

		for (i = 0; i <= input.length - 1; i++) {

			if (operando(input[i])) {
				operands.push(input[i]);
			}

			else {
				while (!operators.isEmpty() && prioridad((char) operators.peek(), input[i]) == 1) {
					//operands.push(operators.peek());
					String num2 = String.valueOf(operands.peek());
					operands.pop();
					String num1 = String.valueOf(operands.peek());
					operands.pop();
					String symb = String.valueOf(operators.peek());
					String rst = String.valueOf(conteo(symb, num1, num2));
					operands.push(rst);
					operators.pop();
				}
				operators.push(input[i]);
			}
		}
		while (!operators.isEmpty()) {
			//operands.push(operators.peek());
			String num2 = String.valueOf(operands.peek());
			operands.pop();
			String num1 = String.valueOf(operands.peek());
			operands.pop();
			String symb = String.valueOf(operators.peek());
			String rst = String.valueOf(conteo(symb, num1, num2));
			operands.push(rst);
			operators.pop();
		}		
	}
	
	public void resume() {
		System.out.println("Total : " + operands.peek());
	}
	
	public void initMatriz(){
		int i, j;
		for (i = 0; i < 6; i++){
			for (j = 0; j < 5; j++){
				if (j <= i){
					m[i][j] = 1;
				}
				else{
					m[i][j] = 0;
				}
				if (i == 5) {
					m[i][j] = 0;
				}
				m[0][1] = m[2][3] = 1;
				System.out.print(m[i][j]);
			}
			for (j = 5; j < 7; j++) {
				if (j == 6) {
					m[i][j] = 1;
				}
				if (i == 5) {
					m[i][j] = 0;
				}
				System.out.print(m[i][j]);
			}
			System.out.print("\n");	
		}	
		//m[0][1] = m[2][3] = 1;
	}
	
	public int prioridad(char op1, char op2){
		int i=0, j=0;
		
		switch(op1){
		case '+' : i = 0;
		break;
		case '-' : i = 1;
		break;
		case '*' : i = 2;
		break;
		case '/' : i = 3;
		break;
		case '^' : i = 4;
		break;
		}
		
		switch(op2){
		case '+' : j = 0;
		break;
		case '-' : j = 1;
		break;
		case '*' : j = 2;
		break;
		case '/' : j = 3;
		break;
		case '^' : j = 4;
		break;
		}
		System.out.println("Prioridad : " + m[i][j]);
		return (m[i][j]);
	}
	
	public boolean operando(char o){
		return (o != '+' &&
				o != '-' &&
				o != '*' &&
				o != '/' &&
				o != '^' );
	}
	
	public double conteo(String symbol, String number1, String number2){
		Double n1 = Double.parseDouble(number1);
		Double n2 = Double.parseDouble(number2);
						
		if(symbol.equals("+")) return (n1 + n2);
		if(symbol.equals("-")) return (n1 - n2);
		if(symbol.equals("*")) return (n1 * n2);
		if(symbol.equals("/")) return (n1 / n2);
		if(symbol.equals("^")) return (Math.pow(n1, n2));
		return 0;
	}

	
		
	/*
	 
	 
	public boolean emptyInput(char[] input) {
		if (input.length == 0)
		return true;
		return false;
	}
	
	public int sizeInput(char[] input) {
		int t;
		t = input.length;
		return t;
	}
	
	public void quitarE (int position, char[] input) {
		int i;
		for (i = position; i < input.length - 1; i++) {
			input[i] = input[i + 1];
		}
	}

 		while (!general.stackIsEmpty()){
			r = general.stackPeek();
			switch(jerarquia(general.stackPeek())){
			// Jerarquia
			// 1. (
			// 2. )
			// 3. +, -
			// 4. *. /
			// 5. ^
			// 0. nada 
			case 1:
				break;
			case 2:
				break;
			case 3:
			case 4:
			case 5:
				if (operators.stackIsEmpty()){
					operators.stackPush(r);
					general.stackPop();
				}
				else if(jerarquia(operators.stackPeek()) >= jerarquia(r)){
					String simbolo = operators.stackPeek(); 
					operators.stackPop();
					operators.stackPush(r);
					general.stackPop();
					String num1 = operands.stackPeek();
					operands.stackPop();
					String num2 = operands.stackPeek();
					operands.stackPop();
					String rst = String.valueOf(conteo(simbolo, num1, num2));
					operands.stackPush(rst);
				}
				break;
			default:
				operands.stackPush(r);
				general.stackPop();
				break;
			}
		}
		System.out.println("Result : " + operands.stackPeek());
	}
	
	public int jerarquia(String j){
		int precedencia = 0;
		if (j.equals("^")) precedencia = 5;
		if (j.equals("*") || j.equals("/")) precedencia = 4;
		if (j.equals("+") || j.equals("-")) precedencia = 3;
		if (j.equals(")")) precedencia = 2;
		if (j.equals("(")) precedencia = 1;
		return precedencia;
	}
	
	public boolean isNumeric(String str){
		try{
			double number = Double.parseDouble(str);
		}
		catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
	public void calculate(char[] input){
		
		i = 0;
						
		while (input[i] != '#') {
			if (operando(input[i])) {
				operands.push(input[i++]);
			}
			else{
				while (!operators.isEmpty() && prioridad((char)operators.peek(), input[i]) == 1) {
					String num1 = (String) operands.peek();
					operands.pop();
					String num2 = (String) operands.peek();
					operands.pop();
					String sym = (String) operators.peek();
					operators.pop();
					String rst = String.valueOf(conteo(sym, num1, num2));
					operands.push(rst);
					operators.pop();
					i++;
				}
				operators.push(input[i++]);
				i++;
			}
		}
	}*/

}

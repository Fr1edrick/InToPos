package logica;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * Clase creada con la Lógica preliminar para operaciones de la calculadora
 * haciendo uso del algoritmo de infijo a posfijo
 * @author Fredy L. Vaquiro Perdomo
 * @author Marielena Barrio Reinoso
 * @author 
 * @see <a href = "https://github.com/Fr1edrick/InToPos"> GitHub Calculator </a>
 */

public class CalculatorLogic {

	//Variable de entorno
	Stack operators = new Stack(); //Declaración de la pila para operadores
	Stack operands = new Stack(); //Declaración de la pila de operandos
	private int [][] m = new int[7][7];
	private int i;
	
	
	/**
	 * Método con el algoritmo que realiza la conversión infija a postfija
	 * con su correspondiente conteo
	 * @param char[] input Entrada en forma de expresión dada para ser procesada 
	 */
	public void calculate(char[] input) {

		String num1, num2, symb, rst;
		
		if (checkAlpha(String.valueOf(input[i]))) {
			System.out.println("[ " + input[i] + " ]" + " No es un numero o signo matemático, verifique e intente de nuevo");
			System.exit(0);
		}
		
		for (i = 0; i <= input.length - 1; i++) {
			
			if (operando(input[i])) {
				operands.push(input[i]);
			} 
			else if (input[i] == '(') {
				operators.push(input[i]);
			}
			else if (input[i] == ')') {
				while (!operators.isEmpty() && (char) operators.peek() != '(') {
					num2 = String.valueOf(operands.peek());
					operands.pop();
					num1 = String.valueOf(operands.peek());
					operands.pop();
					symb = String.valueOf(operators.peek());
					rst = String.valueOf(conteo(symb, num1, num2));
					operands.push(rst);
					operators.pop();
					operators.pop();
				}
			}

			else {
				while (!operators.isEmpty() && prioridad((char) operators.peek(), input[i]) == 1) {

					num2 = String.valueOf(operands.peek());
					operands.pop();
					num1 = String.valueOf(operands.peek());
					operands.pop();
					symb = String.valueOf(operators.peek());
					rst = String.valueOf(conteo(symb, num1, num2));
					operands.push(rst);
					operators.pop();
				}
				operators.push(input[i]);
			}
		}
		while (!operators.isEmpty()) {
			// operands.push(operators.peek());
			num2 = String.valueOf(operands.peek());
			operands.pop();
			num1 = String.valueOf(operands.peek());
			operands.pop();
			symb = String.valueOf(operators.peek());
			rst = String.valueOf(conteo(symb, num1, num2));
			operands.push(rst);
			operators.pop();
		}
	}

	public boolean checkAlpha(String input) {
		String v = "[a-zA-Z]";
		Pattern validar = Pattern.compile(v);
		Matcher confirma = validar.matcher(input);
		if (confirma.matches()) {
		return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Método para mostrar en pantalla el resultado de conteo final 
	 * realizado por el algoritmo
	 */
	public void resume() {
		System.out.println("Total : " + operands.peek());
	}
	
	/**
	 * Método para que inicializa la matriz de precedencia de signos
	 * matemáticos y obtener su jerarquía
	 */
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
	
	/**
	 * Método para que apoya a la matriz de signo para dar la prioridad en 
	 * la jerarquía de los signos
	 * @param op1 fila de la matriz
	 * @param op2 columna de la matriz
	 * @return coordenadas de la matriz
	 */
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
		case '(' : i = 5;
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
		case ')' : j = 6;
		break;
		}
		System.out.println("Prioridad : " + m[i][j]);
		return (m[i][j]);
	}
	
	/**
	 * Método para validar que el operando sea un numero y no los simbolos
	 * matemáticos
	 * @param o operador o numero a validar
	 * @return true o false si cumple con la validación
	 */
	public boolean operando(char o){
		return (o != '+' &&
				o != '-' &&
				o != '*' &&
				o != '/' &&
				o != '^' &&
				o != '(' &&
				o != ')' );
	}
	
	/**
	 * Método que realiza las operaciones mátemáticas dependiendo del signo
	 * @param symbol Signo matemático operador
	 * @param number1 Primer operando
	 * @param number2 Segundo operando
	 * @return valor obtenido de la operación redondeado a 2 cifras decimales
	 */
	public double conteo(String symbol, String number1, String number2){
		Double n1 = Double.parseDouble(number1);
		Double n2 = Double.parseDouble(number2);
						
		if(symbol.equals("+")) return Math.round((n1 + n2) * 100.0) / 100.0;
		if(symbol.equals("-")) return Math.round((n1 - n2) * 100.0) / 100.0;
		if(symbol.equals("*")) return Math.round((n1 * n2) * 100.0) / 100.0;
		if(symbol.equals("/")) return Math.round((n1 / n2) * 100.0) / 100.0;
		if(symbol.equals("^")) return Math.round((Math.pow(n1, n2))* 100.0) / 100.0;
		return 0;
	}
}

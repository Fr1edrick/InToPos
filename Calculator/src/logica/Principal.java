package logica;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalculatorLogic cal = new CalculatorLogic();
		cal.initMatriz();
		String exp = "(2+4)*5^2/6-1";
		char[] infijo = new char[exp.length()];
		exp.getChars(0, exp.length(), infijo, 0);
		cal.calculate(infijo);
		cal.resume();	
	}

}

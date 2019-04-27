package main;

public class Calculator {

	public static double calculate(double firstNumber, double secondNumber, String operation) {

		double result = 0;

		switch (operation) {
		case "+": {
			result = firstNumber + secondNumber;
			break;
		}
		case "-": {
			result = firstNumber - secondNumber;
			break;
		}
		case "*": {
			result = firstNumber * secondNumber;
			break;
		}
		case "/": {
			result = firstNumber / secondNumber;
			break;
		}
		default: {
			result = 0;
			break;
		}
		}

		return result;
	}
}

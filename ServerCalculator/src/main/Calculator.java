package main;

public class Calculator {

	public static int calculate(int firstNumber, int secondNumber, String operation) {

		int result = 0;

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

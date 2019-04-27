package main;

public class Control {

	public static boolean isInputNumbersOk(String entry) {
		if (entry.equals("exit"))
			return true;

		try {
			Double.parseDouble(entry);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isInputSignOk(String entry) {
		if (entry.equals("exit"))
			return true;
		
		if (entry.equals("+") || entry.equals("-") || entry.equals("*") || entry.equals("/"))
			return true;

		return false;
	}
	
	public static boolean isDivideByZero(double secondNumber, String operation) {
		if(secondNumber == 0 && operation.equals("/"))
			return true;
		
		return false;
	}
}

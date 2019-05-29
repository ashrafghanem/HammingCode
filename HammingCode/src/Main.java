import java.util.Scanner;

public class Main {
	public static boolean checkBinary(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (!Character.isDigit(input.charAt(i)) || (input.charAt(i) != '0' && input.charAt(i) != '1')) {
				return false;
			}
		}
		return true;
	}

	public static void findParityTerm(String input, int step, String[] code) {
		String P = "";
		int count = 0;

		for (int i = step - 1; i < input.length();) {
			if ((i + step) > input.length())
				P += input.substring(i, input.length());
			else
				P += input.substring(i, i + step);

			if (step == 1 || step == 2)
				i += Math.pow(2, step);
			else if (step == 4 || step == 8)
				i += Math.pow(2, step - 1);
		}
		for (int j = 0; j < P.length(); j++) {
			if (P.charAt(j) == '1')
				count++;
		}

		if (count % 2 == 0) {
			if (step == 1)
				code[0] = "0";
			else if (step == 2)
				code[1] = "0";
			else if (step == 4)
				code[2] = "0";
			else if (step == 8)
				code[3] = "0";
		} else {
			if (step == 1)
				code[0] = "1";
			else if (step == 2)
				code[1] = "1";
			else if (step == 4)
				code[2] = "1";
			else if (step == 8)
				code[3] = "1";
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("Enter the binary input sent data [8 bits]: ");

		String input;
		input = in.nextLine();

		while (true) {
			try {
				if (input.length() != 8 || !checkBinary(input))
					throw new Exception();
				else
					break;
			} catch (Exception ex) {
				System.out.println("Wrong Input Data");
				input = in.nextLine();
			}
		}
		input = "XX" + input.charAt(0) + "X" + input.substring(1, 4) + "X" + input.substring(4, 8);

		String encodedData = "";
		String code[] = new String[4];

		findParityTerm(input, 1, code);
		findParityTerm(input, 2, code);
		findParityTerm(input, 4, code);
		findParityTerm(input, 8, code);

		System.out.print("Code is: ");
		for (int i = 0; i < code.length; i++) {
			System.out.print(code[i]);
		}
		int k = 0;
		for (int j = 0; j < input.length(); j++) {
			if (input.charAt(j) == 'X') {
				encodedData += code[k++];
			} else {
				encodedData += input.charAt(j);
			}
		}
		System.out.println("\nEncoded Data is: " + encodedData);

		System.out.println("Enter the received data, bit by bit:");
		String receivedData = "";

		while (true) {
			for (int i = 0; i < 12; i++) {
				receivedData += in.next();
			}
			if (!checkBinary(receivedData)) {
				System.out.println("Wrong Data inserted!\nTry again");
			} else
				break;
		}

		String checkCode[] = new String[4];
		findParityTerm(receivedData, 1, checkCode);
		findParityTerm(receivedData, 2, checkCode);
		findParityTerm(receivedData, 4, checkCode);
		findParityTerm(receivedData, 8, checkCode);

		int countErrorPosition = 0;
		for (int i = 0; i < 4; i++) {
			if (checkCode[i] == "1") {
				countErrorPosition += Math.pow(2, i);
			}
		}
		int c = 0;
		for (int i = 0; i < encodedData.length(); i++) {
			if (encodedData.charAt(i) != receivedData.charAt(i)) {
				c++;
			}
		}
		if (c > 1) {
			System.out.println("More than one error occured! \nCannot correct them!");
		} else if (countErrorPosition == 0) {
			System.out.println("No Error");
		} else {
			System.out.println("The Error occured at bit: " + countErrorPosition);
		}
		in.close();
	}
}

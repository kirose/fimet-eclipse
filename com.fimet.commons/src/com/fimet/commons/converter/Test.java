package com.fimet.commons.converter;

//import static com.fimet.commons.converter.Converter.*;

public class Test {
	static byte[][] asciiToHex = {{48,48},{48,49},{48,50},{48,51},{48,52},{48,53},{48,54},{48,55},{48,56},{48,57},{48,65},{48,66},{48,67},{48,68},{48,69},{48,70},{49,48},{49,49},{49,50},{49,51},{49,52},{49,53},{49,54},{49,55},{49,56},{49,57},{49,65},{49,66},{49,67},{49,68},{49,69},{49,70},{50,48},{50,49},{50,50},{50,51},{50,52},{50,53},{50,54},{50,55},{50,56},{50,57},{50,65},{50,66},{50,67},{50,68},{50,69},{50,70},{51,48},{51,49},{51,50},{51,51},{51,52},{51,53},{51,54},{51,55},{51,56},{51,57},{51,65},{51,66},{51,67},{51,68},{51,69},{51,70},{52,48},{52,49},{52,50},{52,51},{52,52},{52,53},{52,54},{52,55},{52,56},{52,57},{52,65},{52,66},{52,67},{52,68},{52,69},{52,70},{53,48},{53,49},{53,50},{53,51},{53,52},{53,53},{53,54},{53,55},{53,56},{53,57},{53,65},{53,66},{53,67},{53,68},{53,69},{53,70},{54,48},{54,49},{54,50},{54,51},{54,52},{54,53},{54,54},{54,55},{54,56},{54,57},{54,65},{54,66},{54,67},{54,68},{54,69},{54,70},{55,48},{55,49},{55,50},{55,51},{55,52},{55,53},{55,54},{55,55},{55,56},{55,57},{55,65},{55,66},{55,67},{55,68},{55,69},{55,70}};
	static byte[] hexToAscii = {};
	public static void main(String[] args) {
		/*String text = "Hola Marco A. Salazar. Mi numero es: 5522509168";
		System.out.println(text);
		System.out.println(new String(asciiToHex(text.getBytes())));
		System.out.println(new String(hexToAscii(asciiToHex(text.getBytes()))));
		String ascii = "Hola";
		String binary = new String(asciiToBinary(ascii.getBytes()));
		System.out.println(ascii);
		System.out.println(binary);
		String hex = new String(asciiToHex(ascii.getBytes()));
		System.out.println(hex);
		System.out.println(new String(hexToBinary(hex.getBytes())));
		System.out.println(new String(binaryToHex(binary.getBytes())));
		System.out.println(new String(binaryToAscii(binary.getBytes())));
		byte[] byteArray = new byte[] {1, -54};
		StringBuilder byteString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			byteString.append(String.format("%02X",byteArray[i]));
		}
		System.out.println(byteString.toString());*/
		testAsciiToHex();
	}
	/*private static void testHexToAscii() {
		for (int i = 0; i < 128; i++) {
			System.out.println(i+"-"+hexToAscii(asciiToHex(new byte[] {(byte)i}))[0]);
		}
	}*/
	private static void testAsciiToHex() {
		String hex;
		StringBuilder sb = new StringBuilder();
		byte[] pair;
		//byte[] pair2;
		for (int i = -128; i < 128; i++) {
			hex = String.format("%02X", (byte)i);
			pair = hex.getBytes();
			sb.append(((byte)i)+"{"+pair[0]+","+pair[1]+"},"+new String(pair)+"\n");
			//pair2 = asciiToHex[i];
			//sb.append("{"+pair[0]+","+pair[1]+"},{"+pair2[0]+","+pair2[1]+"},"+new String(pair)+","+new String(pair2)+"\n");
		}
		System.out.println(sb.toString());
	}

}

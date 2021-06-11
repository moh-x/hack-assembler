package hack;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * Translates Hack assembly language mnemonics into binary codes.
 * */
public class Code {
	
	public static void main ( String[] args ) {}
	
	private final static Map<String, String> destTable;
	static {
		HashMap<String, String> destCodes = new HashMap<>();
		destCodes.put(null, "000");
		destCodes.put("M", "001");
		destCodes.put("D", "010");
		destCodes.put("MD", "011");
		destCodes.put("A", "100");
		destCodes.put("AM", "101");
		destCodes.put("AD", "110");
		destCodes.put("AMD", "111");
		destTable = Collections.unmodifiableMap(destCodes);
	}
	
	private final static Map<String, String> compTable;
	static {
		HashMap<String, String> compCodes = new HashMap<>();
		compCodes.put("0", "0"+"101010");
		compCodes.put("1", "0"+"111111");
		compCodes.put("-1", "0"+"111010");
		compCodes.put("D", "0"+"001100");
		compCodes.put("A", "0"+"110000");
		compCodes.put("M", "1"+"110000");
		compCodes.put("!D", "0"+"001101");
		compCodes.put("!A", "0"+"110001");
		compCodes.put("!M", "1"+"110001");
		compCodes.put("-D", "0"+"001111");
		compCodes.put("-A", "0"+"110011");
		compCodes.put("-M", "1"+"110011");
		compCodes.put("D+1", "0"+"011111");
		compCodes.put("A+1", "0"+"110111");
		compCodes.put("M+1", "1"+"110111");
		compCodes.put("D-1", "0"+"001110");
		compCodes.put("A-1", "0"+"110010");
		compCodes.put("M-1", "1"+"110010");
		compCodes.put("D+A", "0"+"000010");
		compCodes.put("D+M", "1"+"000010");
		compCodes.put("D-A", "0"+"010011");
		compCodes.put("D-M", "1"+"010011");
		compCodes.put("A-D", "0"+"000111");
		compCodes.put("M-D", "1"+"000111");
		compCodes.put("D&A", "0"+"000000");
		compCodes.put("D&M", "1"+"000000");
		compCodes.put("D|A", "0"+"010101");
		compCodes.put("D|M", "1"+"010101");
		compTable = Collections.unmodifiableMap(compCodes);
	}
	
	private final static Map<String, String> jumpTable;
	static {
		HashMap<String, String> jumpCodes = new HashMap<>();
		jumpCodes.put(null, "000");
		jumpCodes.put("JGT", "001");
		jumpCodes.put("JEQ", "010");
		jumpCodes.put("JGE", "011");
		jumpCodes.put("JLT", "100");
		jumpCodes.put("JNE", "101");
		jumpCodes.put("JLE", "110");
		jumpCodes.put("JMP", "111");
		jumpTable = Collections.unmodifiableMap(jumpCodes);
	}
	
	/***/
	static String calc ( int decimal ) {
		String binary = Integer.toBinaryString(decimal);
		int zeros = 15 - binary.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < zeros; i++) {
			sb.append(0);
		}
		return "0"+ sb.toString() + binary;
	}
	
	/**
	 * Returns the binary code of the dest mnemonic.
	 * */
	String dest ( String mnemonic ) {
		return destTable.get(mnemonic);
	}
	
	/**
	 * Returns the binary code of the comp mnemonic.
	 * */
	String comp ( String mnmeonic ) {
		return compTable.get(mnmeonic);
	}
	
	/**
	 * Returns the binary code of the jump mnemonic.
	 * */
	String jump ( String mnemonic ) {
		return jumpTable.get(mnemonic);
	}

}

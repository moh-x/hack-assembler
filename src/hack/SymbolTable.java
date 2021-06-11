package hack;


import java.util.HashMap;


/**
 * Keeps a correspondence between symbolic labels and numeric 
 * addresses.
 * */
public class SymbolTable {
	
	public static void main ( String[] args ) {
		/*
		SymbolTable st = new SymbolTable();
		System.out.println(symbolTable.size());
		*/
	}

	private static HashMap<String, Integer> symbolTable;
	
	/**
	 * Creates a new empty symbol table.
	 * */
	SymbolTable () {
		symbolTable = new HashMap<>();
		symbolTable.put("SP", 0);
		symbolTable.put("LCL", 0);
		symbolTable.put("ARG", 0);
		symbolTable.put("THIS", 0);
		symbolTable.put("THAT", 0);
		for (int i = 0; i < 16; i++)
			symbolTable.put(String.format("R%d", i), i);
		symbolTable.put("SCREEN", 16384);
		symbolTable.put("KBD", 24576);
	}
	
	/**
	 * Adds the pair (symbol, address) to the table.
	 * */
	void addEntry ( String symbol, int address) {
		symbolTable.put(symbol, address);
	}
	
	/**
	 * Does the symbol table contain the given symbol?
	 * */
	boolean contains ( String symbol ) {
		return symbolTable.containsKey(symbol);
	}
	
	/**
	 * Returns the address associated with the symbol.
	 * */
	int getAddress ( String symbol ) {
		return symbolTable.get(symbol);
	}
	
}

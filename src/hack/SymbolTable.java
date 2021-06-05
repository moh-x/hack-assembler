package hack;


import java.util.HashMap;


/**
 * Keeps a correspondence between symbolic labels and numeric 
 * addresses.
 * */
public class SymbolTable {

	private static final long serialVersionUID = 1L;
	private static HashMap<String, Integer> symbolTable;
	
	/**
	 * Creates a new empty symbol table.
	 * */
	SymbolTable () {
		symbolTable = new HashMap<>();
	}
	
	/**
	 * Adds the pair (symbol, address) to the table.
	 * */
	void addEntry ( String symbol, int address) {
		symbolTable.put(symbol, address);
	}
	
	/**
	 * Does the symbol table contain the given symbol ?
	 * */
	boolean contains ( String symbol ) {
		return symbolTable.containsKey(symbol);
	}
	
	
	
}

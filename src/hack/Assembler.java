package hack;


import java.io.*;

public class Assembler {
	
	public static void main ( String[] args ) {
		SymbolTable symbolTable;
		final String fileName = "prog.asm";
			// data output stream.
		
		// System.out.println(System.getProperty("user.dir"));
		
		/* First Pass */
		try ( Parser parser1 = new Parser(); ) {
			int romAddress = 0;
			symbolTable = new SymbolTable();
			while ( parser1.hasMoreCommands() ) { // Read until end-of-file.
				parser1.advance();
				
				// Record the current ROM address.
				switch ( parser1.commandType() ) {
				case A_COMMAND:
				case C_COMMAND:
					romAddress++;
					continue;
				case L_COMMAND:
					symbolTable.addEntry(parser1.symbol(), romAddress);
					continue;
				}
			}
			
		} catch ( FileNotFoundException e ) {
			System.out.println("Can't find file prog.asm!");
			return;
		}
		
		
		/* Second Pass */
		try ( Parser parser2 = new Parser();
			  // Prepare a corresponding .hack output file.
			  PrintWriter output = new PrintWriter(fileName.replace(".asm", ".hack")); ) {
			int ramAddress = 16;
			String instruction;
			while ( parser2.hasMoreCommands() ) { // Read until end-of-file.
				parser2.advance();
				
				switch ( parser2.commandType() ) {
				case A_COMMAND:
					String symbol = parser2.symbol();
					try {
						instruction = Code.calc(Integer.parseInt(symbol));
					} catch ( NumberFormatException e ) {
						Integer address;
						if ( symbolTable.contains(symbol) ) { // Variable's been initialized.
							address = symbolTable.getAddress(symbol);
						} else { // New variable.
							address = ramAddress;
							symbolTable.addEntry(symbol, address);
							ramAddress++;
						}
						instruction = Code.calc(address);
					}
					break;
				case C_COMMAND:	// Definitely a C_COMMAND.
					Code binary = new Code();
					String dest = binary.dest(parser2.dest());
					String comp = binary.comp(parser2.comp());
					String jump = binary.jump(parser2.jump());
					String lineOut = "111" + comp + dest + jump;
					instruction = lineOut;
					break;
				default:
					continue;
				}
				
				output.println(instruction);
				
			}
			output.flush();
		} catch ( FileNotFoundException e ) {
			System.out.println("Can’t create file prog.hack!");
			System.out.println("Error: " + e);
			return;
		}

		System.out.println("Done!");

	}

}

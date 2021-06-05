package hack;


import java.io.*;

public class Assembler {
	
	public static void main ( String[] args ) {
		Parser data;	// For reading input.
		PrintWriter result;	// data output stream.
		
		// System.out.println(System.getProperty("user.dir"));
		
		try {
			data = new Parser(new File("prog.asm"));
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file prog.asm!");
			return;
		}
		
		try {
			result = new PrintWriter("prog.hack");
		} catch (FileNotFoundException e) {
			System.out.println("Can’t open file prog.hack!");
			System.out.println("Error: " + e);
			data.close(); // Close the input file.
			return;
		}
		
		while ( data.hasMoreCommands() ) { // Read until end-of-file.
			data.advance();
			Code binary = new Code();
			String outLine;
			
			switch ( data.commandType() ) {
			case A_COMMAND:
			case L_COMMAND:
				String symbol = data.symbol();
				// Check if symbol is a number.
				outLine = binary.calc(Integer.parseInt(symbol));
				break;
			default:	// Definitely a C_COMMAND.
				String dest = binary.dest(data.dest());
				String comp = binary.comp(data.comp());
				String jump = binary.jump(data.jump());
				outLine = "111" + comp + dest + jump;
				break;
			}
			
			result.println(outLine);
		}
		System.out.println("Done!");
		
		data.close();
		result.close();
	}

}

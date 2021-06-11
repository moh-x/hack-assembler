package hack;

import java.io.*;
import java.util.Scanner;

/**
 * Encapsulates access to the input code. Reads an assembly language command, 
 * parses it, and provides convenient access to the command's components 
 * (fields and symbols). In addition, removes all white space and comments.
 * @author xodeeq
 * */
public class Parser implements AutoCloseable {
	
	private final File asmFile;
	protected static enum commandType { A_COMMAND, C_COMMAND, L_COMMAND };
	private Scanner program;
	private String currentCommand;

	/**
	 * Opens the input file/stream and gets ready to parse it
	 * @param fileIn
	 * */
	Parser (File fileIn) throws FileNotFoundException {
		asmFile = fileIn;
		program = new Scanner( asmFile );
	}
	
	Parser () throws FileNotFoundException {
		asmFile = new File("prog.asm");
		program = new Scanner( asmFile );
	}

	/**
	 * Are there more commands in the input?
	 * */
	boolean hasMoreCommands () {
		return program.hasNextLine();
	}
	
	/**
	 * Reads the next command from the input and makes it the current 
	 * command. Should be called only if hasMoreCommands() is true. 
	 * Initially there is no current command.
	 * */
	void advance () {
		String nextLine = program.nextLine().strip();
		if ( nextLine.startsWith("//") || nextLine.isEmpty() ) {
			advance();
			return;
		}
		if ( nextLine.contains("//") ) {
			currentCommand = nextLine.split("//")[0].strip();
			return;
		}
		currentCommand = nextLine.strip();
		return;
	}
	
	/**
	 * Returns the type of the current command:
	 * ~ A_COMMAND for @Xxx where Xxx is either a symbol or a decimal number
	 * ~ C_COMMAND for dest=comp;jump
	 * ~ L_COMMAND (actually, pseudo-command) for (Xxx) where Xxx is a symbol.
	 * */
	commandType commandType () {
		if ( currentCommand.startsWith("@") )
			return commandType.A_COMMAND;
		else if ( currentCommand.startsWith("(") )
			return commandType.L_COMMAND;
		else
			return commandType.C_COMMAND;
	}
	
	/**
	 * Returns the symbol or decimal Xxx of the current command @Xxx or (Xxx). 
	 * Should be called only when commandType() is A_COMMAND or L_COMMAND.
	 * */
	String symbol () {
		switch ( commandType() ) {
		case A_COMMAND:
			return currentCommand.substring(1);
		default:
			return currentCommand.substring(1, currentCommand.length()-1);
		}
	}
	
	/**
	 * Returns the dest mnemonic in the current C-command (8 possibilities). 
	 * Should be called only when commandType() is C_COMMAND.
	 * */
	String dest () {
		return currentCommand.contains("=") ? currentCommand.split("=")[0] : null;
	}
	
	/**
	 * Returns the comp mnemonic in the current C-command (28 possibilities). 
	 * Should be called only when commandType() is C_COMMAND .
	 * */
	String comp () {
		String comp = currentCommand;
		if ( comp.contains("=") ) {
			comp = currentCommand.split("=")[1];
		}
		if ( comp.contains(";") ) {
			comp = currentCommand.split(";")[0];
		}
		return comp;
	}
	
	/**
	 * Returns the jump mnemonic in the current C-command (8 possibilities). 
	 * Should be called only when commandType() is C_COMMAND .
	 * */
	String jump () {
		return currentCommand.contains(";") ? currentCommand.split(";")[1] : null;
	}
	
	public void close () {
		program.close();
		return;
	}
	
}

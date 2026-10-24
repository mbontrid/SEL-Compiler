
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Main class to run the parser for SEL compiler.
 * Read a source file, parse it, and print leftmost derivation rule numbers.
 * Additionally, can output LaTeX representation if specified.
 */
public class Main {

  /**
   * Helper class to parse command line arguments.
   */
  private static class ArgParser {

    /** The input source file */
    String inputFile;

    /**
     * Parses command line arguments. Will exit on invalid arguments.
     * 
     * @param args command line arguments
     */
    ArgParser(String[] args) {

      // 1 argument: source file only
      // 3 arguments: -wt latexFile sourceFile
      // otherwise: error

      if (args.length == 1) {

        inputFile = args[0];

      } else {

        System.err.println("Invalid arguments. Usage: java -jar part1.jar sourceFile.sel");
        System.exit(1);

      }
    }

  }

  /**
   * Run the lexical analyzer on the given source file.
   * It prints all tokens and a list of variables with their first occurrence
   * line.
   * 
   * @param filePath the path to the source file.
   */
  private static void runLexer(String filePath) {

    try {

      // create lexer and variable map
      LexicalAnalyzer lexer = new LexicalAnalyzer(new FileReader(filePath));
      Map<String, Integer> varMap = new TreeMap<>();
      Symbol token;

      // process tokens until there are none left
      while ((token = lexer.yylex()) != null) {

        if (token.getType() == LexicalUnit.EOS)
          break; // end of stream token -> stop processing

        System.out.println(token);
        if (token.getType() == LexicalUnit.VARNAME) {
          varMap.putIfAbsent(token.getValue().toString(), token.getLine());
        }
      }

      // print variables if any
      if (!varMap.isEmpty()) {
        System.out.println("Variables");
        varMap.forEach((name, line) -> System.out.println(name + " " + line));
      }

      // catch io exceptions (there might be more but this will do for now)
    } catch (IOException e) {
      System.err.println("I/O Error: " + e.getMessage());
    }
  }

  /**
   * Main method to run the parser.
   * 
   * @param args command line arguments; expects source file and optional -wt for
   *             LaTeX output
   */
  public static void main(String[] args) {

    // Parse command line arguments
    ArgParser parsed = new ArgParser(args);
    String inputFile = parsed.inputFile;

    // Run the lexical analyzer
    runLexer(inputFile);

  }

}

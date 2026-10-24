
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.StringReader;

/**
 * Unit tests for the LexicalAnalyzer class.
 * These tests cover various scenarios including valid tokens, comments,
 * whitespace handling, and invalid tokens.
 */
public class LexerTest {

  /**
   * Test the lexical analyzer (lexer) for correct tokenization of a regular input
   * string.
   */
  @Test
  public void testTokenScan() throws Exception {
    // sample code to be tokenized (from pdf example)
    String code = "IN a;";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // token IN
    Symbol token = lexer.yylex();
    assertEquals(LexicalUnit.IN, token.getType());
    assertEquals("IN", token.getValue());

    // token a
    token = lexer.yylex();
    assertEquals(LexicalUnit.VARNAME, token.getType());
    assertEquals("a", token.getValue());

    // token ;
    token = lexer.yylex();
    assertEquals(LexicalUnit.SEMI, token.getType());

    // token end of stream (EOS)
    token = lexer.yylex();
    assertEquals(LexicalUnit.EOS, token.getType());
  }

  /**
   * Test that spaces and tabs are ignored by the lexer.
   */
  @Test
  public void testSpacesAndTabs() throws Exception {

    // sample code with spaces and tabs
    String code = "   Main()   \t ;  ";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // token Main
    Symbol token = lexer.yylex();
    assertEquals(LexicalUnit.MAIN, token.getType());

    // token (
    token = lexer.yylex();
    assertEquals(LexicalUnit.LPAREN, token.getType());

    // token )
    token = lexer.yylex();
    assertEquals(LexicalUnit.RPAREN, token.getType());

    // token ;
    token = lexer.yylex();
    assertEquals(LexicalUnit.SEMI, token.getType());

    // token end of stream (EOS)
    token = lexer.yylex();
    assertEquals(LexicalUnit.EOS, token.getType());
  }

  /**
   * Test that short comments (starting with $) are ignored by the lexer.
   */
  @Test
  public void testShortCommentIgnored() throws Exception {

    // sample code with a short comment
    String code = "IN a; $ this is a comment\n@ a;";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // token IN
    Symbol token = lexer.yylex();
    assertEquals(LexicalUnit.IN, token.getType());

    // token a
    token = lexer.yylex();
    assertEquals(LexicalUnit.VARNAME, token.getType());
    assertEquals("a", token.getValue());

    // token ;
    token = lexer.yylex();
    assertEquals(LexicalUnit.SEMI, token.getType());

    // token @ (should have skipped the comment)
    token = lexer.yylex();
    assertEquals(LexicalUnit.AROBASE, token.getType());

    // token a
    token = lexer.yylex();
    assertEquals(LexicalUnit.VARNAME, token.getType());
    assertEquals("a", token.getValue());
  }

  /**
   * Test that long comments (enclosed in !!) are ignored by the lexer.
   */
  @Test
  public void testLongCommentIgnored() throws Exception {

    // sample code with a long comment
    String code = "## this is a long comment \n yeah long ####Is it ?##\nMain() ;";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // token Main (should have skipped the comment)
    Symbol token = lexer.yylex();
    assertEquals(LexicalUnit.MAIN, token.getType());

    // no need to check further tokens for this test
  }

  /**
   * Test that invalid tokens throw an exception.
   */
  @Test
  public void testInvalidTokens() throws Exception {

    // sample code with an invalid token (à)
    String code = "IN à;";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // Expect a RuntimeException when trying to read the invalid token (Line 1,
    // column 7)
    assertThrows(RuntimeException.class, () -> {
      while (lexer.yylex() != null) {
        // continue reading tokens until exception
      }
    });
  }

  /**
   * Test a more complex sequence of tokens to ensure the lexer handles multiple
   * token types correctly.
   */
  @Test
  public void testComplexSequence() throws Exception {

    // sample code with multiple tokens
    String code = "Main() { IN a; @ a; }";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // expected tokens in order
    LexicalUnit[] expected = {
        LexicalUnit.MAIN, LexicalUnit.LPAREN, LexicalUnit.RPAREN, LexicalUnit.LBRACK, LexicalUnit.IN,
        LexicalUnit.VARNAME, LexicalUnit.SEMI, LexicalUnit.AROBASE, LexicalUnit.VARNAME, LexicalUnit.SEMI,
        LexicalUnit.RBRACK
    };

    // check each token in sequence
    for (LexicalUnit unit : expected) {
      Symbol token = lexer.yylex();
      assertEquals(unit, token.getType());
    }
  }

  /**
   * Test all keywords and symbols to ensure they are recognized correctly.
   */
  @Test
  public void testAllKeywordsAndSymbols() throws Exception {

    // sample code with all keywords and symbols
    String code = "Main() { } {} ; @ IN := ( ) - + * / If ! || && == <= < LOOP";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // expected tokens in order
    LexicalUnit[] expected = {
        LexicalUnit.MAIN, LexicalUnit.LPAREN, LexicalUnit.RPAREN, LexicalUnit.LBRACK, LexicalUnit.RBRACK,
        LexicalUnit.LBRACK, LexicalUnit.RBRACK,
        LexicalUnit.SEMI, LexicalUnit.AROBASE, LexicalUnit.IN, LexicalUnit.ASSIGN, LexicalUnit.LPAREN,
        LexicalUnit.RPAREN, LexicalUnit.MINUS, LexicalUnit.PLUS, LexicalUnit.TIMES, LexicalUnit.DIVIDE,
        LexicalUnit.IF, LexicalUnit.NEGATION, LexicalUnit.OR,
        LexicalUnit.AND, LexicalUnit.EQUAL, LexicalUnit.SMALEQ, LexicalUnit.SMALLER,
        LexicalUnit.LOOP };

    // check each token in sequence
    for (LexicalUnit unit : expected) {
      assertEquals(unit, lexer.yylex().getType());
    }
  }

  /**
   * Test that identifiers are recognized correctly.
   */
  @Test
  public void testIdentifiers() throws Exception {

    // sample code with identifiers
    String code = "Main() sD a1 b cCc;";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    assertEquals(LexicalUnit.MAIN, lexer.yylex().getType());
    assertEquals(LexicalUnit.LPAREN, lexer.yylex().getType());
    assertEquals(LexicalUnit.RPAREN, lexer.yylex().getType());
    for (int i = 0; i < 3; i++) {
      assertEquals(LexicalUnit.VARNAME, lexer.yylex().getType()); // a1, b, cCc (vid)
    }
  }

  /**
   * Test that the lexer correctly identifies the end of stream (EOS).
   */
  @Test
  public void testEmptyInput() throws Exception {

    // sample code with empty input
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(""));

    // Expect an EOS token immediately
    Symbol token = lexer.yylex();
    assertEquals(LexicalUnit.EOS, token.getType());
  }

  /**
   * Test that an unterminated long comment throws an exception.
   */
  @Test
  public void testUnterminatedLongCommentThrows() throws Exception {

    // sample code with unterminated long comment
    String code = "## this comment never ends";
    LexicalAnalyzer lexer = new LexicalAnalyzer(new StringReader(code));

    // Expect a RuntimeException when trying to read the unterminated comment
    assertThrows(RuntimeException.class, lexer::yylex);
  }

}

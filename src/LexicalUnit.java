/**
 * A terminal symbol, a.k.a. a letter in the grammar.
 */
public enum LexicalUnit {
  /** Main */
  MAIN,
  /** [VarName] */
  VARNAME,
  /** [Number] */
  NUMBER,
  /** <code>;</code> */
  SEMI,
  /** <code>:=</code> */
  ASSIGN,
  /** <code>(</code> */
  LPAREN,
  /** <code>)</code> */
  RPAREN,
  /** <code>-</code> */
  MINUS,
  /** <code>+</code> */
  PLUS,
  /** <code>*</code> */
  TIMES,
  /** <code>/</code> */
  DIVIDE,
  /** <code>@</code> */
  AROBASE,
  /** <code>If</code> */
  IF,
  /** <code>{</code> */
  LBRACK,
  /** <code>}</code> */
  RBRACK,
  /** <code>!</code> */
  NEGATION,
  /** <code>&&</code> */
  AND,
  /** <code>||</code> */
  OR,
  /** <code>==</code> */
  EQUAL,
  /** <code>&lt;<=/code> */
  SMALEQ,
  /** <code>&lt;</code> */
  SMALLER,
  /** <code>LOOP</code> */
  LOOP,
  /** <code>IN</code> */
  IN,
  /** End Of Stream */
  EOS, // End of stream
}

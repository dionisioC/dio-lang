package dio.lang

enum class TokenType {
    OpenParen, //("("),
    CloseParen, //(")"),
    Slash,
    Backslash,
    Asterisk,
    Equals, //("="),
    DoubleEquals, //("="),
    GreaterThan, //(">"),
    LessThan, //(">"),
    GreaterThanEquals, //(">="),
    LessThanEquals, //("<="),
    Colon, //(":"),
    Semicolon, //(";"),
    Plus, //("+"),
    Minus, //("-"),
    Underscore, //("_"),
    Identifier,
    Number,
    String,
    Char,
    Class,
    Interface,
    ShiftLeft,
    ShiftRight,
    Assignment,
    ;
}

data class Token(
    val tokenType: TokenType,
    val line: Int,
    val start: Int,
    val end: Int,
    val value: String,
)
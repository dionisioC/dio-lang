package dio.lang

sealed interface Expression {
//    fun resolve(): Expression
}

data class UnaryExpression(
    val operator: String,
    val right: Expression,
): Expression

data class LiteralExpression<T>(
    val value: T
): Expression

data class BinaryExpression(
    val left: Expression,
    val operator: String,
    val right: Expression,
): Expression {

}

data class AssignExpression(
    val name: String,
    val value: Expression,
): Expression {
}


class Parser(
    private val tokens : List<Token>
) {

    private var currentIndex = 0

    fun parse(): List<Expression> {
        val expressions = mutableListOf<Expression>()
        while (currentIndex < tokens.size - 1) {
            val token = current()
            when (token.tokenType) {
                TokenType.OpenParen -> TODO()
                TokenType.CloseParen -> TODO()
                TokenType.Slash -> TODO()
                TokenType.Backslash -> TODO()
                TokenType.Asterisk -> TODO()
                TokenType.Equals -> TODO()
                TokenType.DoubleEquals -> TODO()
                TokenType.GreaterThan -> TODO()
                TokenType.LessThan -> TODO()
                TokenType.GreaterThanEquals -> TODO()
                TokenType.LessThanEquals -> TODO()
                TokenType.Colon -> TODO()
                TokenType.Semicolon -> TODO()
                TokenType.Plus -> TODO()
                TokenType.Minus -> TODO()
                TokenType.Underscore -> TODO()
                TokenType.Identifier -> TODO()
                TokenType.Number -> TODO()
                TokenType.String -> TODO()
                TokenType.Char -> TODO()
                TokenType.Class -> TODO()
                TokenType.Interface -> TODO()
                TokenType.ShiftLeft -> TODO()
                TokenType.ShiftRight -> TODO()
                TokenType.Assignment -> {
                    // let x = expression
                    advance()
                    val name = current()
                    advance() // =
                    val expression = getExpression()
                    expressions += AssignExpression(name.value, expression)
                }
                TokenType.Function -> TODO()
            }
        }

        return expressions
    }

    private fun getExpression(): Expression {
        val token = next()

        when (token.tokenType) {
            TokenType.OpenParen -> TODO()
            TokenType.CloseParen -> TODO()
            TokenType.Slash -> TODO()
            TokenType.Backslash -> TODO()
            TokenType.Asterisk -> TODO()
            TokenType.Equals -> TODO()
            TokenType.DoubleEquals -> TODO()
            TokenType.GreaterThan -> TODO()
            TokenType.LessThan -> TODO()
            TokenType.GreaterThanEquals -> TODO()
            TokenType.LessThanEquals -> TODO()
            TokenType.Colon -> TODO()
            TokenType.Semicolon -> TODO()
            TokenType.Plus -> TODO()
            TokenType.Minus -> TODO()
            TokenType.Underscore -> TODO()
            TokenType.Identifier -> TODO()
            TokenType.Number -> {
                return LiteralExpression(token.value.toInt())
            }
            TokenType.String -> TODO()
            TokenType.Char -> TODO()
            TokenType.Class -> TODO()
            TokenType.Interface -> TODO()
            TokenType.ShiftLeft -> TODO()
            TokenType.ShiftRight -> TODO()
            TokenType.Assignment -> TODO()
            TokenType.Function -> TODO()
        }
    }

    private fun advance() {
        currentIndex++
    }

    private fun next(): Token {
        advance()
        return current()
    }

    private fun current() = tokens[currentIndex]

}
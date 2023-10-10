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
            if (token.type == TokenType.EOF) {
                break
            } else if (token.type == TokenType.Assignment) {
                // let x = expression
                advance()
                val name = current()
                advance() // =
                val expression = getExpression()
                expressions += AssignExpression(name.value, expression)
            }
            advance()
        }

        return expressions
    }

    private fun getExpression(): Expression {
        val token = next()

        if (token.type == TokenType.Number) {
            return LiteralExpression(token.value.toInt())
        } else if (token.type == TokenType.String) {
            return LiteralExpression(token.value)
        }

        TODO("Need to implement this lol")
    }

    private fun advance() {
        currentIndex++
    }

    private fun next(): Token {
        advance()
        return current()
    }

    private fun peek(): Token {
        return tokens[currentIndex++]
    }

    private fun current() = tokens[currentIndex]

}
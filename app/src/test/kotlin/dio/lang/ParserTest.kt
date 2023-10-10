package dio.lang

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class ParserTest {
    companion object {
        @JvmStatic
        fun `happy path cases`() = Stream.of(
            Arguments.of("let value = 3", AssignExpression("value", LiteralExpression(3))),
            Arguments.of("let value = \"My string\"", AssignExpression("value", LiteralExpression("My string"))),
        )
    }

    @ParameterizedTest
    @MethodSource("happy path cases")
    fun `happy path tests`(source: String, expression: Expression) {
        val lexer = Lexer(source).lex()
        val parser = Parser(lexer).parse()

        assertEquals(expression, parser[0])
    }

}
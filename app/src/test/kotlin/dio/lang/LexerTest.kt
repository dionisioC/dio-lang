/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package dio.lang

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

class LexerTest {

    companion object {
        @JvmStatic
        fun `happy test cases`(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("Standard assignment", "let test = 1", listOf(
                    Token(TokenType.Assignment, 0, 0, 3, "let"),
                    Token(TokenType.Identifier, 0, 4, 8, "test"),
                    Token(TokenType.Equals, 0, 9, 10, "="),
                    Token(TokenType.Number, 0, 11, 12, "1"),
                    Token(TokenType.EOF, 0, 12, 12, ""),
                )),
                Arguments.of("Basic float number", "29_385.33", listOf(
                    Token(TokenType.Number, 0, 0, 9, "29385.33"),
                    Token(TokenType.EOF, 0, 9, 9, ""),
                )),
                Arguments.of("Number or true", "29_385.33 || true", listOf(
                    Token(TokenType.Number, 0, 0, 9, "29385.33"),
                    Token(TokenType.DoubleOr, 0, 10, 12, "||"),
                    Token(TokenType.True, 0, 13, 17, "true"),
                    Token(TokenType.EOF, 0, 17, 17, ""),
                )),
                Arguments.of("Basic int number", "29_385", listOf(
                    Token(TokenType.Number, 0, 0, 6, "29385"),
                    Token(TokenType.EOF, 0, 6, 6, ""),
                )),
                Arguments.of("Basic string", "\"Hello world\"", listOf(
                    Token(TokenType.String, 0, 0, 13, "Hello world"),
                    Token(TokenType.EOF, 0, 13, 13, ""),
                )),
                Arguments.of("String with escapes", "\"Hello \\\"world\\\" is what someone said\"", listOf(
                    Token(TokenType.String, 0, 0, 38, "Hello \"world\" is what someone said"),
                    Token(TokenType.EOF, 0, 38, 38, ""),
                )),
                Arguments.of("Two assignments", "let test = 1\nlet secondTest = test + 1", listOf(
                    Token(TokenType.Assignment, 0, 0, 3, "let"),
                    Token(TokenType.Identifier, 0, 4, 8, "test"),
                    Token(TokenType.Equals, 0, 9, 10, "="),
                    Token(TokenType.Number, 0, 11, 12, "1"),
                    Token(TokenType.Assignment, 1, 13, 16, "let"),
                    Token(TokenType.Identifier, 1, 17, 27, "secondTest"),
                    Token(TokenType.Equals, 1, 28, 29, "="),
                    Token(TokenType.Identifier, 1, 30, 34, "test"),
                    Token(TokenType.Plus, 1, 35, 36, "+"),
                    Token(TokenType.Number, 1, 37, 38, "1"),
                    Token(TokenType.EOF, 1, 38, 38, ""),
                )),
            )
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("happy test cases")
    fun `happy path lexer tests`(name: String, code: String, result: List<Token>) {
        val classUnderTest = Lexer(code)
        assertEquals(result, classUnderTest.lex())
    }
}

package dio.lang

class Lexer(private val string: String) {
    private val tokenList = mutableListOf<Token>()
    private var currentLine = 0
    private var currentIndex = -1
    private var currentIndexInLine = 0

    fun lex(): LexedFile {
        println("Lexing: $string")
        while (currentIndex < string.length) {
            val char = next() ?: break
            println("Dealing with: '$char', index: $currentIndex")
            when (char) {
                in '0'..'9' -> {
                    tokenList += scanNumber()
                }
                ' ' -> { /* Ignore it */ }
                '\t' -> { /* Ignore it */ }
                '\n' -> {
                    currentLine++
                    currentIndexInLine = 0
                }
                '+' -> { tokenList += Token(TokenType.Plus, currentLine, currentIndex, currentIndex + 1, "+") }
                '-' -> { tokenList += Token(TokenType.Minus, currentLine, currentIndex, currentIndex + 1, "-") }
                '*' -> { tokenList += Token(TokenType.Asterisk, currentLine, currentIndex, currentIndex + 1, "*") }
                '/' -> { tokenList += Token(TokenType.Slash, currentLine, currentIndex, currentIndex + 1, "/") }
                '=' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.DoubleEquals, currentLine, currentIndex, currentIndex + 2, "=")
                        advance()
                    } else {
                        tokenList += Token(TokenType.Equals, currentLine, currentIndex, currentIndex + 1, "==")
                    }
                }
                '>' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.GreaterThanEquals, currentLine, currentIndex, currentIndex + 2, "=")
                        advance()
                    } else if (peek() == '>') {
                        tokenList += Token(TokenType.ShiftRight, currentLine, currentIndex, currentIndex + 2, ">>")
                        advance()
                    } else {
                        tokenList += Token(TokenType.GreaterThan, currentLine, currentIndex, currentIndex + 1, "==")
                    }
                }
                '<' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.LessThanEquals, currentLine, currentIndex, currentIndex + 2, "=")
                        advance()
                    } else if (peek() == '<') {
                        tokenList += Token(TokenType.ShiftLeft, currentLine, currentIndex, currentIndex + 2, "<<")
                        advance()
                    } else {
                        tokenList += Token(TokenType.LessThan, currentLine, currentIndex, currentIndex + 1, "==")
                    }
                }
                '_' -> { tokenList += Token(TokenType.Underscore, currentLine, currentIndex, currentIndex + 1, "_") }
                ':' -> { tokenList += Token(TokenType.Colon, currentLine, currentIndex, currentIndex + 1, ":") }
                ';' -> { tokenList += Token(TokenType.Semicolon, currentLine, currentIndex, currentIndex + 1, ";") }
                '(' -> { tokenList += Token(TokenType.OpenParen, currentLine, currentIndex, currentIndex + 1, "(") }
                ')' -> { tokenList += Token(TokenType.CloseParen, currentLine, currentIndex, currentIndex + 1, ")") }
                '"' -> {
                    val token = scanString()
                    tokenList += token
                }
                '\'' -> {
                    val token = scanChar(string, currentIndex + 1)
                    tokenList += token
                }
                else -> { throw IllegalArgumentException("Invalid character at $currentLine:$currentIndexInLine: '$char'") }
            }
        }
        return LexedFile(tokenList)
    }

    private fun scanString (): Token {
        val value = StringBuilder()
        val startIndex = currentIndex
        while (true) {
            // val x = "asdf -> \"hello\"" = asdf -> "hello"
            val char = next()
            if (char == '\\') {
                val nextChar = next() ?: throw IllegalArgumentException("Unfinished escaped string")

                if (nextChar !in STRING_ESCAPED_CHARS) {
                    throw IllegalArgumentException("Unrecognized escaped char")
                } else {
                    value.append(STRING_SUB_CHARS[STRING_ESCAPED_CHARS.indexOf(nextChar)])
                }
            } else if (char != '"') {
                value.append(char)
            } else {
                break
            }
        }
        return Token(TokenType.String, 1, currentIndex - (currentIndex - startIndex), currentIndex, value.toString())
    }

    fun scanNumber(): Token {
        val value = StringBuilder()
        val startIndex = currentIndex
        while (true) {
            val currentChar = current() ?: break

            if (currentChar in VALID_NUMBERS) {
                value.append(currentChar)
            } else if (currentChar in IGNORED_CHARS_IN_NUMBER) {
                // We are just ignoring/consuming chars
            } else {
                break
            }

            advance()
        }

        return Token(TokenType.Number, currentLine, currentIndex - (currentIndex - startIndex), currentIndex, value.toString())
    }

    /**
     * Returns the next character after advancing the pointer. Null if we are at the end
     */
    fun next(): Char? {
        return if (currentIndex+1 >= string.length) {
            null
        } else {
            currentIndex++
            currentIndexInLine++
            string[currentIndex]
        }
    }

    /**
     * Returns the current char without advancing
     */
    fun current(): Char? {
        return if (currentIndex >= string.length) {
            null
        } else {
            string[currentIndex]
        }
    }

    /**
     * Advance char without returning
     */
    private fun advance(number: Int = 1) {
        currentIndex += number
        currentIndexInLine += number
    }

    /**
     * Returns the next char without advancing
     */
    private fun peek(): Char? {
        return if (currentIndex+1 >= string.length) {
            null
        } else {
            string[currentIndex+1]
        }
    }

}
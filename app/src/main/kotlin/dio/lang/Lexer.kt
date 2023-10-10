package dio.lang

class Lexer(private val string: String) {
    private val tokenList = mutableListOf<Token>()
    private var currentLine = 0
    private var currentIndex = 0
    private var currentIndexInLine = 0

    fun lex(): List<Token> {
        println("Lexing: $string")
        while (currentIndex < string.length) {
            val char = current() ?: break
            println("Dealing with: '$char', index: $currentIndex")
            when (char) {
                in '0'..'9' -> {
                    tokenList += scanNumber()
                }
                in 'a' .. 'z', in 'A' .. 'Z' -> {
                    tokenList += scanWord()
                }
                ' ' -> { /* Ignore it */ advance() }
                '\t' -> { /* Ignore it */ advance() }
                '\r' -> { /* Ignore it */ advance() }
                '\n' -> {
                    currentLine++
                    currentIndexInLine = 0
                    advance()
                }
                '+' -> { tokenList += Token(TokenType.Plus, currentLine, currentIndex, currentIndex + 1, "+"); advance() }
                '-' -> { tokenList += Token(TokenType.Minus, currentLine, currentIndex, currentIndex + 1, "-"); advance() }
                '*' -> { tokenList += Token(TokenType.Asterisk, currentLine, currentIndex, currentIndex + 1, "*"); advance() }
                '/' -> { tokenList += Token(TokenType.Slash, currentLine, currentIndex, currentIndex + 1, "/"); advance() }
                '=' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.DoubleEquals, currentLine, currentIndex, currentIndex + 2, "==")
                        advance(2)
                    } else {
                        tokenList += Token(TokenType.Equals, currentLine, currentIndex, currentIndex + 1, "=")
                        advance()
                    }
                }
                '>' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.GreaterThanEquals, currentLine, currentIndex, currentIndex + 2, "=")
                        advance(2)
                    } else if (peek() == '>') {
                        tokenList += Token(TokenType.ShiftRight, currentLine, currentIndex, currentIndex + 2, ">>")
                        advance(2)
                    } else {
                        tokenList += Token(TokenType.GreaterThan, currentLine, currentIndex, currentIndex + 1, ">")
                        advance()
                    }
                }
                '<' -> {
                    if (peek() == '=') {
                        tokenList += Token(TokenType.LessThanEquals, currentLine, currentIndex, currentIndex + 2, "=")
                        advance(2)
                    } else if (peek() == '<') {
                        tokenList += Token(TokenType.ShiftLeft, currentLine, currentIndex, currentIndex + 2, "<<")
                        advance(2)
                    } else {
                        tokenList += Token(TokenType.LessThan, currentLine, currentIndex, currentIndex + 1, "==")
                        advance()
                    }
                }
                '_' -> { tokenList += Token(TokenType.Underscore, currentLine, currentIndex, currentIndex + 1, "_"); advance() }
                ':' -> { tokenList += Token(TokenType.Colon, currentLine, currentIndex, currentIndex + 1, ":"); advance() }
                ';' -> { tokenList += Token(TokenType.Semicolon, currentLine, currentIndex, currentIndex + 1, ";"); advance() }
                '(' -> { tokenList += Token(TokenType.OpenParen, currentLine, currentIndex, currentIndex + 1, "("); advance() }
                ')' -> { tokenList += Token(TokenType.CloseParen, currentLine, currentIndex, currentIndex + 1, ")"); advance() }
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
        tokenList += Token(TokenType.EOF, currentLine, currentIndex, currentIndex, "")
        return tokenList
    }

    private fun scanWord(): Token {
        val value = StringBuilder()
        val startIndex = currentIndex
        while (current().isIdentifierChar()) {
            value.append(current())
            advance()
        }

        val isKeyword = value.toString() in KEYWORDS
        val type = if (isKeyword) value.toString().toTokenType() else TokenType.Identifier

        return Token(type, currentLine, currentIndex - (currentIndex - startIndex), currentIndex, value.toString())
    }

    private fun scanString(): Token {
        val value = StringBuilder()
        val startIndex = currentIndexInLine
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
                advance()
                break
            }
        }
        return Token(TokenType.String, currentLine, currentIndexInLine - (currentIndexInLine - startIndex), currentIndexInLine, value.toString())
    }

    private fun scanNumber(): Token {
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
    private fun next(): Char? {
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
    private fun current(): Char? {
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
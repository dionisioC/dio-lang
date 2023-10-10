package dio.lang

val KEYWORDS = setOf(
    "let",
    "class",
    "trait",
    "true",
    "false",
    "fn",
)

private val identifierChars: Set<Char> = ('a'..'z').toSet() + ('A' .. 'Z').toSet() + "_-".toSet()
fun Char?.isIdentifierChar(): Boolean {
    return this in identifierChars
}

fun String.toTokenType() = when (this) {
    "class" -> { TokenType.Class }
    "let" -> { TokenType.Assignment }
    "true" -> { TokenType.True }
    "false" -> { TokenType.False }
    "trait" -> { TokenType.Trait }
    "fn" -> { TokenType.Function }
    else -> { throw IllegalArgumentException("Unrecognized word") }
}
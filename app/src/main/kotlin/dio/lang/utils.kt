package dio.lang

val KEYWORDS = setOf(
    "let",
    "class",
    "fn",
)

private val identifierChars: Set<Char> = ('a'..'z').toSet() + ('A' .. 'Z').toSet() + "_-".toSet()
fun Char?.isIdentifierChar(): Boolean {
    return this in identifierChars
}

fun String.toTokenType() = when (this) {
    "class" -> { TokenType.Class }
    "let" -> { TokenType.Assignment }
    "fn" -> { TokenType.Function }
    else -> { throw IllegalArgumentException("Unrecognized word") }
}
import problems.SeiveOfErotasthenese

fun main() {
    val primeNumbers = SeiveOfErotasthenese.findPrimesInRange(17)
    println("Prime Numbers: ${primeNumbers.joinToString()}")
}
import problems.SieveOfErotasthenese

fun main() {
    val primeFactors = SieveOfErotasthenese.factorize(20)
    println("Prime Factors: ${primeFactors.joinToString()}")
}
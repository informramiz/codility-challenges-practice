import problems.PrimeAndCompositeNumbers

fun main() {
    val n = 36
    val divisorsCount = PrimeAndCompositeNumbers.countDivisors(n)
    println("maxSum: $divisorsCount")
}
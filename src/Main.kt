import problems.SieveOfErotasthenese

fun main() {
    val A = arrayOf(3, 1, 2, 3, 6)
//    //let's test individual methods first to make sure they are right
//    val count = SieveOfErotasthenese.countNumbers(A)
//    val divisorsCount = SieveOfErotasthenese.countDivisors(1, count)
    val dCounts = SieveOfErotasthenese.countNonDivisors(A)
    println("Divisors Count: ${dCounts.joinToString()}")
}
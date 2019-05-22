import problems.SieveOfErotasthenese

fun main() {
    val P = intArrayOf(1, 4, 16)
    val Q = intArrayOf(26, 10, 20)
    val N = 26
    val semiPrimesCountForEachRangePQ = SieveOfErotasthenese.countSemiPrimes(N, P, Q)
    println("Prime Factors: ${semiPrimesCountForEachRangePQ.joinToString()}")
}
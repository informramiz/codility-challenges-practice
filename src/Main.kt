import problems.PrimeAndCompositeNumbers

fun main() {
//    val A = intArrayOf(1, 2, 1, 3, 1, 4, 1, 2)
    val A = intArrayOf(1, 2, 3, 4, 3, 4, 1, 2, 3, 4, 6, 2)
    val maxBlocks = PrimeAndCompositeNumbers.peaks(A)
    println("Maximum Blocks: ${maxBlocks}")
}
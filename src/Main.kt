import problems.PrimeAndCompositeNumbers

fun main() {
//    val A = intArrayOf(1, 2, 1, 3, 1, 4, 1, 2)
    val A = intArrayOf(1, 5, 3, 4, 3, 4, 1, 2, 3, 4, 6, 2)
//    val A = intArrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 1)
    val maxSetFlags = PrimeAndCompositeNumbers.findMaxSettableFlags(A)
    println("Maximum Flags: ${maxSetFlags}")
}
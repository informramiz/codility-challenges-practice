import problems.MaxSlice

fun main() {
    val A = arrayOf(3, 2, -6, 4, 0)
    val maxSum = MaxSlice.findMaxSliceSumWithNegativePossibleValue(A)
    println("maxSum: $maxSum")
}
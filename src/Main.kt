import problems.MaxSlice

fun main() {
    val A = arrayOf(3, 2, 6, -1, 4, 5, -1, 2)
    val maxSum = MaxSlice.maxDoubleSliceSum(A)
    println("maxSum: $maxSum")
}
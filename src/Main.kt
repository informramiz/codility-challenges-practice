import problems.MaxSlice

fun main() {
    val A = arrayOf(5, -7, 3, 5, -2, 4, -1)
    val maxSum = MaxSlice.findMaxSliceSum(A)
    println("max slice sum: $maxSum")
}
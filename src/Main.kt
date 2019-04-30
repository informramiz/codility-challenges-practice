import problems.MaxSlice

fun main() {
//    val A = arrayOf(3, 2, 6, -1, 4, 5, -1, 2)
//    val A = arrayOf(5, 17, 0, 3)
    val A = arrayOf(0, 10, -5, -2, 0)
    val maxSum = MaxSlice.maxDoubleSliceSum(A)
    println("maxSum: $maxSum")
}
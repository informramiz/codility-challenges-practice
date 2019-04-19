import problems.MaxSlice

fun main() {
    val A = arrayOf(23171, 21011, 21123, 21366, 21013, 21367)
    val maxProfilt = MaxSlice.findMaximumProfit(A)
    println("max profit: $maxProfilt")
}
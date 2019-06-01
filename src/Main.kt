import problems.FibonacciNumbers

fun main() {
//    val A = arrayOf(0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0)
    val A = arrayOf(1, 1, 0, 0, 0)
    val jumps = FibonacciNumbers.countMinJumps(A)
    print(jumps)
}
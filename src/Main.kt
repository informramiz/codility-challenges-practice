import problems.FibonacciNumbers

fun main() {
    val A = arrayOf(1, 1, 0, 0, 0)
    val jumps = FibonacciNumbers.countMinJumps(A)
    print(jumps)
}
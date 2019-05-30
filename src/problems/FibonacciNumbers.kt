package problems

object FibonacciNumbers {
    fun findNthFibonacciNumber(n: Int): Int {
        val fib = Array(n+1) {0}
        fib[0] = 0
        fib[1] = 1
        for (i in 2..n) {
            fib[i] = fib[i-1] + fib[i-2]
        }

        return fib[n]
    }
}
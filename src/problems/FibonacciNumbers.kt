package problems

/**
 * https://codility.com/media/train/11-Fibonacci.pdf
 */
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

    fun findNthFibonacciNumberOptimized(n: Int): Long {
        var secondLastFibonacci = 0L
        var lastFibonacci = 1L
        for (i in 2..n) {
            val nextFibonacci = lastFibonacci + secondLastFibonacci
            //now this next nextFibonacci will become last fibonacci for next iteration
            //and similarly LastFibonacci will become second last
            secondLastFibonacci = lastFibonacci
            lastFibonacci = nextFibonacci
        }

        return lastFibonacci
    }

    /**
     * Link: https://app.codility.com/programmers/lessons/13-fibonacci_numbers/ladder/
     * Ladder
     * Count the number of different ways of climbing to the top of a ladder.
     *
     * * You have to climb up a ladder. The ladder has exactly N rungs, numbered from 1 to N. With each step, you can ascend by one or two rungs. More precisely:
     * with your first step you can stand on rung 1 or 2,
     * if you are on rung K, you can move to rungs K + 1 or K + 2,
     * finally you have to stand on rung N.
     * Your task is to count the number of different ways of climbing to the top of the ladder.
     * For example, given N = 4, you have five different ways of climbing, ascending by:
     * 1, 1, 1 and 1 rung,
     * 1, 1 and 2 rungs,
     * 1, 2 and 1 rung,
     * 2, 1 and 1 rungs, and
     * 2 and 2 rungs.
     * Given N = 5, you have eight different ways of climbing, ascending by:
     * 1, 1, 1, 1 and 1 rung,
     * 1, 1, 1 and 2 rungs,
     * 1, 1, 2 and 1 rung,
     * 1, 2, 1 and 1 rung,
     * 1, 2 and 2 rungs,
     * 2, 1, 1 and 1 rungs,
     * 2, 1 and 2 rungs, and
     * 2, 2 and 1 rung.
     * The number of different ways can be very large, so it is sufficient to return the result modulo 2P, for a given integer P.
     * Write a function:
     * class Solution { public int[] solution(int[] A, int[] B); }
     * that, given two non-empty arrays A and B of L integers, returns an array consisting of L integers specifying the consecutive answers; position I should contain the number of different ways of climbing the ladder with A[I] rungs modulo 2B[I].
     * For example, given L = 5 and:
     * A[0] = 4   B[0] = 3
     * A[1] = 4   B[1] = 2
     * A[2] = 5   B[2] = 4
     * A[3] = 5   B[3] = 3
     * A[4] = 1   B[4] = 1
     * the function should return the sequence [5, 1, 8, 0, 1], as explained above.
     * Write an efficient algorithm for the following assumptions:
     * L is an integer within the range [1..50,000];
     * each element of array A is an integer within the range [1..L];
     * each element of array B is an integer within the range [1..30].
     */
    fun ladder(A: Array<Int>, B: Array<Int>): Array<Int> {
        val count = Array(A.size) {0}
        for (i in 0 until A.size) {
            //number of compositions of 1s and 2s that sum up to a total n is F(n+1) Fibonacci number
            //so F(n+1)-th Fibonacci number will give us total possible ways of climbing a ladder of length N
            //using steps of either 1 or 2
            //reference: https://en.wikipedia.org/wiki/Fibonacci_number
            //look at Mathematics section of above link
            //as we only need to return totalWaysOfClimbing % 2^B[i] according to problem statement so
            count[i] =  findNthFibonacciNumberWithModulo(A[i]+1, BitwiseOperations.powerOf2(B[i]))
        }

        return count
    }

    fun findNthFibonacciNumberWithModulo(n: Int, modulo: Int): Int {
        var secondLastFibonacci = 0
        var lastFibonacci = 1
        for (i in 2..n) {
            val nextFibonacci = BitwiseOperations.mod(lastFibonacci + secondLastFibonacci, modulo)
            //now this next nextFibonacci will become last fibonacci for next iteration
            //and similarly LastFibonacci will become second last
            secondLastFibonacci = lastFibonacci
            lastFibonacci = nextFibonacci
        }

        return lastFibonacci
    }
}
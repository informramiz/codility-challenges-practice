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
        //EXPLANATION:
        // number of compositions of 1s and 2s that sum up to a total n is F(n+1) Fibonacci number
        //so F(n+1)-th Fibonacci number will give us total possible ways of climbing a ladder of length N
        //using steps of either 1 or 2
        //reference: https://en.wikipedia.org/wiki/Fibonacci_number
        //look at Mathematics section of above link

        val maxA = A.max()!!
        val maxB = B.max()!!
        //We need to find Fibonacci numbers up to (maxA+1) to cover all possible lengths
        //as number of compositions of 1s and 2s that sum up to a total n is F(n+1)
        //we also need to say inside module (2^maxB) as required in question to avoid data overflow
        val fibonacciNumbers = findNFibonacciNumbersWithModulo(maxA + 1, BitwiseOperations.powerOf2(maxB))

        val count = Array(A.size) {0}
        for (i in 0 until A.size) {
            //as number of compositions of 1s and 2s that sum up to a total n is F(n+1)
            //as we only need to return totalWaysOfClimbing % 2^B[i] according to problem statement so
            count[i] =  BitwiseOperations.mod(fibonacciNumbers[A[i]+1], BitwiseOperations.powerOf2(B[i]))
        }

        return count
    }

    /**
     * Returns first n Fibonacci numbers while making sure their value never exceeds module
     */
    fun findNFibonacciNumbersWithModulo(n: Int, modulo: Int): Array<Int> {
        val fib = Array(n+1) {0}
        fib[0] = 0
        fib[1] = 1
        for (i in 2..n) {
            fib[i] = BitwiseOperations.mod(fib[i-1] + fib[i-2], modulo)
        }

        return fib
    }

    fun countMinJumps(A: Array<Int>): Int {
        val fib = getFibonacciNumbersBelowExcept0(A.size).reversed()

        var jumpsCount = 0
        var i = -1
        while (i < A.size) {
            var didJump = false

            for (f in fib) {
                val nextPosition = i + f

                if (nextPosition == A.size) {
                    //we reached the other bank
                    return jumpsCount + 1
                } else if (nextPosition >= 0 && nextPosition < A.size && A[nextPosition] == 1) {
                    //we can jump from position i to i+f using current fibonacci length f
                    i = nextPosition
                    jumpsCount++
                    didJump = true
                    break
                }
            }

            //check if a jump was made or not. If not then that means we can't jump from here onward
            if (!didJump) {
                return -1
            }
        }

        return -1
    }

    /**
     * Returns Fibonacci numbers until their value exceeds n
     */
    private fun getFibonacciNumbersBelowExcept0(n: Int): List<Int> {
        val fib = mutableListOf<Int>()
        fib.add(1)
        fib.add(2)

        while (fib.last() <= n) {
            fib.add(fib[fib.lastIndex] + fib[fib.lastIndex-1])
        }

        return fib
    }
}
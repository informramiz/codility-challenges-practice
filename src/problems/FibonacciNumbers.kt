package problems

import java.util.*

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
        //get all the Fibonacci numbers until the last Fibonacci number exceeds A.size
        val fib = getFibonacciNumbersBelowExcept0(A.size).reversed()

        //as we need to find minimum jumps possible and there can be multiple points which can
        //lead to the end so we have to go through all those points. We will keep all those points
        //in queue and use a breadth first search algorithm to pick (BFS). The point the reaches to
        //the end first will be the one with minimum jumps
        val queue = LinkedList<Point>()
        //as initially the frog is at position -1 and jumpsCount 0 so
        queue.add(Point(-1, 0))

        while (!queue.isEmpty()) {
            //get the next candidate point
            val point = queue.remove()

            //for each fibonacci number with value with which a jump is possible, add them to queue
            for (f in fib) {
                val nextPosition = point.position + f

                if (nextPosition == A.size) {
                    //we reached the other bank so just return total jump count
                    return point.jumpCount + 1
                } else if (nextPosition >= 0 && nextPosition < A.size && A[nextPosition] == 1) {
                    //we can jump from position i to nextPosition using current fibonacci length f
                    queue.add(Point(nextPosition, point.jumpCount + 1))
                    //because we have discovered point A[nextPosition] so we mark it as discovered by assigning 0
                    //Why? Any point that is going to discover/lead to A[nextPosition] after it is already
                    //discovered is obviously taking longer path
                    // i.e: because we are using breadth first search
                    //and A[nextPosition] appeared first in breadth and any point that later discovers A[nextPosition]
                    //is in bottom breadth so that path is definitely not shorter.
                    A[nextPosition] = 0
                }
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

    private data class Point(val position: Int, val jumpCount: Int)
}
object CountingElements {
    /**
     * Problem: You are given an integer m (1 <= m <= 1 000 000) and two non-empty, zero-indexed
     * arrays A and B of n integers, a0, a1, . . . , an−1 and b0, b1, . . . , bn−1 respectively (0 <= ai
     * , bi <= m).
     * The goal is to check whether there is a swap operation which can be performed on these
     * arrays in such a way that the sum of elements in array A equals the sum of elements in
     * array B after the swap. By swap operation we mean picking one element from array A and
     * one element from array B and exchanging them.
     */
    fun checkIfSwapMakeTotalsEqual(A: Array<Int>, B: Array<Int>, m: Int): Boolean {
        /* let's say the elements that needs to be swapped are (ai, bi) from each array respectively then
        * Bsum + ai - bi = Asum + bi - ai
        * -> ai = bi - ( (Bsum - Asum)/2 )
        * ai = bi - d/2
        *
        * so given an element bi from array B we will get corresponding ai value that needs to be present
        * in A and needs to be swapped. Note: This ai also must obey 0 <= ai <= m condition as given in question
        */

        val sumA = A.sum()
        val sumB = B.sum()
        val d = sumB - sumA

        //d needs to be even so that d/2 is an integer because both A and B arrays are integer arrays
        if (d % 2 != 0) {
            return false
        }

        //because m is in range [1, 1,000,000]) so we can count its elements using a count array
        val elementsCountInA = countArrayElements(A, m)

        //now find ai for each bi and check if it is in [0, m] and also present in A (elementsCountInA[ai] > 0)
        B.forEach { bi ->
            val ai =  bi - d/2
            if (ai in 0..m && elementsCountInA[ai] > 0) {
                return true
            }
        }

        return false
    }

    private fun countArrayElements(A: Array<Int>, m: Int): Array<Int> {
        val count = Array(m + 1) {0}
        A.forEach { count[it]++ }
        return count
    }

    /*
    * A non-empty array A consisting of N integers is given.
    * A permutation is a sequence containing each element from 1 to N once, and only once.
    * For example, array A such that:
    * A[0] = 4
    * A[1] = 1
    * A[2] = 3
    * A[3] = 2
    * is a permutation, but array A such that:
    * A[0] = 4
    * A[1] = 1
    * A[2] = 3
    * is not a permutation, because value 2 is missing.
    * The goal is to check whether array A is a permutation.
    * Write a function:
    * class Solution { public int solution(int[] A); }
    * that, given an array A, returns 1 if array A is a permutation and 0 if it is not.
    * For example, given array A such that:
    * A[0] = 4
    * A[1] = 1
    * A[2] = 3
    * A[3] = 2
    * the function should return 1.
    * Given array A such that:
    * A[0] = 4
    * A[1] = 1
    * A[2] = 3
    * the function should return 0.
    * Write an efficient algorithm for the following assumptions:
    * N is an integer within the range [1..100,000];
    * each element of array A is an integer within the range [1..1,000,000,000].
    */
    fun checkIfPermutation(A: Array<Int>): Int {
        if (A.isEmpty()) return 1

        //arithmetic series sum
        val maxA = A.size
        val expectedSum = A.size * (1 + maxA) / 2
        val actualSum = A.sum()

        val isSumFine = expectedSum == actualSum
        if (!isSumFine) {
            return 0
        }

        val areTotalElementsFine = A.size == maxA
        if (!areTotalElementsFine) {
            return 0
        }

        val counter = Array(A.size + 1) {0}
        var distinctElementsCount = 0
        A.forEach { value ->
            if (value >= counter.size) {
                return 0
            }

            if (counter[value] == 0) {
                distinctElementsCount++
                counter[value]++
            } else {
                return 0
            }
        }

        return if (distinctElementsCount == A.size) {
            1
        } else {
            0
        }
    }

    fun factorial(n: Int): Int {
        var total = 1
        for (i in 1..n) {
            total *= i
        }
        return total
    }

    fun product(A: Array<Int>): Int {
        var total = 1
        A.forEach {
            total *= it
        }
        return total
    }
}
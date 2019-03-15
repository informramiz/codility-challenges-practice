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

    /*
    *A small frog wants to get to the other side of a river. The frog is initially located on one bank of the river (position 0) and wants to get to the opposite bank (position X+1). Leaves fall from a tree onto the surface of the river.
    *You are given an array A consisting of N integers representing the falling leaves. A[K] represents the position where one leaf falls at time K, measured in seconds.
    *The goal is to find the earliest time when the frog can jump to the other side of the river. The frog can cross only when leaves appear at every position across the river from 1 to X (that is, we want to find the earliest moment when all the positions from 1 to X are covered by leaves). You may assume that the speed of the current in the river is negligibly small, i.e. the leaves do not change their positions once they fall in the river.
    *For example, you are given integer X = 5 and array A such that:
    *
    *A[0] = 1
    *A[1] = 3
    *A[2] = 1
    *A[3] = 4
    *A[4] = 2
    *A[5] = 3
    *A[6] = 5
    *A[7] = 4
    *In second 6, a leaf falls into position 5. This is the earliest time when leaves appear in every position across the river.
    *
    *Write a function:
    *
    *fun solution(X: Int, A: IntArray): Int
    *
    *that, given a non-empty array A consisting of N integers and integer X, returns the earliest time when the frog can jump to the other side of the river.
    *
    *If the frog is never able to jump to the other side of the river, the function should return −1.
    *
    *For example, given X = 5 and array A such that:
    *
    *A[0] = 1
    *A[1] = 3
    *A[2] = 1
    *A[3] = 4
    *A[4] = 2
    *A[5] = 3
    *A[6] = 5
    *A[7] = 4
    *the function should return 6, as explained above.
    *
    *Write an efficient algorithm for the following assumptions:
    *
    *N and X are integers within the range [1..100,000];
    *each element of array A is an integer within the range [1..X].
    */
    fun frogRiverOne(X: Int, A: Array<Int>): Int {
        val counter = Array(X + 1) {0}
        var leavesNeeded = X
        A.forEachIndexed { index, value ->
            if (counter[value] == 0) {
                counter[value]++
                leavesNeeded--
            }

            if (leavesNeeded == 0) {
                return index
            }
        }

        return -1
    }

    /*
    * MaxCounters:
    *
    * Calculate the values of counters after applying all alternating operations: increase counter by 1; set value of all counters to current maximum.
    * Programming language:
    * You are given N counters, initially set to 0, and you have two possible operations on them:
    *
    * increase(X) − counter X is increased by 1,
    * max counter − all counters are set to the maximum value of any counter.
    * A non-empty array A of M integers is given. This array represents consecutive operations:
    *
    * if A[K] = X, such that 1 ≤ X ≤ N, then operation K is increase(X),
    * if A[K] = N + 1 then operation K is max counter.
    * For example, given integer N = 5 and array A such that:
    *
    * A[0] = 3
    * A[1] = 4
    * A[2] = 4
    * A[3] = 6
    * A[4] = 1
    * A[5] = 4
    * A[6] = 4
    * the values of the counters after each consecutive operation will be:
    *
    * (0, 0, 1, 0, 0)
    * (0, 0, 1, 1, 0)
    * (0, 0, 1, 2, 0)
    * (2, 2, 2, 2, 2)
    * (3, 2, 2, 2, 2)
    * (3, 2, 2, 3, 2)
    * (3, 2, 2, 4, 2)
    * The goal is to calculate the value of every counter after all operations.
    *
    * Write a function:
    *
    * class Solution { public int[] solution(int N, int[] A); }
    *
    * that, given an integer N and a non-empty array A consisting of M integers, returns a sequence of integers representing the values of the counters.
    *
    * Result array should be returned as an array of integers.
    *
    * For example, given:
    *
    * A[0] = 3
    * A[1] = 4
    * A[2] = 4
    * A[3] = 6
    * A[4] = 1
    * A[5] = 4
    * A[6] = 4
    * the function should return [3, 2, 2, 4, 2], as explained above.
    *
    * Write an efficient algorithm for the following assumptions:
    *
    * N and M are integers within the range [1..100,000];
    * each element of array A is an integer within the range [1..N + 1].
    */
    fun maxCounters(N: Int, A: IntArray): IntArray {
        val counters = Array(N) {0}

        var currentMaxValue = 0
        var lastMaxValue = 0

        A.forEach { value ->
            if (value in 1..N) { //increase operation
                val counterIndex = value - 1
                counters[counterIndex] = Math.max(counters[counterIndex], lastMaxValue)
                counters[counterIndex]++
                //keep a track of the max counter value
                currentMaxValue = Math.max(counters[counterIndex], currentMaxValue)
            } else if (value == N + 1) { //reset all counters to max counter value operation
                lastMaxValue = currentMaxValue
            }
        }

        //check if there are any counters for which reset to max counter operation is still pending
        counters.forEachIndexed { index, value ->
            counters[index] = Math.max(lastMaxValue, value)
        }
        return counters.toIntArray()
    }

    /**
     * ----MissingInteger----
     * Find the smallest positive integer that does not occur in a given sequence.
     * This is a demo task.
     *
     * Write a function:
     *
     * fun solution(A: IntArray): Int
     *
     * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
     *
     * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
     *
     * Given A = [1, 2, 3], the function should return 4.
     *
     * Given A = [−1, −3], the function should return 1.
     *
     * Write an efficient algorithm for the following assumptions:
     *
     * N is an integer within the range [1..100,000];
     * each element of array A is an integer within the range [−1,000,000..1,000,000].
     */
    fun findMissingInteger(A: IntArray): Int {
        val counter = Array(1_000_000 + 1) {0}
        //required number should be greater than 0, so ignore number 0
        counter[0] = 1

        A.forEach { value ->
            if (value > 0) {
                counter[value]++
            }
        }

        counter.forEachIndexed { index, value ->
            if (value == 0) {
                return index
            }
        }

        return 1
    }
}
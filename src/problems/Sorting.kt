package problems

import extensions.negativeCount
import extensions.swap
import extensions.toInt

//https://codility.com/media/train/4-Sorting.pdf
object Sorting {
    fun selectionSort(A: Array<Int>): Array<Int> {
        for (i in 0 until A.size-1) {
            val minimumElementIndex = findIndexOfMinimum(A, i+1)
            A.swap(i, minimumElementIndex)
        }

        return A
    }

    private fun findIndexOfMinimum(A: Array<Int>, startIndex: Int): Int {
        var min = A[startIndex]
        var minIndex = startIndex
        for (i in startIndex+1 until A.size) {
            if (min > A[i]) {
                min = A[i]
                minIndex = i
            }
        }

        return minIndex
    }

    /**
     * Counting sort (time = O(n + k)) is only used when we know the
     * the numbers range [0...k]
     * NOTE: This program does not support negative numbers, we can add
     * negative numbers support by either shifting all elements some number so that
     * the minimum negative number becomes 0 or we can count negative numbers in a separate array
     */
    fun countingSort(A: Array<Int>, maximumPossibleNumberInArray: Int): Array<Int> {
        //make an array for the possible number range
        val counter = Array(maximumPossibleNumberInArray + 1) {0}

        //count all the numbers in array
        A.forEach { value ->
            counter[value]++
        }

        //for all the possible numbers in range [0...k]
        //go through each if its count is greater than 0 then add to array A
        //that many times
        var indexA = 0
        counter.forEachIndexed { number, count ->
            for (i in 0 until count) {
                A[indexA] = number
                indexA++
            }
        }

        return A
    }

    fun mergeSort(A: Array<Int>) {
        _mergeSort(A,0, A.size)
    }

    private fun _mergeSort(A: Array<Int>, start: Int, end: Int) {
        if ((end - start) < 2) { //size = 1, so already sorted
            return
        }

        val middle = (start + end) / 2
        _mergeSort(A, start, middle)
        _mergeSort(A, middle, end)
        mergeArray(A, start, middle, end)
    }

    private fun mergeArray(destination: Array<Int>, start: Int, middle: Int, end: Int) {
        var start1 = start
        var start2 = middle
        val copy = destination.copyOf()

        for (i in start until end) {
            if (start1 < middle && (start2 >= end || copy[start1] <= copy[start2])) {
                destination[i] = copy[start1]
                start1++
            } else {
                destination[i] = copy[start2]
                start2++
            }
        }
    }

    /**
     * You are given a zero-indexed array A consisting of n > 0 integers; you must return
     * the number of unique values in array A.
     */
    fun countDistinctNumbers(A: Array<Int>): Int {
        /**
         * First, sort array A; similar values will then be next to each other.
         * Finally, just count the number of distinct pairs in adjacent cells.
         */
        if (A.isEmpty()) {
            return 0
        }

        A.sort()
        var distinctCount = 1
        for (i in 0 until A.size-1) {
            if (A[i] != A[i + 1]) {
                distinctCount++
            }
        }

        return distinctCount
    }

    /**
     * https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/
     *
     * MaxProductOfThree
     * Maximize A[P] * A[Q] * A[R] for any triplet (P, Q, R).
     * Note: Array size n is in range [3..100_000] and
     * A[i] is in range [−1,000..1,000].
     */
    fun maxProductOfThree(A: Array<Int>): Int {
        A.sort()
        //the biggest product can be achieved by following 2 ways
        //1. 3 biggest positive numbers
        //2. 1 biggest positive number, 2 biggest (in terms of absolute value) negative numbers because
        // multiplying 2 negative numbers will result in positive number

        //first check the the case-1 (biggest positive numbers)
        val maxProduct = A[A.lastIndex] * A[A.lastIndex-1] * A[A.lastIndex-2]
        //as array is sorted so biggest (in terms of absolute value) will be on start of array (A[0], A[1])
        val maxProductWith2NegativeNumbers = A[A.lastIndex] * A[0] * A[1]
        return Math.max(maxProduct, maxProductWith2NegativeNumbers)
    }

    /**
     * https://app.codility.com/programmers/lessons/6-sorting/triangle/
     * Triangle
     * Determine whether a triangle can be built from a given set of edges.
     */
    fun isTriangularTripletPresent(A: Array<Int>): Int {
        if (A.size < 3) return 0

        A.sort()
        for (i in 0 until A.size - 2) {
            //after sorting numbers have to be adjacent to fulfill triplet condition
            //otherwise there value distance will be greater enough to violate at least 1 condition
            val p = A[i]
            val q = A[i + 1]
            val r = A[i + 2]

            if (isTriangularTriplet(p, q, r)) {
                return 1
            }
        }

        return 0
    }

    private fun isTriangularTriplet(p: Int, q: Int, r: Int): Boolean {
        val condition1 = p.toLong() + q > r
        val condition2 = q.toLong() + r > p
        val condition3 = r.toLong() + p > q

        return condition1 && condition2 && condition3
    }
}
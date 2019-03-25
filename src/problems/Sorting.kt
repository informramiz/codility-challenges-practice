package problems

import extensions.swap

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
}
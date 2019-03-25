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
}
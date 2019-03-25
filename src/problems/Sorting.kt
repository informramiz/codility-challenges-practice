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
}
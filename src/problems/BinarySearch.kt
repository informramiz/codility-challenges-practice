package problems

/**
 * https://codility.com/media/train/12-BinarySearch.pdf
 */
object BinarySearch {
    fun findNumber(A: Array<Int>, key: Int): Int {
        var start = 0
        var end = A.lastIndex
        while (start <= end) {
            val mid = (start + end) / 2
            if (A[mid] == key) {
                return mid
            } else if (A[mid] > key) {
                //the key is in range [start, mid)
                end = mid - 1
            } else { //A[mid] < key
                //the key is in range (mid, end]
                start = mid + 1
            }
        }

        //we did not find a match for the key
        return -1
    }

    /**
     * Given number key, returns the maximum number n that is less than or equal to key (i.e, n <= key)
     */
    fun findMaxNumberEqualOrLessThanKey(A: Array<Int>, key: Int): Int {
        var start = 0
        var end = A.lastIndex
        var result = -1
        while (start <= end) {
            val mid = (start + end) / 2
            if (A[mid] <= key) {
                result = mid
                start = mid + 1
            } else { //A[mid] > key, we are interested in range [start, mid)
                end = mid - 1
            }
        }

        //we did not find a match for the key
        return result
    }

    /**
     * https://codility.com/media/train/12-BinarySearch.pdf
     *
     * Problem:
     * You are given n binary values x0, x1, . . . , xn−1, such that xi ∈ {0, 1}. This array represents holes in a
     * roof (1 is a hole). You are also given k boards of the same size. The goal is to choose the optimal (minimal)
     * size of the boards that allows all the holes to be covered by boards.
     *
     * Solution:
     * We can search for a the right size of board in range [1, n] using binary search and for each size k we
     * can check if it covers all the holes (nlgn)
     *
     * @param A, Ai ∈ {0, 1}, represents holes in a roof (1 is a hole)
     * @param maxBoardsCount, boards of count
     *
     * @return min/optimal size of the boards (count <= maxBoardsCount) needed to cover all the holes in the roof
     */
    fun findBoardSize(A: Array<Int>, maxBoardsCount: Int): Int {
        var result = -1
        //search for right board size in range [0, A.size] using binary search
        var start = 0
        var end = A.lastIndex
        while (start <= end) {
            val mid = (start + end) / 2
            if (countBoardsOf(mid, A) <= maxBoardsCount) {
                end = mid - 1
                result = end
            } else {
                start = mid + 1
            }
        }
        return result
    }

    private fun countBoardsOf(boardSize: Int, A: Array<Int>): Int {
        var boardsCount = 0
        var lastCoveredIndex = -1
        for (i in 0 until A.size) {
            if (A[i] == 1 && i > lastCoveredIndex) {
                //there is a hole at position i and it is not covered by previous board
                //minus 1 because i itself is not covered and needs to be covered as well.
                lastCoveredIndex = i + boardSize - 1
                boardsCount++
            }
        }

        return boardsCount
    }
}
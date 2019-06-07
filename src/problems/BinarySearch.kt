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
}
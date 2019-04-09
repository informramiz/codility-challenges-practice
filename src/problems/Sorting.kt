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

    /**
     * Disc Intersections
     */
    fun calculateDiscIntersections(A: Array<Int>): Int {
        val MAX_PAIRS_ALLOWED = 10_000_000L
        //calculate startX and endX for each disc
        //as y is always 0 so we don't care about it. We only need X
        val ranges = Array(A.size) { i ->
            calculateXRange(i, A[i])
        }

        //sort Xranges by the startX
        ranges.sortBy { range ->
            range.start
        }

        val starts = Array(ranges.size) {index ->
            ranges[index].start
        }

        var count = 0
        for (i in 0 until ranges.size) {
            val checkRange = ranges[i]

            //find the right most disc whose start is less than or equal to end of current disc
            val index = bisectRight(starts, checkRange.endInclusive, i)

            //the number of discs covered by this disc are:
            //count(the next disc/range ... to the last disc/range covered by given disc/range)
            //example: given disc index = 3, last covered (by given disc) disc index = 5
            //count = 5 - 3 = 2
            //because there are only 2 discs covered by given disc
            // (immediate next disc with index 4 and last covered disc at index 5)
            val intersections = (index - i)

            //because we are only considering discs intersecting/covered by a given disc to the right side
            //and ignore any discs that are intersecting on left (because previous discs have already counted those
            // when checking for their right intersects) so this calculation avoids any duplications
            count += intersections

            if (count > MAX_PAIRS_ALLOWED) {
                return -1
            }
        }

        return if (count > MAX_PAIRS_ALLOWED) {
            -1
        } else {
            count
        }
    }

    private fun calculateXRange(x: Int, r: Int): LongRange {
        val minX = x - r.toLong()
        val maxX = x + r.toLong()

        return LongRange(minX, maxX)
    }

    private fun doesIntersect(srcRange: LongRange, chkRange: LongRange): Boolean {
        return srcRange.start in chkRange || srcRange.endInclusive in chkRange
    }

    fun binarySearch(array: Array<Int>, key: Int): Int {
        var start = 0
        var end = array.size - 1
        while (start <= end) {
            val mid = Math.ceil((start + end) / 2.0).toInt()
            val midValue = array[mid]
            when {
                midValue == key ->
                    return mid
                midValue < key -> // key is in right half part
                    start = mid + 1
                else -> //key is in first half of the array
                    end = mid - 1
            }
        }

        return -1
    }

    fun bisectRight(array: Array<Long>, key: Long, arrayStart: Int = 0): Int {
        var start = arrayStart
        var end = array.size - 1
        var bisect = start

        while (start <= end) {
            val mid = Math.ceil((start + end) / 2.0).toInt()
            val midValue = array[mid]
            val indexAfterMid = mid + 1

            if (key >= midValue) {
                bisect = mid
            }

            if (key >= midValue && (indexAfterMid > end || key < array[indexAfterMid])) {
                break
            } else if (key < midValue) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        }

        return bisect
    }
}
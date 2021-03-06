package problems

object MaxSlice {
    /**
     * https://codility.com/media/train/7-MaxSlice.pdf
     *
     * NOTE:  We assume that the slice can be empty and its sum equals 0 SO
     * a slice CAN NOT be smaller than 0
     *
     * Example:
     * 5 -7 3 5 -2 4 -1
     * In the picture, the slice with the largest sum is highlighted in gray. The sum of this slice
     * equals 10 (A[2..5]) and there is no slice with a larger sum.
     */
    fun findMaxSliceSum(A: Array<Int>): Int {
        var maxSum = 0
        var runningSum = 0

        A.forEach { value ->
            runningSum += value
            //sum can't be less than 0 as we must assume an empty slice has sum = 0
            runningSum = Math.max(0, runningSum)
            //check if new running sum is greater than last max sum
            maxSum = Math.max(runningSum, maxSum)
        }

        return maxSum
    }

    fun findMaxSumSlice(A: Array<Int>): Slice {
        var maxSum = Slice()
        val runningSum = Slice()
        A.forEachIndexed { index, value ->
            runningSum.sum += value
            runningSum.q = index
            //sum can't be less than 0 (as in that case empty slice has 0 sum)
            if (runningSum.sum < 0) {
                //reset running sum to 0
                runningSum.sum = 0
                runningSum.p = index + 1
                runningSum.q = index + 1
            }

            if (maxSum.sum < runningSum.sum) {
                maxSum = runningSum.copy()
            }
        }

        return maxSum
    }

    /**
     * https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_profit/
     */
    fun findMaximumProfit(A: Array<Int>): Int {
        if (A.isEmpty()) return 0

        var maxProfit = 0
        var minBuyPrice = A[0]
        for (i in 1 until A.size) {
            //define new value for readability
            val currentDayPrice = A[i]
            //keep track of min buy price on any day.
            minBuyPrice = Math.min(minBuyPrice, currentDayPrice)
            maxProfit = Math.max(maxProfit, currentDayPrice - minBuyPrice)
        }

        //we have to return 0 if profit is not possible
        return Math.max(maxProfit, 0)
    }

    /**
     * https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_slice_sum/
     * Note: The max sum can be negative so this is different problem than the one solved above
     */
    fun findMaxSliceSumWithNegativePossibleValue(A: Array<Int>): Int {
        var maxSum = A[0]
        var runningSum = A[0]

        for (i in 1 until A.size) {
            runningSum = Math.max(A[i], runningSum + A[i])
            maxSum = Math.max(maxSum, runningSum)
        }

        return maxSum
    }

    /**
     * https://app.codility.com/programmers/lessons/9-maximum_slice_problem/max_double_slice_sum/
     * MaxDoubleSliceSum
     * Find the maximal sum of any double slice.
     * The sum of double slice (X, Y, Z) is the total of
     * A[X + 1] + A[X + 2] + ... + A[Y − 1] + A[Y + 1] + A[Y + 2] + ... + A[Z − 1].
     * Solution help: https://www.martinkysel.com/codility-maxdoubleslicesum-solution/
     */
    fun maxDoubleSliceSum(A: Array<Int>): Int {
        val prefixSums = Array(A.size) {0}
        val suffixSums = Array(A.size) {0}

        //we can't include 0 or lastIndex because x and z are not included in sum
        //also, sum can't be less than 0 because empty slice has sum 0 so
        for (i in 1 until A.size-1) {
            //pick max of 0 or current element + previous prefix sum
            prefixSums[i] = Math.max(0, prefixSums[i-1] + A[i])
        }

        for (i in A.size-2 downTo 1) {
            //pick max of 0 or previous prefix sum (i+1 because we are going reverse
            suffixSums[i] = Math.max(0, suffixSums[i+1] + A[i])
        }

        var maxDoubleSliceSum = 0
        for (y in 1 until A.size-1) {
            //as y is not part of max slice sum so we check for each index Yi where
            //max(0, maxSum(1..Yi-1) + maxSum(Yi+1..N-1)) = max(0, prefixSums(Yi-1) + suffixSums(Yi+1))
            maxDoubleSliceSum = Math.max(maxDoubleSliceSum, prefixSums[y-1] + suffixSums[y+1])
        }

        return maxDoubleSliceSum
    }
}

data class Slice(var sum: Int = 0,
                 var p: Int = 0,
                 var q: Int = 0)
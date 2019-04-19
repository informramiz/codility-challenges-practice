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
}

data class Slice(var sum: Int = 0,
                 var p: Int = 0,
                 var q: Int = 0)
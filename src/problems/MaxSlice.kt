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
            maxSum = Math.max(runningSum, maxSum)
        }

        return maxSum
    }
}
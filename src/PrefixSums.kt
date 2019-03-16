import kotlin.math.max
import kotlin.math.min

object PrefixSums {
    //https://codility.com/media/train/3-PrefixSums.pdf
    /**
     * There is a simple yet powerful technique that allows for the fast calculation of sums of
     * elements in given slice (contiguous segments of array). Its main idea uses prefix sums which
     * are defined as the consecutive totals of the first 0, 1, 2, . . . , n elements of an array.
     * Given An = [a0, a1, a2 ..., an-1]
     * prefix sums are
     * p0 = 0, p1 = a0, p2 = a0 + a1, p3 = a0 + a1 + a2, . . ., pn = a0 + a1 + . . . + an−1
     */
    private fun calculatePrefixSums(A: Array<Int>): Array<Int> {
        val P = Array(A.size + 1) {0}
        for (i in 1 until P.size) {
            //Px = Px-1 + Ax-1
            //i.e., P1 = P0 + a0 where P0 = 0
            P[i] = P[i-1] + A[i-1]
        }
        return P
    }

    /**
     * Calculate Slice [x ... y] sum such that 0 <= x <= y < n,
     * where the total is the sum ax + ax+1 + . . . + ay−1 + ay
     * P(y+1) = a0 + a1 + ... + ax-1 + ax + ax+1 + ... + ay-1 + ay
     * we know that Px = a0 + a1 + ... + ax-1 so
     * Py+1 = Px + ax + ax+1 + ... + ay-1 + ay
     * -> ax + ... + ay = Py+1 - Px
     * -> sum[x ... y] = Py+1 - Px
     */
    private fun calculateSliceSum(P: Array<Int>, x: Int, y: Int): Int {
        //as explained above
        //sum[x ... y] = Py+1 - Px
        return P[y+1] - P[x]
    }

    /*
    You are given a non-empty, zero-indexed array A of n (1 ¬ n ¬ 100 000) integers
    a0, a1, . . . , an−1 (0 ¬ ai ¬ 1 000). This array represents number of mushrooms growing on the
    consecutive spots along a road. You are also given integers k and m (0 ¬ k, m < n).
    A mushroom picker is at spot number k on the road and should perform m moves. In
    one move she moves to an adjacent spot. She collects all the mushrooms growing on spots
    she visits. The goal is to calculate the maximum number of mushrooms that the mushroom
    picker can collect in m moves.
    For example, consider array A such that:
    2 3 7 5 1 3 9
    The mushroom picker starts at spot (array index) k = 4 and should perform m = 6 moves. She might
    move to spots 3, 2, 3, 4, 5, 6 and thereby collect 1 + 5 + 7 + 3 + 9 = 25 mushrooms. This is the
    maximal number of mushrooms she can collect.
    */
    fun pickMaximumMushroomsInGivenMoves(A: Array<Int>, k: Int, m: Int): Int {
        val prefixSums = calculatePrefixSums(A)
        /**
         *  If we make p moves in one direction, we can calculate the maximal opposite location of the mushroom picker. The mushroom
         *  picker collects all mushrooms between these extremes. We can calculate the total number of
         *  collected mushrooms in constant time by using prefix sums.
         */

        //first start moving left first, then right
        //i.e. p step left, take p step go get back to k spot, now take remaining steps (m - 2p) right
        val leftFirstMax  = moveMushroomPickerLeftFirst(A, prefixSums, k, m)
        //now start moving right first then left
        val rightFirstMax = moveMushroomPickerRightFirst(A, prefixSums, k, m)


        return max(leftFirstMax, rightFirstMax)
    }

    /**
     *  If we make p moves in one direction, we can calculate the maximal opposite location of the mushroom picker. The mushroom
     *  picker collects all mushrooms between these extremes. We can calculate the total number of
     *  collected mushrooms in constant time by using prefix sums.
     */
    private fun moveMushroomPickerLeftFirst(A: Array<Int>, prefixSums: Array<Int>, k: Int, m: Int): Int {
        //first try moving left first
        var max = 0
        val leftSliceLength = k // k - 0 = k
        //we can't exceed left slice length neither can we exceed maximum allowed moves
        //so we can only move left as long as there are moves left and we don't exceed slice length (to avoid index out of bound)
        val maxLeftMovesPossible = min(leftSliceLength, m)
        for (p in 0..maxLeftMovesPossible) {
            //move left by p steps
            val leftPosition = k - p

            //as we have moved left by p steps so remaining moves are
            // = m - 2p
            // why 2? Because to go to right we have to go through the steps
            //that we took to go left first. So we went by p steps to left
            //then to go to right we first went back by p steps (back at k now)
            //and now we can go to right
            //also to go right
            val rightStepsPossible = (m - 2 * p)

            var proposedRightPosition = k + rightStepsPossible
            //it is possible that we took all allowed m moves to left so in this
            //case p = m, so (m - 2p) => (m - 2m) => -m
            // right position can't be negative so in this case no right steps
            //can be take so we will be k on right side
            proposedRightPosition = max(proposedRightPosition, k)

            //we don't want to overshoot array length
            val finalRightPosition = min(proposedRightPosition, A.size - 1)

            val sliceSum = calculateSliceSum(prefixSums, leftPosition, finalRightPosition)
            max = max(max, sliceSum)
        }

        return max
    }

    /**
     *  If we make p moves in one direction, we can calculate the maximal opposite location of the mushroom picker. The mushroom
     *  picker collects all mushrooms between these extremes. We can calculate the total number of
     *  collected mushrooms in constant time by using prefix sums.
     */
    private fun moveMushroomPickerRightFirst(A: Array<Int>, prefixSums: Array<Int>, k: Int, m: Int): Int {
        //same logic as move left first method but in opposite meanings
        var max = 0

        val lengthOfRightSlice = A.size - k - 1
        val maxPossibleRightMoves = min(lengthOfRightSlice, m)
        for (p in 0..maxPossibleRightMoves) {
            val rightPosition = k + p
            val leftSteps = k - (m - 2 * p)
            var proposedLeftPosition = k - leftSteps

            //it is possible that we took all allowed m moves to right so in this case no left steps
            //can be take so we will be k on left side.
            //so in this
            //case p = m, so k -(m - 2p) => k -(m - 2m) => k - (-m) => k + m => k+m > k so array will overshoot
            // left position can't overshoot array
            proposedLeftPosition = min(proposedLeftPosition, k)

            //just to avoid array overshoot
            val finalLeftPosition = max(proposedLeftPosition, 0)
            val sliceSum = calculateSliceSum(prefixSums, finalLeftPosition, rightPosition)

            max = max(sliceSum, max)
        }
        return max
    }
}
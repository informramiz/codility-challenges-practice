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
        val P = Array(A.size + 1) { 0 }
        for (i in 1 until P.size) {
            //Px = Px-1 + Ax-1
            //i.e., P1 = P0 + a0 where P0 = 0
            P[i] = P[i - 1] + A[i - 1]
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
        return P[y + 1] - P[x]
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
        val leftFirstMax = moveMushroomPickerLeftFirst(A, prefixSums, k, m)
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
            //you can validate it as
            //proposedRightPosition = max(proposedRightPosition, k)
            //or
            if (proposedRightPosition < k) {
                proposedRightPosition = k
            }

            //we don't want to overshoot array length.
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
            // left position can't overshoot array. You can validate it as
            //proposedLeftPosition = min(proposedLeftPosition, k)
            //or
            if (proposedLeftPosition > k) {
                proposedLeftPosition = k
            }

            //just to avoid array overshoot
            val finalLeftPosition = max(proposedLeftPosition, 0)
            val sliceSum = calculateSliceSum(prefixSums, finalLeftPosition, rightPosition)

            max = max(sliceSum, max)
        }
        return max
    }

    /**
     *A non-empty array A consisting of N integers is given. The consecutive elements of array A represent consecutive cars on a road.
     *
     *Array A contains only 0s and/or 1s:
     *
     *0 represents a car traveling east,
     *1 represents a car traveling west.
     *The goal is to count passing cars. We say that a pair of cars (P, Q), where 0 ≤ P < Q < N, is passing when P is traveling to the east and Q is traveling to the west.
     *
     *For example, consider array A such that:
     *
     *  A[0] = 0
     *  A[1] = 1
     *  A[2] = 0
     *  A[3] = 1
     *  A[4] = 1
     *We have five pairs of passing cars: (0, 1), (0, 3), (0, 4), (2, 3), (2, 4).
     *
     *Write a function:
     *
     *class Solution { public int solution(int[] A); }
     *
     *that, given a non-empty array A of N integers, returns the number of pairs of passing cars.
     *
     *The function should return −1 if the number of pairs of passing cars exceeds 1,000,000,000.
     *
     *For example, given:
     *
     *  A[0] = 0
     *  A[1] = 1
     *  A[2] = 0
     *  A[3] = 1
     *  A[4] = 1
     *the function should return 5, as explained above.
     *
     *Write an efficient algorithm for the following assumptions:
     *
     *N is an integer within the range [1..100,000];
     *each element of array A is an integer that can have one of the following values: 0, 1.
     */
    fun findTotalCarPairs(A: Array<Int>): Int {
        val maxPairsCountAllowed = 1_000_000_000
        val prefixSums = calculatePrefixSums(A)
        var pairsCount = 0
        A.forEachIndexed { index, value ->
            if (value == 0) {
                pairsCount += calculateSliceSum(prefixSums, index, A.size - 1)
            }

            if (pairsCount > maxPairsCountAllowed) {
                return -1
            }
        }

        return pairsCount
    }

    /**
     * A DNA sequence can be represented as a string consisting of the letters A, C, G and T,
     * which correspond to the types of successive nucleotides in the sequence.
     * Each nucleotide has an impact factor, which is an integer.
     * Nucleotides of types A, C, G and T have impact factors of 1, 2, 3 and 4, respectively.
     * You are going to answer several queries of the form:
     * What is the minimal impact factor of nucleotides contained in a particular part of the given DNA sequence?
     *
     * The DNA sequence is given as a non-empty string S = S[0]S[1]...S[N-1] consisting of N characters. There are M queries, which are given in non-empty arrays P and Q, each consisting of M integers. The K-th query (0 ≤ K < M) requires you to find the minimal impact factor of nucleotides contained in the DNA sequence between positions P[K] and Q[K] (inclusive).
     * For example, consider string S = CAGCCTA and arrays P, Q such that:
     *
     * P[0] = 2    Q[0] = 4
     * P[1] = 5    Q[1] = 5
     * P[2] = 0    Q[2] = 6
     *
     * The answers to these M = 3 queries are as follows:
     *
     * The part of the DNA between positions 2 and 4 contains nucleotides G and C (twice), whose impact factors are 3 and 2 respectively, so the answer is 2.
     * The part between positions 5 and 5 contains a single nucleotide T, whose impact factor is 4, so the answer is 4.
     * The part between positions 0 and 6 (the whole string) contains all nucleotides, in particular nucleotide A whose impact factor is 1, so the answer is 1.
     *
     * Write a function:
     *
     * class Solution { public int[] solution(String S, int[] P, int[] Q); }
     *
     * that, given a non-empty string S consisting of N characters and two non-empty arrays P and Q consisting of M integers, returns an array consisting of M integers specifying the consecutive answers to all queries.
     * Result array should be returned as an array of integers.
     *
     * For example, given the string S = CAGCCTA and arrays P, Q such that:
     *
     * P[0] = 2    Q[0] = 4
     * P[1] = 5    Q[1] = 5
     * P[2] = 0    Q[2] = 6
     *
     * the function should return the values [2, 4, 1], as explained above.
     *
     * Write an efficient algorithm for the following assumptions:
     *
     * N is an integer within the range [1..100,000];
     * M is an integer within the range [1..50,000];
     * each element of arrays P, Q is an integer within the range [0..N − 1];
     * P[K] ≤ Q[K], where 0 ≤ K < M;
     * string S consists only of upper-case English letters A, C, G, T.
     */
    fun findMinimalImpactFactorForDNASequence(S: String, P: Array<Int>, Q: Array<Int>): Array<Int> {
        val nucleotidesMap = mapOf('A' to 1, 'C' to 2, 'G' to 3, 'T' to 4) //for A, C, G, T respectively
        //calculate prefix sum for occurrence of each nucleotide in string S
        val nucleotidesPrefixSumsMap = prefixSumOfNucleotides(S, nucleotidesMap.keys)

        val answer = Array(P.size) {0}
        for (i in 0 until P.size) {
            val p = P[i]
            val q = Q[i]

            //for each nucleotide, check if it is present in range [p, q]
            //and set the first encountered nucleotide's impact factor
            //why first encounter? because we are searching in order [A, C, G, T]
            //which is also the order in which impact factors are sorted and if A is in range [P, Q]
            //then that is the minimum
            for ( (nucleotide, impactFactor) in nucleotidesMap) {
                val prefixSumsForNucleotide = nucleotidesPrefixSumsMap.getValue(nucleotide)
                if (calculateSliceSum(prefixSumsForNucleotide, p, q) > 0) {
                    answer[i] = impactFactor
                    break
                }
            }
        }

        return answer
    }

    /**
     * We will only calculate prefixSum for how many times a nucleotide occurs in given range
     * so P3 = 2 for nucleotide 'A' means that 'A' occurred 2 times till range [0, 2]
     */
    private fun prefixSumOfNucleotides(S: String, nucleotides: Set<Char>): Map<Char, Array<Int>> {
        val nucleotidesPrefixSumsMap = HashMap<Char, Array<Int>>(nucleotides.size)
        //initialise sums array for each nucleotide
        val prefixSumSize = S.length + 1
        nucleotides.forEach { nucleotide ->
            nucleotidesPrefixSumsMap[nucleotide] = Array(prefixSumSize) {0}
        }

        //prefixSums for each nucleotide
        for (i in 1 until prefixSumSize) {
            val currentNucleotide = S[i-1]
            nucleotides.forEach { nucleotide ->
                //for nucleotide == currentNucleotide
                // Pi = Pi-1 + 1
                //for all others
                //Pi = Pi-1 + 0
                val prefixSumsForNucleotide = nucleotidesPrefixSumsMap[nucleotide]!!
                prefixSumsForNucleotide[i] = prefixSumsForNucleotide[i-1] + (currentNucleotide == nucleotide).toInt()
            }

        }

        return nucleotidesPrefixSumsMap
    }

    private fun Boolean.toInt(): Int {
        return if (this) 1 else 0
    }

    /**
     * MinAvgTwoSlice
     *
     * Find the minimal average of any slice containing at least two elements.
     * Programming language:
     * A non-empty array A consisting of N integers is given. A pair of integers (P, Q), such that 0 ≤ P < Q < N, is called a slice of array A (notice that the slice contains at least two elements). The average of a slice (P, Q) is the sum of A[P] + A[P + 1] + ... + A[Q] divided by the length of the slice. To be precise, the average equals (A[P] + A[P + 1] + ... + A[Q]) / (Q − P + 1).
     *
     * For example, array A such that:
     *
     * A[0] = 4
     * A[1] = 2
     * A[2] = 2
     * A[3] = 5
     * A[4] = 1
     * A[5] = 5
     * A[6] = 8
     * contains the following example slices:
     *
     * slice (1, 2), whose average is (2 + 2) / 2 = 2;
     * slice (3, 4), whose average is (5 + 1) / 2 = 3;
     * slice (1, 4), whose average is (2 + 2 + 5 + 1) / 4 = 2.5.
     * The goal is to find the starting position of a slice whose average is minimal.
     *
     * Write a function:
     *
     * class Solution { public int solution(int[] A); }
     *
     * that, given a non-empty array A consisting of N integers, returns the starting position of the slice with the minimal average. If there is more than one slice with a minimal average, you should return the smallest starting position of such a slice.
     *
     * For example, given array A such that:
     *
     * A[0] = 4
     * A[1] = 2
     * A[2] = 2
     * A[3] = 5
     * A[4] = 1
     * A[5] = 5
     * A[6] = 8
     * the function should return 1, as explained above.
     *
     * Write an efficient algorithm for the following assumptions:
     *
     * N is an integer within the range [2..100,000];
     * each element of array A is an integer within the range [−10,000..10,000].
     */
    fun findMinAvgSlice(A: Array<Int>): Int {
        /*
        For solution check:
        https://www.martinkysel.com/codility-minavgtwoslice-solution/
        https://codesays.com/2014/solution-to-min-avg-two-slice-by-codility/
        For general math proof:
        https://github.com/daotranminh/playground/blob/master/src/codibility/MinAvgTwoSlice/proof.pdf

        General idea:
        1. A slice needs to have at least 2 elements. A slice of size 4 can be divided into
        2 slices of size 2 each. A slice of size 5 can be divided into 2 slices of size 2 and 3 respectively.
        so all bigger slices are made up of either 2 or 3 (to handle odd size of bigger slice) sub-slices.
        For a bigger

        2 If we say that a bigger slice (size > 3) has minimal avg that means that either its sub-slices have
        same avg because otherwise the bigger slice can not have minimal avg (because at least one subslice will have
        less avg than bigger slice). Using this argument we can divide the bigger sub-slice into smaller slices and keep
        dividing (again using the same argument) until we reach 2 or 3 size sub-slices (a slice can't be smaller than this).
        So instead of finding a bigger slice with min avg we can just check 2 or 3 size sub-slices and pick the one with
        min avg (because bigger slice can only be optimal if smaller sub-slices are optimal)
        */
        var minAvgSliceIndex = 0
        var minAvg = 10_000.0 //max possible value of Ai
        for (i in 0 until A.size - 2) {
            //check the 2 size sub-slice
            val twoSizeSliceAvg = (A[i] + A[i+1]) / 2.0
            if (twoSizeSliceAvg < minAvg) {
                minAvg = twoSizeSliceAvg
                minAvgSliceIndex = i
            }

            val threeSizeSliceAvg = (A[i] + A[i + 1] + A[i + 2]) / 3.0
            if (threeSizeSliceAvg < minAvg) {
                minAvg = threeSizeSliceAvg
                minAvgSliceIndex = i
            }
        }

        //check the last 2 size slice
        val lastTwoSizeSliceAvg = (A[A.size - 1] + A[A.size - 2]) / 2.0
        if (lastTwoSizeSliceAvg < minAvg) {
            minAvgSliceIndex = A.size - 2
        }

        return minAvgSliceIndex
    }

    private fun calculateSliceSumAvg(P: Array<Int>, x: Int, y: Int): Double {
        val elementsCount = y - x + 1.0
        //slice needs to have at least 2 elements to be called sliced
        if (elementsCount < 2) {
            return 10_0000.0
        }
        return calculateSliceSum(P, x, y) / elementsCount
    }
}
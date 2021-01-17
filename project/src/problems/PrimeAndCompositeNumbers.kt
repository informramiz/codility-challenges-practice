package problems

object PrimeAndCompositeNumbers {
    /**
     * https://codility.com/media/train/8-PrimeNumbers.pdf
     *  if number a is a divisor of n, then n/a is also a divisor.
     *  One of these two divisors is less than or equal to √n.
     *  (If that were not the case, n would be
     *  a product of two numbers greater than √n, which is impossible.)
     *
     *  For example, for n = 36, following are symmetric divisors
     *
     *  1 | 2 3 4 | 6 | 9 12 18 36
     *  (1, 2, 3, 4) has symmetric divisors (9, 12, 18, 36),
     *  6 has symmetric divisor 6 (both are same) so we count it only 1 time
     *
     *  Thus, iterating through all the numbers from 1 to √n allows us to find all the divisors.
     *  If number n is of the form k^2
     *  , then the symmetric divisor of k is also k. This divisor should be
     *  counted just once.
     */
    fun countDivisors(n: Int): Int {
        val longN = n.toLong()
        var i = 1L
        var count = 0
        while (i * i < n) {
            //check if i divides n
            if (longN % i == 0L) {
                //we count both i and it's symmetric divisor n/i
                //because i can only divide n if
                // i x (n/i) = n
                // i has to be multiplied by some number x to get n value and that some number
                // is also a divisor

                count += 2
            }

            i++
        }

        //consider the symmetric divisor case: 6 x 6 = 36 so in this we only count one number (6)
        //and not both numbers because both numbers are same
        if (i * i == longN) {
            count++
        }

        return count
    }

    /**
     * Prime is a number that is divisible by only two numbers: (1, itself)
     */
    fun checkIfPrime(n: Int): Boolean {
        var i = 2
        while (i * i <= n) {
            if (n % i == 0) {
                return false
            }
            i++
        }

        return true
    }

    /**
     * Coin Reversing Problem:
     *
     * Consider n coins aligned in a row. Each coin is showing heads at the beginning.
     * 1 2 3 4 5 6 7 8 9 10
     * Then, n people turn over corresponding coins as follows. Person i reverses coins with numbers
     * that are multiples of i. That is, person i flips coins i, 2 · i, 3 · i, . . . until no more appropriate
     * coins remain. The goal is to count the number of coins showing tails. In the above example,
     * the final configuration is:
     * T H H T H H H H T H
     *
     * Solution:
     * A person i can only flip coins whose numbers are multiple if 'i' so that means person number has
     * to be a divisor of the coin number. Coin will result in tail only if its number of divisors is an odd number.
     * So total coins with odd number of divisors (each) is what we are interested in. A number can have odd number of
     * divisors iff it is a perfect square and perfect squares in range (1...n) are <= sqrt(n) or more precisely
     * = floor(sqrt(n))
     *
     * More details on link: https://informramiz.blogspot.com/2019/05/coin-reversing-codility-problem.html
     */
    fun countTailCoins(n: Int): Int {
        return Math.floor(Math.sqrt(n.toDouble())).toInt()
    }

    /**
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/count_factors/
     * A positive integer D is a factor of a positive integer N if there exists an integer M such that N = D * M.
     * For example, 6 is a factor of 24, because M = 4 satisfies the above condition (24 = 6 * 4).
     * Write a function:
     * class Solution { public int solution(int N); }
     * that, given a positive integer N, returns the number of its factors.
     * For example, given N = 24, the function should return 8, because 24 has 8 factors, namely 1, 2, 3, 4, 6, 8, 12, 24. There are no other factors of 24.
     */
    fun countFactors(n: Int): Int {
        //factors are just divisors so count divisors
        return countDivisors(n)
    }

    /**
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/min_perimeter_rectangle/
     * An integer N is given, representing the area of some rectangle.
     * The area of a rectangle whose sides are of length A and B is A * B, and the perimeter is 2 * (A + B).
     * The goal is to find the minimal perimeter of any rectangle whose area equals N. The sides of this rectangle should be only integers.
     * For example, given integer N = 30, rectangles of area 30 are:
     * (1, 30), with a perimeter of 62,
     * (2, 15), with a perimeter of 34,
     * (3, 10), with a perimeter of 26,
     * (5, 6), with a perimeter of 22.
     * Write a function:
     * class Solution { public int solution(int N); }
     * that, given an integer N, returns the minimal perimeter of any rectangle whose area is exactly equal to N.
     *
     * For example, given an integer N = 30, the function should return 22, as explained above.
     * Write an efficient algorithm for the following assumptions:
     * N is an integer within the range [1..1,000,000,000].
     */
    fun findMinimumPerimeter(n: Int): Int {
        //we will use long to avoid integer overflow which can easily happen if given
        //n = Int.MAX_VALUE
        val longN = n.toLong()
        var i = 1L
        var minPerimeter = (2 + (longN + longN))

        while (i * i < longN) {
            //rectangles with area N will have sides A, B as divisors so
            //we need to find divisors and then calculate perimeter and
            //keep track of minimum perimeter
            if (n % i == 0L) {
                val A = i
                val B = n / i
                val perimeter = 2 * (A + B)
                minPerimeter = Math.min(minPerimeter, perimeter)
            }

            i++
        }

        //handle the boundary case of perfect squares (6 x 6 = 36)
        if (i * i == longN) {
            //in this case both sides are i (A = i, B = i)
            val perimeter = 2 * (i + i)
            minPerimeter = Math.min(minPerimeter, perimeter)
        }

        return minPerimeter.toInt()
    }

    /**
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/peaks/
     * Peaks
     * Divide an array into the maximum number of same-sized blocks, each of which should contain
     * an index P such that A[P - 1] < A[P] > A[P + 1].
     */
    fun peaks(A: IntArray): Int {
        //array size must be >= 3 for any peak to exist
        if (A.size < 3) return 0

        //calculate prefixSums for peaks (peaks encountered till any index from left to right)
        //this will help us in easily finding peaks count in a given range.
        val prefixSums = peaksPrefixSum(A)

        //if there is no peak at all, then just return
        if (prefixSums[A.size] == 0) return 0

        //the smaller the size of k the max blocks we will have and because k will be increasing
        //so the first ever k for which all blocks will contain peaks
        //will result in max blocks
        var k = 2
        //Because all blocks should be equal size so max possible K will be <= n/2
        //because the only other possible K is when K = N (1 block)
        val halfN = A.size / 2
        while (k <= halfN ) {
            //K can only divide array A of size N in equal blocks if it is a divisor of the N
            if (A.size % k == 0) {
                if (doAllBlocksContainPeaks(prefixSums, k)) {
                    //return blocks count
                    return A.size / k
                }
            }
            k++
        }

        //Remember, we have already checked at top of function for at least 1 peak in full array
        //so this code flow is only reached when no possible blocks were found with each having a peak
        //that means the whole array is just 1 block
        //so just return 1
        return 1
    }

    private fun peaksPrefixSum(A: IntArray): Array<Int> {
        val prefixSums = Array(A.size + 1) {0}
        for (i in 1 until A.size) {
            prefixSums[i+1] = prefixSums[i] + if (i+1 < A.size && A[i] > A[i-1] && A[i] > A[i+1]) 1 else 0
        }

        return prefixSums
    }

    private fun doAllBlocksContainPeaks(prefixSums: Array<Int>, blockSize: Int): Boolean {
        for (i in 0..prefixSums.size-blockSize step blockSize) {
            if (!isPeakPresent(prefixSums, i, i+blockSize-1)) {
                return false
            }
        }

        return true
    }

    private fun isPeakPresent(prefixSums: Array<Int>, x: Int, y: Int): Boolean {
        return peaksCountInRange(prefixSums, x, y) > 0
    }

    private fun peaksCountInRange(prefixSums: Array<Int>, x: Int, y: Int): Int {
        //Py = Py+1 - Px
        return prefixSums[y+1] - prefixSums[x]
    }

    /**
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/flags/
     * Flags
     * Find the maximum number of flags that can be set on mountain peaks.
     */
    fun findMaxSettableFlags(A: IntArray): Int {
        //find peak indexes in array A
        val peakIndexes = findPeakIndexes(A)
        //if there are no peaks then we can't set any flags at all
        if (peakIndexes.isEmpty()) {
            return 0
        }

        //variable to keep track of max flags set
        var maxFlagsSet = 0

        //the max flags we can set are equal to total peaks
        //in case each peak is exactly peaks.size distance (index distance) apart
        //and min flags is 1 because we can always set flag on the first peak so
        //the range for possible flag count values is [1, peaks.size]
        var start = 1
        var end = peakIndexes.size

        //we will use binary search. We know that if we can set X flags then that
        //means that we also set x-1, x-2 ... flags so the range we should check is
        //(mid, end] and if we can't set x flags then we know that we can't set
        //x+1, x+2 flags count as well so the range to check is [start, mid)
        while (start <= end) {
            //get the middle of flag counts range [start, end]
            val midFlagsCount = Math.ceil((start + end) / 2.0).toInt()
            //try setting the flags K = midFlagsCount
            val newSetFlagsCount = setFlags(peakIndexes, midFlagsCount)

            //check if all flags were set
            if (newSetFlagsCount >= midFlagsCount) {
                //that means all flags before mid can be set (range: [start, mid])
                //so let's check how many more flags we can set
                //in the range (mid, end]
                start = midFlagsCount + 1
            } else if (newSetFlagsCount < midFlagsCount) {
                //not all flags were set so it means
                //we should check the range [start, mid]
                end = midFlagsCount - 1
            }

            //keep track of max flags set so far.
            maxFlagsSet = Math.max(newSetFlagsCount, maxFlagsSet)
        }

        return maxFlagsSet
    }

    /**
     * Returns peaks indexes in array A
     */
    private fun findPeakIndexes(A: IntArray): List<Int> {
        if (A.size < 3) return emptyList()

        val peakIndexes = mutableListOf<Int>()
        for (i in 1 until A.size) {
            //check if it is a peak
            if (i+1 < A.size && A[i] > A[i-1] && A[i] > A[i+1]) {
                peakIndexes.add(i)
            }
        }

        return peakIndexes
    }

    /**
     * Try to set given number of flags (flagsCount)
     * and return the number of flags that were successfully set on peaks
     */
    private fun setFlags(peakIndexes: List<Int>, flagsCount: Int): Int {
        //we need at least 1 peak to set any flag
        if (peakIndexes.isEmpty()) {
            return 0
        }
        //first peak will definitely have a flag set so flagsCount-1
        var remainingFlags = flagsCount - 1
        //first peak flag already set
        var previousFlagIndex = peakIndexes[0]

        for (pIndex in peakIndexes) {
            //check if current peakIndex is at K = flagsCount distance apart from the previous peak
            //as this is the condition for setting flags.
            if (pIndex - previousFlagIndex >= flagsCount) {
                //Make current peakIndex as previous flag index (as flag is set) for use in next iteration
                previousFlagIndex = pIndex
                //as 1 flag was set so decrease flag count
                remainingFlags--
            }

            //if we have already set all flags so no need to check more
            if (remainingFlags <= 0) break
        }

        //return the max flags set
        return flagsCount - remainingFlags
    }
}
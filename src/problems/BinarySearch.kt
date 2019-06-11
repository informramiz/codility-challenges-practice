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

    /**
     * https://app.codility.com/programmers/lessons/14-binary_search_algorithm/min_max_division/
     * -------MinMaxDivision---------
     * Divide array A into K blocks and minimize the largest sum of any block.
     * ---------------
     * You are given integers K, M and a non-empty array A consisting of N integers. Every element of the array is not greater than M.
     * You should divide this array into K blocks of consecutive elements. The size of the block is any integer between 0 and N. Every element of the array should belong to some block.
     * The sum of the block from X to Y equals A[X] + A[X + 1] + ... + A[Y]. The sum of empty block equals 0.
     * The large sum is the maximal sum of any block.
     *
     * For example, you are given integers K = 3, M = 5 and array A such that:
     * A[0] = 2
     * A[1] = 1
     * A[2] = 5
     * A[3] = 1
     * A[4] = 2
     * A[5] = 2
     * A[6] = 2
     *
     * The array can be divided, for example, into the following blocks:
     *
     * [2, 1, 5, 1, 2, 2, 2], [], [] with a large sum of 15;
     * [2], [1, 5, 1, 2], [2, 2] with a large sum of 9;
     * [2, 1, 5], [], [1, 2, 2, 2] with a large sum of 8;
     * [2, 1], [5, 1], [2, 2, 2] with a large sum of 6.
     *
     * The goal is to minimize the large sum. In the above example, 6 is the minimal large sum.
     *
     * Write a function:
     * class Solution { public int solution(int K, int M, int[] A); }
     *
     * that, given integers K, M and a non-empty array A consisting of N integers, returns the minimal large sum.
     * For example, given K = 3, M = 5 and array A such that:
     *
     * A[0] = 2
     * A[1] = 1
     * A[2] = 5
     * A[3] = 1
     * A[4] = 2
     * A[5] = 2
     * A[6] = 2
     *
     * the function should return 6, as explained above.
     *
     * Write an efficient algorithm for the following assumptions:
     * N and K are integers within the range [1..100,000];
     * M is an integer within the range [0..10,000];
     * each element of array A is an integer within the range [0..M].
     */
    fun findMinBlockSum(K: Int, M: Int, A: Array<Int>): Int {
        //the min sum is 1 element sum and that is when K >= A.length. In this max(A) is
        // actually the minimum block sum
        var minSum = A.max()!!
        //the max sum is when K = 1, in this case whole array A sum (sum(A)) is the minimum possible sum
        var maxSum = A.sum()

        //handle boundary cases
        if (K == 1) {
            return maxSum
        } else if (K >= A.size) {
            return minSum
        }

        //now we can find the min possible block sum by using binary search as binary search
        //can be applied to a given search space if that search space in sorted and in this case the sorted
        //search space is [minSum, maxSum] so binary search is applicable
        var result = 0
        while (minSum <= maxSum) {
            val midSum = (minSum + maxSum) / 2
            //count the blocks that we can achieve with midSum as maxSum
            val blocksCount = countBlocks(A, midSum)
            if (blocksCount <= K) {
                //Case blocksCount < K:
                //this means that we are putting too many elements into a block and elements/block can
                //be decreased which means that we achieve smaller sum than midSum. So we check
                //in range [minSum, midSum-1]
                //
                //Case blocksCount == K:
                //even though the number blocks are fine with midSum but we still want to check if we can
                //achieve a sum lower than midSum so we search in range [minSum, midSum-1]
                maxSum = midSum-1
                //keep track of last midSum (will come in handy for case blocksCount == K)
                result = midSum
            } else {
                //as blocksCount > K, this means that we are putting too few elements in a block. We need
                //to increase elements/block to decrease the blocks count. This will result in increased
                //blockSum so the right sum will be in range [midSum+1, maxSum]
                minSum = midSum + 1
            }
        }

        return result
    }

    private fun countBlocks(A: Array<Int>, maxBlockSum: Int): Int {
        //as A is always non-empty so we will always have 1 block
        var blockCount = 1
        var blockSum = 0
        for (a in A) {
            //check if a can fit in current block while keep blockSum within maxBlockSum
            if (blockSum + a <= maxBlockSum) {
                blockSum += a
            } else {
                //as a can't fit within current block so start a new block and reset the blockSum starting
                //with initial value a
                blockSum = a
                blockCount++
            }
        }

        return blockCount
    }

    /**
     * -----------NailingPlanks--------------
     * Count the minimum number of nails that allow a series of planks to be nailed.
     * You are given two non-empty arrays A and B consisting of N integers. These arrays represent N planks. More precisely, A[K] is the start and B[K] the end of the K−th plank.
     * Next, you are given a non-empty array C consisting of M integers. This array represents M nails. More precisely, C[I] is the position where you can hammer in the I−th nail.
     * We say that a plank (A[K], B[K]) is nailed if there exists a nail C[I] such that A[K] ≤ C[I] ≤ B[K].
     * The goal is to find the minimum number of nails that must be used until all the planks are nailed. In other words, you should find a value J such that all planks will be nailed after using only the first J nails. More precisely, for every plank (A[K], B[K]) such that 0 ≤ K < N, there should exist a nail C[I] such that I < J and A[K] ≤ C[I] ≤ B[K].
     * For example, given arrays A, B such that:
     * A[0] = 1    B[0] = 4
     * A[1] = 4    B[1] = 5
     * A[2] = 5    B[2] = 9
     * A[3] = 8    B[3] = 10
     * four planks are represented: [1, 4], [4, 5], [5, 9] and [8, 10].
     * Given array C such that:
     * C[0] = 4
     * C[1] = 6
     * C[2] = 7
     * C[3] = 10
     * C[4] = 2
     * if we use the following nails:
     * 0, then planks [1, 4] and [4, 5] will both be nailed.
     * 0, 1, then planks [1, 4], [4, 5] and [5, 9] will be nailed.
     * 0, 1, 2, then planks [1, 4], [4, 5] and [5, 9] will be nailed.
     * 0, 1, 2, 3, then all the planks will be nailed.
     * Thus, four is the minimum number of nails that, used sequentially, allow all the planks to be nailed.
     * Write a function:
     * class Solution { public int solution(int[] A, int[] B, int[] C); }
     * that, given two non-empty arrays A and B consisting of N integers and a non-empty array C consisting of M integers, returns the minimum number of nails that, used sequentially, allow all the planks to be nailed.
     * If it is not possible to nail all the planks, the function should return −1.
     * For example, given arrays A, B, C such that:
     * A[0] = 1    B[0] = 4
     * A[1] = 4    B[1] = 5
     * A[2] = 5    B[2] = 9
     * A[3] = 8    B[3] = 10
     * C[0] = 4
     * C[1] = 6
     * C[2] = 7
     * C[3] = 10
     * C[4] = 2
     * the function should return 4, as explained above.
     * Write an efficient algorithm for the following assumptions:
     * N and M are integers within the range [1..30,000];
     * each element of arrays A, B, C is an integer within the range [1..2*M];
     * A[K] ≤ B[K].
     *
     */
    fun findMinNailsToPlank(A: Array<Int>, B: Array<Int>, C: Array<Int>): Int {
        var minNails = 1
        var maxNails = C.size
        //find the right number of nails using binary search
        var minNailsCount = -1
        while (minNails <= maxNails) {
            val mid = (minNails + maxNails) / 2
            if (canNailAllPlanks(A, B, C, mid)) {
                //let's check if we can still nail if we decrease the number of nails
                //search space: [minNails, mid-1]
                maxNails = mid - 1
                //keep the last nails used in case we don't find any further valid nails
                minNailsCount = mid
            } else {
                //we were not able to nail all planks. We need more nails than mid
                //search space: [mid+1, maxNails]
                minNails = mid + 1
            }
        }

        return minNailsCount
    }

    private fun canNailAllPlanks(plankStarts: Array<Int>, plankEnds: Array<Int>,
                         nails: Array<Int>, maximumAllowedNails: Int): Boolean {
        for (i in 0 until plankStarts.size) {
            if (!nailPlank(plankStarts[i], plankEnds[i], nails, maximumAllowedNails)) {
                return false
            }
        }

        return true
    }

    private fun nailPlank(plankStart: Int, plankEnd: Int, nails: Array<Int>, maximumAllowedNails: Int): Boolean {
        for (i in 0 until maximumAllowedNails) {
            if (nails[i] in plankStart..plankEnd) {
                return true
            }
        }

        return false
    }
}
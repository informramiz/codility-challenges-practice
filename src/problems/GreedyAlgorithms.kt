package problems

/**
 * Created by Ramiz Raja on 2019-06-19
 * https://codility.com/media/train/14-GreedyAlgorithms.pdf
 *
 * ----------Description---------------
 * We consider problems in which a result comprises a sequence of steps or choices that have to be made to achieve the
 * optimal solution. Greedy programming is a method by which a solution is determined based on making the locally
 * optimal choice at any given moment. In other words, we choose the best decision from the viewpoint of the
 * current stage of the solution.
 * Depending on the problem, the greedy method of solving a task may or may not be the best approach.
 * If it is not the best approach, then it often returns a result which is approximately correct but suboptimal.
 * In such cases dynamic programming or brute-force can be the optimal approach. On the other hand,
 * if it works correctly, its running time is usually faster than those of dynamic programming or brute-force.
 */

object GreedyAlgorithms {
    /**
     * https://codility.com/media/train/14-GreedyAlgorithms.pdf
     * --------Problem---------
     * There are n > 0 canoeists(people to ride to a paddle boat) weighing
     * respectively 1 <= w0 <= w1 <= . . . <= wn−1 <= 10^9.
     * The goal is to seat them in the minimum number of double canoes(paddle boats)
     * whose displacement (the maximum load) equals k.
     * You may assume that wi <= k.
     *
     * -------Solution-----------
     * The fattest canoeist is seated with the thinnest, as long as their weight is less than or equal to k.
     * If not, the fattest canoeist is seated alone in the canoe.
     */
    fun greedyCanoeist(W: IntArray, k: Int): Int {
        var start = 0
        var end = W.lastIndex
        var canoesCount = 0
        while (start <= end) {
            //try to seat a fattest (highest w) with lightest (smallest w), otherwise seat fattest alone
            if (W[start] + W[end] <= k) {
                //both skinny and fattest were seated
                start++
                end--
            } else {
                //lightest skinny and fattest were not seated together so seat fattest alone
                end--
            }

            canoesCount++
        }

        return canoesCount
    }

    fun tieRopes(A: IntArray, K: Int): Int {
        var maxCount = 0
        var runningCount = 0
        var runningLength = 0L
        for (length in A) {
            runningLength += length
            runningCount++

            if (runningLength >= K) {
                maxCount = Math.max(maxCount, runningCount)
                runningLength = 0
                runningCount = 0
            }
        }

        return maxCount
    }
}
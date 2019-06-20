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

    /**
     * https://app.codility.com/programmers/lessons/16-greedy_algorithms/tie_ropes/
     *
     * TieRopes
     * Tie adjacent ropes to achieve the maximum number of ropes of length >= K.
     * Programming language:
     * There are N ropes numbered from 0 to N − 1, whose lengths are given in an array A, lying on the floor in a line. For each I (0 ≤ I < N), the length of rope I on the line is A[I].
     * We say that two ropes I and I + 1 are adjacent. Two adjacent ropes can be tied together with a knot, and the length of the tied rope is the sum of lengths of both ropes. The resulting new rope can then be tied again.
     * For a given integer K, the goal is to tie the ropes in such a way that the number of ropes whose length is greater than or equal to K is maximal.
     * For example, consider K = 4 and array A such that:
     * A[0] = 1
     * A[1] = 2
     * A[2] = 3
     * A[3] = 4
     * A[4] = 1
     * A[5] = 1
     * A[6] = 3
     * The ropes are shown in the figure below.
     * We can tie:
     * rope 1 with rope 2 to produce a rope of length A[1] + A[2] = 5;
     * rope 4 with rope 5 with rope 6 to produce a rope of length A[4] + A[5] + A[6] = 5.
     * After that, there will be three ropes whose lengths are greater than or equal to K = 4. It is not possible to produce four such ropes.
     * Write a function:
     * class Solution { public int solution(int K, int[] A); }
     * that, given an integer K and a non-empty array A of N integers, returns the maximum number of ropes of length greater than or equal to K that can be created.
     * For example, given K = 4 and array A such that:
     * A[0] = 1
     * A[1] = 2
     * A[2] = 3
     * A[3] = 4
     * A[4] = 1
     * A[5] = 1
     * A[6] = 3
     * the function should return 3, as explained above.
     * Write an efficient algorithm for the following assumptions:
     * N is an integer within the range [1..100,000];
     * K is an integer within the range [1..1,000,000,000];
     * each element of array A is an integer within the range [1..1,000,000,000].
     */
    fun tieRopes(A: IntArray, K: Int): Int {
        var count = 0
        var runningLength = 0L
        for (length in A) {
            runningLength += length

            if (runningLength >= K) {
                count++
                runningLength = 0
            }
        }

        return count
    }
}
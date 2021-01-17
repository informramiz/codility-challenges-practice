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

    /**
     * https://app.codility.com/programmers/lessons/16-greedy_algorithms/max_nonoverlapping_segments/
     * ---------MaxNonoverlappingSegments--------------
     * Find a maximal set of non-overlapping segments.
     * Programming language:
     * Located on a line are N segments, numbered from 0 to N − 1, whose positions are given in arrays A and B. For each I (0 ≤ I < N) the position of segment I is from A[I] to B[I] (inclusive). The segments are sorted by their ends, which means that B[K] ≤ B[K + 1] for K such that 0 ≤ K < N − 1.
     * Two segments I and J, such that I ≠ J, are overlapping if they share at least one common point. In other words, A[I] ≤ A[J] ≤ B[I] or A[J] ≤ A[I] ≤ B[J].
     * We say that the set of segments is non-overlapping if it contains no two overlapping segments. The goal is to find the size of a non-overlapping set containing the maximal number of segments.
     * For example, consider arrays A, B such that:
     * A[0] = 1    B[0] = 5
     * A[1] = 3    B[1] = 6
     * A[2] = 7    B[2] = 8
     * A[3] = 9    B[3] = 9
     * A[4] = 9    B[4] = 10
     * The segments are shown in the figure below.
     * The size of a non-overlapping set containing a maximal number of segments is 3. For example, possible sets are {0, 2, 3}, {0, 2, 4}, {1, 2, 3} or {1, 2, 4}. There is no non-overlapping set with four segments.
     * Write a function:
     * class Solution { public int solution(int[] A, int[] B); }
     * that, given two arrays A and B consisting of N integers, returns the size of a non-overlapping set containing a maximal number of segments.
     * For example, given arrays A, B shown above, the function should return 3, as explained above.
     * Write an efficient algorithm for the following assumptions:
     * N is an integer within the range [0..30,000];
     * each element of arrays A, B is an integer within the range [0..1,000,000,000];
     * A[I] ≤ B[I], for each I (0 ≤ I < N);
     * B[K] ≤ B[K + 1], for each K (0 ≤ K < N − 1).
     *
     *
     * ----------Solution-----------
     * As segments are sorted by their ends (B[0] <= B[1] ... <= B[N-1]) so pick the segments which finish
     * early, verify that it does not overlap with the previously picked segment. If it does not overlap then
     * count it
     */
    fun maxNonOverlappingSegments(A: IntArray, B: IntArray): Int {
        if (A.isEmpty()) return 0

        var count = 1
        var lastEnd = B[0]

        for (i in 1 until A.size) {
            if (A[i] > lastEnd) {
                count++
                lastEnd = B[i]
            }
        }

        return count
    }

}
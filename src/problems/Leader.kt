package problems

object Leader {
    /**
     * https://codility.com/media/train/6-Leader.pdf
     * LEADER:
     *The leader of this sequence is the element whose value occurs more than n/2 times.
     * A = [6 8 4 6 8 6 6] so 6 is the leader in A
     * Notice that the sequence can have at most one leader. If there were two leaders then their
     * total occurrences would be more than 2 · n/2 = n, but we only have n elements.
     */

    /**
     * Find leader using sorting: O(nlgn)
     * If the sequence is presented in increasing order, then identical values are adjacent to each other.
     * Having sorted the sequence, we can easily count slices of the same values and find the leader
     * in a smarter way. Notice that if the leader occurs somewhere in our sequence, then it must
     * occur at index n/2 (the central element). This is because, given that the leader occurs in more
     * than half the total values in the sequence, there are more leader values than will fit on either
     * side of the central element in the sequence.
     */
    fun findLeaderSolution1(A: Array<Int>): Int {
        A.sort()
        //as array is sorted so if there is any leader it must be at the middle at has to occur more than n/2 times
        val candidate = A[A.size / 2]

        var count = 0
        A.forEach { value ->
            if (value == candidate) {
                count++
            }
        }

        return if (count > A.size/2) candidate else -1
    }
}
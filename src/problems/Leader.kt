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
    fun findLeaderUsingSorting(A: Array<Int>): Int {
        A.sort()
        //as array is sorted so if there is any leader it must be at the middle at has to occur more than n/2 times
        val candidate = A[A.size / 2]

        var count = countValue(A, candidate)

        return if (count > A.size/2) candidate else -1
    }

    /**
     * Find leader using Stack: O(n)
     * If we remove two DIFFERENT elements from the sequence then leader for remaining sequence of size n-2 is still the
     * the same. Using this idea we can keep removing pairs of different elements and at the end if the element left
     * is the candidate for leader.
     *
     * How to remove element pair?
     * After each such operation we check whether the two elements at the top of the stack are different.
     * If they are, we remove them from the stack. This is equivalent to removing a pair of different
     * elements from the sequence
     *
     * Note: We don't need to actually use the stack data structure. All we need to remember is the last element
     * pushed to stack (last stack top) and the current size of the stack. Then in current iteration we can
     * compare the stack top with the current element to see if they are different and +/- the size of the stack
     * accordingly
     */
    fun findLeaderUsingStack(A: Array<Int>): Int {
        val candidateIndex = findLeaderCandidateIndex(A)
        if (candidateIndex == -1) {
            return -1
        }

        //now let's count leader candidate occurrences to verify if it indeed a leader or not.
        val candidate = A[candidateIndex]
        val candidateCount = countValue(A, candidate)

        //remember: a leader has to occur more than n/2 times
        return if (isCountGoodToBeALeader(candidateCount, A.size)) candidate else -1
    }

    private fun findLeaderCandidateIndex(A: Array<Int>): Int {
        var stackSize = 0
        var stackTop = -1
        var stackTopIndex = -1
        A.forEachIndexed { index, value ->
            if (stackSize == 0) {
                //stack is empty so push the element onto stack
                stackTop = value
                stackSize++
                stackTopIndex = index
            }  else {
                //there is an element on stack so let's check if current element and last element are different
                if (stackTop != value) {
                    //decrease the size of stack by 1 (1 because we have not pushed current element to stack)
                    //and the pair we are removing includes (current element, last element).
                    stackSize--
                } else {
                    //last element and current are same so push current element to stack as well
                    //Note: We are not updating stackTop because last and current elements are same so no need
                    stackSize++
                }
            }
        }

        //check if there is no element left on stack, as in that case there is no leader candidate
        //so no need to proceed further
        return if (stackSize > 0) {
            //element on top of stack, so we have a leader candidate
            stackTopIndex
        } else {
            //no element left on stack so no leader candidate
            -1
        }
    }

    /**
     * Array Dominator (basically leader):
     * Find an index of an array such that its value occurs at more than half of indices in the array.
     */
    fun findDominatorIndex(A: Array<Int>): Int {
        val candidateIndex = findLeaderCandidateIndex(A)
        //check if we have a valid index
        if (candidateIndex == -1) {
            return -1
        }

        //now let's count leader candidate occurrences to verify if it indeed a leader or not.
        val candidate = A[candidateIndex]
        val candidateCount = countValue(A, candidate)

        //remember: a leader has to occur more than n/2 times
        return if (isCountGoodToBeALeader(candidateCount, A.size)) candidateIndex else -1
    }

    private fun isCountGoodToBeALeader(candidateCount: Int, n: Int) = candidateCount > n / 2

    private fun countValue(A: Array<Int>, valueToCount: Int): Int {
        var candidateCount = 0
        A.forEach { value ->
            if (value == valueToCount) {
                candidateCount++
            }
        }
        return candidateCount
    }

    /**
     * https://app.codility.com/programmers/lessons/8-leader/equi_leader/
     * EquiLeader:
     * Find the index S such that the leaders of the sequences A[0], A[1], ..., A[S]
     * and A[S + 1], A[S + 2], ..., A[N - 1] are the same.
     */
    fun countEquiLeaders(A: Array<Int>): Int {
        var count = 0
        val forwardCandidates = findLeaderCandidatesForward(A)
        val reverseCandidates = findLeaderCandidatesReverse(A)
        for(i in 0 until A.size - 1) {
            if (forwardCandidates[i] != -1_000_000_001
                && forwardCandidates[i] == reverseCandidates[i+1]) {
                    count++
            }
        }
        return count
    }

    private fun findLeaderCandidatesReverse(A: Array<Int>): Array<Int> {
        //we are going to reuse function to calculate forward leader candidates
        //by passing it a reversed array and then reverse the returned array
        val candidates = findLeaderCandidatesForward(A.reversedArray())
        //reverse the output array as we passed the reversed array to function above
        candidates.reverse()
        return candidates
    }

    private fun findLeaderCandidatesForward(A: Array<Int>): Array<Int> {
        val candidates = Array(A.size) {-1_000_000_001}
        var stackSize = 0
        var stackTop = -1_000_000_001
        //var stackTopIndex = -1
        A.forEachIndexed { index, value ->
            if (stackSize == 0) {
                //stack is empty so push the element onto stack
                stackTop = value
                stackSize++
                //stackTopIndex = index
            }  else {
                //there is an element on stack so let's check if current element and last element are different
                if (stackTop != value) {
                    //decrease the size of stack by 1 (1 because we have not pushed current element to stack)
                    //and the pair we are removing includes (current element, last element).
                    stackSize--
                } else {
                    //last element and current are same so push current element to stack as well
                    //Note: We are not updating stackTop because last and current elements are same so no need
                    stackSize++
                }
            }

            if (stackSize > 0) {
                candidates[index] = stackTop
            }
        }

        return candidates
    }
}
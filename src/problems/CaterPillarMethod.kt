package problems

/**
 * https://codility.com/media/train/13-CaterpillarMethod.pdf
 * The Caterpillar method is a likeable name for a popular means of solving algorithmic tasks.
 * The idea is to check elements in a way that’s reminiscent of movements of a caterpillar.
 * The caterpillar crawls through the array. We remember the front and back positions of the caterpillar,
 * and at every step either of them is moved forward.
 */
object CaterPillarMethod {
    /**
     * Let’s check whether a sequence a0, a1, . . . , an≠1 (1  ̨ ai  ̨ 109)
     * contains a contiguous sub-sequence whose sum of elements equals s.
     */
    fun isThereASequenceOfSum(A: IntArray, givenSum: Int): Boolean {
        var runningSum = 0
        var front = 0
        //for each new back position
        for (back in A) {
            //keep moving the front until the runningSum <= givenSum
            //1. If we find a runningSum == givenSum then return true
            //2. If we find a runningSum > givenSum then stop the
            // front there, move back by 1 step and then move front
            while (front < A.size && (runningSum + A[front]) <= givenSum) {
                runningSum += A[front]
                front++
            }

            if (runningSum == givenSum) {
                return true
            }

            //we did not find a sum with given back, so remove current back from runningSum and move it by 1 step
            runningSum -= back
        }

        //we did not find a sub-sequence whose sum = givenSum
        return false
    }
}
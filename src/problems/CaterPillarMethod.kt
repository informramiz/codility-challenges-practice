package problems

/**
 * https://codility.com/media/train/13-CaterpillarMethod.pdf
 *
 * The Caterpillar method is a likeable name for a popular means of solving algorithmic tasks.
 * The idea is to check elements in a way that’s reminiscent of movements of a caterpillar.
 * The caterpillar crawls through the array. We remember the front and back positions of the caterpillar,
 * and at every step either of them is moved forward.
 */
object CaterPillarMethod {
    /**
     * Let’s check whether a sequence a0, a1, . . . , an-1 (1 < ai <= 10^9)
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

    /**
     * https://codility.com/media/train/13-CaterpillarMethod.pdf
     * -------Problem----------
     * You are given n sticks (of lengths 1 <= a0 <= a1  ̨ ...  <= an-1 <= 10^9). The goal is to count the number of triangles
     * that can be constructed using these sticks. More precisely, we have to count the number of triplets at indices x<y<z,
     * such that ax+ay > az
     *
     * For every pair x,y we can find the largest stick z that can be used to construct the triangle.
     * Every stick k, less than that largest stick z (such that y < k < z), can also be used to build triangle,
     * because the condition ax + ay > ak will still be true. We can add up all these triangles at once to calculate
     * all triangles for given (x, y) using formula (largestZ - y) because each z after y can make up a triangle with
     * given (x, y). We repeat the same process for remaining (x, y)
     */
    fun countTrianglesOfSticks(A: IntArray): Int {
        var trianglesCount = 0
        //for every x
        for (x in 0 until A.size) {
            var z = x + 2 //x + 2 because x+1 will be y to achieve condition (x < y < z)
            //for every y in range [x+1, n]
            for (y in x+1 until A.size) {
                //find the largest z(or you can say all the possible z) that can make triangle with
                //given (x, y) (i.e, which satisfy condition Ax + Ay > Az
                while (z < A.size && (A[x] + A[y]) < A[z]) {
                    //current z can make a triangle with given (x, y) so move to next to see if that can
                    z++ //call this currentZ
                }

                //all z values in range [x+2, currentZ] can make triangles
                //NOTE: -1 in (z - y - 1) because the last z is invalid as the while loop broke due to that z
                //NOTE: we use this calculation for following y because this current z value can make triangles
                //with all the Ys < currentZ because of given assumption (1 <= a0 <= a1  ̨ ...  <= an-1 <= 10^9) so
                // if Ax + Ay > Az then it will definitely be true for all Ys < currentZ
                trianglesCount += z - y - 1
            }
        }

        return trianglesCount
    }

    /**
     * https://app.codility.com/programmers/lessons/15-caterpillar_method/count_distinct_slices/
     * CountDistinctSlices
     * Count the number of distinct slices (containing only unique numbers).
     * Programming language:
     * An integer M and a non-empty array A consisting of N non-negative integers are given. All integers in array A are less than or equal to M.
     * A pair of integers (P, Q), such that 0 ≤ P ≤ Q < N, is called a slice of array A. The slice consists of the elements A[P], A[P + 1], ..., A[Q]. A distinct slice is a slice consisting of only unique numbers. That is, no individual number occurs more than once in the slice.
     * For example, consider integer M = 6 and array A such that:
     * A[0] = 3
     * A[1] = 4
     * A[2] = 5
     * A[3] = 5
     * A[4] = 2
     * There are exactly nine distinct slices: (0, 0), (0, 1), (0, 2), (1, 1), (1, 2), (2, 2), (3, 3), (3, 4) and (4, 4).
     * The goal is to calculate the number of distinct slices.
     * Write a function:
     * class Solution { public int solution(int M, int[] A); }
     * that, given an integer M and a non-empty array A consisting of N integers, returns the number of distinct slices.
     * If the number of distinct slices is greater than 1,000,000,000, the function should return 1,000,000,000.
     * For example, given integer M = 6 and array A such that:
     * A[0] = 3
     * A[1] = 4
     * A[2] = 5
     * A[3] = 5
     * A[4] = 2
     * the function should return 9, as explained above.
     * Write an efficient algorithm for the following assumptions:
     * N is an integer within the range [1..100,000];
     * M is an integer within the range [0..100,000];
     * each element of array A is an integer within the range [0..M].
     */
    fun countDistinctSlices(A: IntArray, M: Int): Int {
        val MAX_DISTINCT_SLICES = 1_000_000_000
        //array to keep count of encountered numbers. We will use it to check if we already encountered
        //a number previously in the given slice or not
        val numbersCount = Array(M + 1) { 0 }
        var distinctSlicesCount = A.size //each number in A is also a distinct slice of size = 1 as per 0 ≤ P ≤ Q < N

        var front = 0
        for (back in 0 until A.size) {
            //we keep going for all numbers that are unique/not discovered (count == 0) for given slice
            while (front < A.size && numbersCount[A[front]] == 0) {
                //count this front as we have explored it
                numbersCount[A[front]]++
                front++
            }

            //all numbers till front-1 can make a slice with given back so
            //NOTE: -1 because last front is what broke the loop so is not valid for given slice
            distinctSlicesCount += front - back - 1

            //we only need to count slices till MAX_DISTINCT_SLICES
            if (distinctSlicesCount >= MAX_DISTINCT_SLICES) {
                return MAX_DISTINCT_SLICES
            }

            //as now we are no longer going to consider the current back so remove its count
            numbersCount[A[back]]--
        }

        return distinctSlicesCount
    }
}
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
    fun countTriangles(A: IntArray): Int {
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
}
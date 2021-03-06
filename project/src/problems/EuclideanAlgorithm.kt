package problems

/**
 * https://codility.com/media/train/10-Gcd.pdf
 */
object EuclideanAlgorithm {
    /**
     * Returns greatest common divisor of both a and b
     */
    fun gcd(a: Int, b: Int): Int {
        if (a % b == 0) {
            return b
        }

        return gcd(b, a % b)
    }

    /**
     * Binary Euclidean Algorithm:
     *
     * A binary algorithm for finding gcd(N,M) is based on the following
     * If N and M are even, gcd(N, M) = 2 gcd(N/2, M/2),
     * If N is even while M is odd, then gcd(N, M) = gcd(N/2, M),
     * If both N and M are odd, then (since N-M is even) |N-M| < max(N,M). Replace the largest of the two with |N-M|.
     *
     * Why is this algorithm more efficient?
     *
     * 1. The algorithm is known as binary because, unlike the original one, it does not use general division of integers
     * but only division by 2. Since in a computer numbers are represented in the Binary system anyway,
     * you should not be surprised to learn that there is a special machine instruction that implements division by 2
     * in a highly efficient manner. This is known as the right shift wherein the rightmost bit is discharged,
     * the remaining bits are shifted one place to the right and the leftmost bit is set to 0.
     *
     * 2. Another handy operation is a bitwise conjunction &. N & 1 is either 1 or 0 for any integer N. It's 1 iff N is odd.
     * Bitwise conjunction is also implemented in hardware, i.e., as a machine instruction.
     *
     * For more info: https://www.cut-the-knot.org/blue/binary.shtml
     *
     * NOTE: As in case if N and M are even,
     * gcd(N, M) = 2 gcd(N/2, M/2)
     * or as it can be = 2 * 2 * 2 ... gcd(N/2, M/2)
     * so we will write it as
     * gcd(N, M) = residual * gcd(N/2, M/2)
     * where residual can be any number multiple of 2
     */
    private fun gcdBinary(a: Int, b: Int, res: Int = 1): Int {
        if (a == b) {
            return res * a
        }

        if (a % 2 == 0 && b % 2 == 0) {
            //both a and b are even so
            return gcdBinary(a/2, b/2, 2 * res)
        } else if (a % 2 == 0) {
            //a is even while b is odd
            return gcdBinary(a/2, b, res)
        } else if (b % 2 == 0) {
            //b is even while as is odd
            return gcdBinary(a, b/2, res)
        } else if (a > b) {
            //both a, b are odd and a > b so set a = a - b
            return gcdBinary(a-b, b, res)
        } else {
            //both a, b are odd and b > a so set b = b - a
            return gcdBinary(a, b - a, res)
        }
    }

    fun gcdBinary(a: Int, b: Int): Int {
        return gcdBinary(a, b, 1)
    }

    /**
     * The least common multiple (LCM) of two integers a and b is the smallest positive integer that
     * is divisible by both a and b. There is the following relation:
     * lcm(a, b) = (a·b) / gcd(a,b)
     *
     * so if we know gcd we can calculate LCM
     * @return long, long because otherwise integer overflow can happen
     */
    fun lcm(a: Long, b: Long): Long {
        return ((a * b) / gcdBinary(a.toInt(), b.toInt()))
    }

    /**
     * Find lcm for multiple integers
     * lcm(a1, a2, ... , an) = lcm(a1, lcm(a2, ... , an))
     */
    fun lcm(A: Array<Int>): Long {
        if (A.size < 2) return -1

        var lcmValue = 1L
        for (i in 0 until A.size) {
            lcmValue = lcm(lcmValue, A[i].toLong())
        }

        return lcmValue
    }

    /**
     * https://app.codility.com/programmers/lessons/12-euclidean_algorithm/chocolates_by_numbers/
     * ------ChocolatesByNumbers----------
     * There are N eatChocolates in a circle. Count the number of eatChocolates you will eat.
     *
     * DETAILS:
     * Two positive integers N and M are given. Integer N represents the number of eatChocolates arranged in a circle, numbered from 0 to N − 1.
     * You start to eat the eatChocolates. After eating a chocolate you leave only a wrapper.
     * You begin with eating chocolate number 0. Then you omit the next M − 1 eatChocolates or wrappers on the circle, and eat the following one.
     * More precisely, if you ate chocolate number X, then you will next eat the chocolate with number (X + M) modulo N (remainder of division).
     * You stop eating when you encounter an empty wrapper.
     *
     * For example, given integers N = 10 and M = 4. You will eat the following eatChocolates: 0, 4, 8, 2, 6.
     * The goal is to count the number of eatChocolates that you will eat, following the above rules.
     *
     * Write a function:
     * class Solution { public int solution(int N, int M); }
     *
     * that, given two positive integers N and M, returns the number of eatChocolates that you will eat.
     * For example, given integers N = 10 and M = 4. the function should return 5, as explained above.
     *
     * Write an efficient algorithm for the following assumptions:
     * N and M are integers within the range [1..1,000,000,000].
     *
     * @param N, number of eatChocolates number in range [0, N-1]
     */
    fun eatChocolates(N: Int, M: Int): Int {
        //Consider the circle as a straight line, the place where the first eaten chocolate is revisited, proceeding
        //with step M is actually the same as you if you proceed with step N. For example, N=10, if you proceed with
        //step N=10 then first chocolate is revisited at step 20. Similarly, if you proceed with step M=4 then
        //first eaten chocolate is revisited at step 20. If N = 12, M = 9 then first chocolate will be revisited
        //at step 36 because this is where M and N meet.
        // As step# in both cases is same so the step/place where
        //first eaten chocolate is revisited (or an empty wrapper is encountered) is LCM of both M and N, so LCM(M,N)
        //
        //step at which first chocolate revisited = stepNumberOfRevisit = LCM(M,N)
        //total jumps after which that (step occurs) chocolate is revisited,
        //proceeding with step M is = stepNumberOfRevisit / M = lcm(M, N) / M
        return (lcm(M.toLong(), N.toLong()) / M).toInt()
    }

    /**
     * https://app.codility.com/programmers/lessons/12-euclidean_algorithm/common_prime_divisors/
     * CommonPrimeDivisors
     * Check whether two numbers have the same prime divisors.
     *
     * A prime is a positive integer X that has exactly two distinct divisors: 1 and X. The first few prime integers are 2, 3, 5, 7, 11 and 13.
     * A prime D is called a prime divisor of a positive integer P if there exists a positive integer K such that D * K = P. For example, 2 and 5 are prime divisors of 20.
     * You are given two positive integers N and M. The goal is to check whether the sets of prime divisors of integers N and M are exactly the same.
     * For example, given:
     * N = 15 and M = 75, the prime divisors are the same: {3, 5};
     * N = 10 and M = 30, the prime divisors aren't the same: {2, 5} is not equal to {2, 3, 5};
     * N = 9 and M = 5, the prime divisors aren't the same: {3} is not equal to {5}.
     * Write a function:
     * class Solution { public int solution(int[] A, int[] B); }
     * that, given two non-empty arrays A and B of Z integers, returns the number of positions K for which the prime divisors of A[K] and B[K] are exactly the same.
     * For example, given:
     * A[0] = 15   B[0] = 75
     * A[1] = 10   B[1] = 30
     * A[2] = 3    B[2] = 5
     * the function should return 1, because only one pair (15, 75) has the same set of prime divisors.
     * Write an efficient algorithm for the following assumptions:
     * Z is an integer within the range [1..6,000];
     * each element of arrays A, B is an integer within the range [1..2,147,483,647].
     */
    fun countNumbersWithCommonPrimeDivisors(A: Array<Int>, B: Array<Int>): Int {
        var count = 0
        for (i in 0 until A.size) {
            if (haveCommonPrimeDivisors(A[i], B[i])) {
                count++
            }
        }

        return count
    }

    private fun haveCommonPrimeDivisors(a: Int, b: Int): Boolean {
        if (a == b) {
            //as both are equal so yes, they have same divisors
            return true
        } else if (a == 1 || b == 1) {
            //as either b or a is 1 and other not so they will never have same prime divisors
            return false
        }

        //If b and a have same prime divisors then their GCD will contain all the prime divisors
        //
        //-------Why GCD must contain all prime divisors?---------
        //Proof is simple. Let g be the gcd of a and b. So we can express a and b as
        // a=gc
        // b=gd.
        // If d and c had any common prime factor p, then p*g would be the gcd, contradicting our
        // definition of g as the gcd. Hence the gcd of a,b contains ALL the common factors of a,b.
        //---------------
        val G = gcd(a, b)

        //G is first divisor of a, let's get the next divisor of a. a/G will next divisor
        var nextDivisorA = a/G
        //keep taking g = gcd(nextDivisorA, G) and dividing nextDivisorA with that g (i.e, nextDivisorA /= g)
        //until we reach nextDivisorA = 1 (means A is good) or we get gcd g = 1 (means A is not good).
        //Note: G in gcd(nextDivisorA, G) makes sure that next divisor we get also divides G (which means also b)
        while (nextDivisorA != 1) {
            val g = gcd(nextDivisorA, G)
            if (g == 1) {
                //nextDivisorA and G does not have any common divisor which means a has a prime
                //divisor that can't divide B
                return false
            }
            //get the next divisor of A
            nextDivisorA /= g
        }

        //now repeat the same process B
        var nextDivisorB = b / G
        while (nextDivisorB != 1) {
            val g = gcd(nextDivisorB, G)
            if (g == 1) {
                return false
            }

            nextDivisorB /= g
        }

        return true
    }
}
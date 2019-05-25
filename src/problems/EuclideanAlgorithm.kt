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
     */
    fun lcm(a: Int, b: Int): Int {
        return (a * b) / gcdBinary(a, b)
    }
}
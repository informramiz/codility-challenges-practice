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
}
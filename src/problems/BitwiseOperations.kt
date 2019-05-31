package problems

object BitwiseOperations {
    /**
     * Given a, b, returns a % b using bitwise operations
     */
    fun mod(a: Int, b: Int): Int {
        //x = a % b, here x can be in range [0, b-1]
        //so we are only interested in bits of (b-1) as those bits will represent mod value
        return a and (b-1)
    }
}
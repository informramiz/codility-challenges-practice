package problems

object PrimeAndCompositeNumbers {
    /**
     * https://codility.com/media/train/8-PrimeNumbers.pdf
     *  if number a is a divisor of n, then n/a is also a divisor.
     *  One of these two divisors is less than or equal to √n.
     *  (If that were not the case, n would be
     *  a product of two numbers greater than √n, which is impossible.)
     *
     *  For example, for n = 36, following are symmetric divisors
     *
     *  1 | 2 3 4 | 6 | 9 12 18 36
     *  (1, 2, 3, 4) has symmetric divisors (9, 12, 18, 36),
     *  6 has symmetric divisor 6 (both are same) so we count it only 1 time
     *
     *  Thus, iterating through all the numbers from 1 to √n allows us to find all the divisors.
     *  If number n is of the form k^2
     *  , then the symmetric divisor of k is also k. This divisor should be
     *  counted just once.
     */
    fun countDivisors(n: Int): Int {
        var i = 1
        var count = 0
        while (i * i < n) {
            //check if i divides n
            if (n % i == 0) {
                //we count both i and it's symmetric divisor n/i
                //because i can only divide n if
                // i x (n/i) = n
                // i has to be multiplied by some number x to get n value and that some number
                // is also a divisor

                count += 2
            }

            i++
        }

        //consider the symmetric divisor case: 6 x 6 = 36 so in this we only count one number (6)
        //and not both numbers because both numbers are same
        if (i * i == 36) {
            count++
        }

        return count
    }
}
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
        val longN = n.toLong()
        var i = 1L
        var count = 0
        while (i * i < n) {
            //check if i divides n
            if (longN % i == 0L) {
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
        if (i * i == longN) {
            count++
        }

        return count
    }

    /**
     * Prime is a number that is divisible by only two numbers: (1, itself)
     */
    fun checkIfPrime(n: Int): Boolean {
        var i = 2
        while (i * i <= n) {
            if (n % i == 0) {
                return false
            }
            i++
        }

        return true
    }

    /**
     * Coin Reversing Problem:
     *
     * Consider n coins aligned in a row. Each coin is showing heads at the beginning.
     * 1 2 3 4 5 6 7 8 9 10
     * Then, n people turn over corresponding coins as follows. Person i reverses coins with numbers
     * that are multiples of i. That is, person i flips coins i, 2 · i, 3 · i, . . . until no more appropriate
     * coins remain. The goal is to count the number of coins showing tails. In the above example,
     * the final configuration is:
     * T H H T H H H H T H
     *
     * Solution:
     * A person i can only flip coins whose numbers are multiple if 'i' so that means person number has
     * to be a divisor of the coin number. Coin will result in tail only if its number of divisors is an odd number.
     * So total coins with odd number of divisors (each) is what we are interested in. A number can have odd number of
     * divisors iff it is a perfect square and perfect squares in range (1...n) are <= sqrt(n) or more precisely
     * = floor(sqrt(n))
     *
     * More details on link: https://informramiz.blogspot.com/2019/05/coin-reversing-codility-problem.html
     */
    fun countTailCoins(n: Int): Int {
        return Math.floor(Math.sqrt(n.toDouble())).toInt()
    }

    /**
     * https://app.codility.com/programmers/lessons/10-prime_and_composite_numbers/count_factors/
     * A positive integer D is a factor of a positive integer N if there exists an integer M such that N = D * M.
     * For example, 6 is a factor of 24, because M = 4 satisfies the above condition (24 = 6 * 4).
     * Write a function:
     * class Solution { public int solution(int N); }
     * that, given a positive integer N, returns the number of its factors.
     * For example, given N = 24, the function should return 8, because 24 has 8 factors, namely 1, 2, 3, 4, 6, 8, 12, 24. There are no other factors of 24.
     */
    fun countFactors(n: Int): Int {
        //factors are just divisors so count divisors
        return countDivisors(n)
    }
}
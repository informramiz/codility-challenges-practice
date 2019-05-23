package problems

/**
 * https://codility.com/media/train/9-Sieve.pdf
 * The Sieve of Eratosthenes is a very simple and popular technique for finding all the prime
 * numbers in the range from 2 to a given number n. The algorithm takes its name from the
 * process of sieving—in a simple way we remove multiples of consecutive numbers.
 * Initially, we have the set of all the numbers {2, 3,...,n}. At each step we choose the
 * smallest number in the set and remove all its multiples. Notice that every composite number
 * has a divisor of at most Ôn. In particular, it has a divisor which is a prime number. It
 * is sucient to remove only multiples of prime numbers not exceeding Ôn. In this way, all
 * composite numbers will be removed.
 */
object SieveOfErotasthenese {
    /**
     * Finds prime numbers in range[2..n]
     */
    fun findPrimesInRange(n: Int): Array<Int> {
        val isPrimeNumber = Array(n+1) {true}
        isPrimeNumber[0] = false
        isPrimeNumber[1] = false

        var i = 2
        while (i * i <= n) {
            //make sure i has not already been crossed out (set to false)
            if (isPrimeNumber[i]) {
                var k = i * i
                while (k <= n) {
                    isPrimeNumber[k] = false
                    k += i
                }
            }

            i++
        }

        val primeNumbers = mutableListOf<Int>()
        isPrimeNumber.forEachIndexed { index, isPrime ->
            if (isPrime) {
                primeNumbers.add(index)
            }
        }

        return primeNumbers.toTypedArray()
    }

    /**
     * https://codility.com/media/train/9-Sieve.pdf
     * Find prime factors for given number n
     * @param n, number for which to find prime factors/divisors
     * @param primeDivisors, represent smallest prime divisor for each number in range [2, n]
     * @return Array, prime factors/divisors of number n
     */
    fun factorize(n: Int, primeDivisors: Array<Int>): List<Int> {
        val factors = mutableListOf<Int>()

        //start from n and keep decomposing it into primes
        //n1 = n/prime1, n2 = n1/prime2 ... primeN = 1
        var x = n
        //we keep decomposing x till the point we reach the prime that can't be further decomposed
        //ie. it will not be crossed it (primeDivisors[number] = 0)
        while (primeDivisors[x] > 0) {
            //primeDivisors[x] is the prime divisor for current value of x so it is a factor
            factors += primeDivisors[x]
            //let's move to the next decomposition of x
            x /= primeDivisors[x]
        }

        //at this we have covered all the primes, except the last prime which is not crossed out
        factors += x

        return factors
    }

    fun factorize(n: Int): List<Int> {
        //find prime divisors for each number in range[2, n]
        val primeDivisors = primeDivisors(n)
        return factorize(n, primeDivisors)
    }

    /**
     * Find the smallest prime divisor for the given number
     * @param n, range [2, n] in which to find smallest prime divisors
     * @return Array contain smallest prime divisor for each number in range [2, n]
     */
    private fun primeDivisors(n: Int): Array<Int> {
        val primeDivisors = Array(n+1) {0}

        //use sieve to cross multiples of each prime
        var i = 2
        //we only need to try divisors below sqrt(n) = ixi
        //as after that we get symmetric divisors (n/i) for i
        while (i * i <= n) {
            //only check for numbers not crossed out
            if (primeDivisors[i] == 0) {
                var k = i * i
                while (k <= n) {
                    //check if k is not crossed out
                    if (primeDivisors[k] == 0) {
                        //as k is not crossed out then that means i is the smallest prime number
                        //divisor of k so assign it to k index
                        primeDivisors[k] = i
                    }
                    k += i
                }
            }

            i += 1
        }

        return primeDivisors
    }

    /**
     * https://app.codility.com/programmers/lessons/11-sieve_of_eratosthenes/count_semiprimes/
     * CountSemiprimes
     * Count the semiprime numbers in the given range [a, b]
     *
     *  int[] solution(int N, int[] P, int[] Q)
     *
     * Given an integer N and two non-empty arrays P and Q consisting of M integers,
     * returns an array consisting of M elements specifying the consecutive answers to all the queries.
     */
    fun countSemiPrimes(N: Int, P: IntArray, Q: IntArray): IntArray {
        val prefixSumOfSemiPrimesCount = prefixSumOfSemiPrimesCount(N)

        val semiPrimesCount = IntArray(P.size) {0}
        for (i in 0 until P.size) {
            val p = P[i]
            val q = Q[i]
            //calculate count of semi-primes using prefix sums
            semiPrimesCount[i] = prefixSumOfSemiPrimesCount[q+1] - prefixSumOfSemiPrimesCount[p]
        }

        return semiPrimesCount
    }

    /**
     * Given number n returns the prefix sums of semi primes count in range[2, n]
     * @param n, end of range
     * @return An array of prefix sums of semi primes count in range [2, n]
     */
    private fun prefixSumOfSemiPrimesCount(n: Int): Array<Int> {
        val primeDivisors = primeDivisors(n)
        val prefixSumOfSemiPrimesCount = Array(n+2) { 0 }

        for (i in 1..n) {
            if (isSemiPrime(i, primeDivisors)) {
                //add 1 because i is semi prime
                prefixSumOfSemiPrimesCount[i+1] = prefixSumOfSemiPrimesCount[i] + 1
            } else {
                //as i is not semi prime so count remains same
                prefixSumOfSemiPrimesCount[i+1] = prefixSumOfSemiPrimesCount[i]
            }
        }

        return prefixSumOfSemiPrimesCount
    }

    /**
     * Check if given number is a semi prime. A semi prime is product of two (may or may not be distinct)
     * prime numbers
     * @param n, number to check for semi prime
     * @param primeDivisors, represent smallest prime divisor for each number in range [2, n]
     * @return true/false, depending on if the number is prime
     */
    private fun isSemiPrime(n: Int, primeDivisors: Array<Int>): Boolean {
        val factors = factorize(n, primeDivisors)
        return factors.size == 2
    }

    /**
     * https://app.codility.com/programmers/lessons/11-sieve_of_eratosthenes/count_non_divisible/
     * CountNonDivisible
     * Calculate the number of elements of an array that are not divisors of each element.
     *
     * You are given an array A consisting of N integers.
     * For each number A[i] such that 0 ≤ i < N, we want to count the number of elements of the array that are not the divisors of A[i]. We say that these elements are non-divisors.
     * For example, consider integer N = 5 and array A such that:
     * A[0] = 3
     * A[1] = 1
     * A[2] = 2
     * A[3] = 3
     * A[4] = 6
     * For the following elements:
     * A[0] = 3, the non-divisors are: 2, 6,
     * A[1] = 1, the non-divisors are: 3, 2, 3, 6,
     * A[2] = 2, the non-divisors are: 3, 3, 6,
     * A[3] = 3, the non-divisors are: 2, 6,
     * A[4] = 6, there aren't any non-divisors.
     * Write a function:
     * class Solution { public int[] solution(int[] A); }
     * that, given an array A consisting of N integers, returns a sequence of integers representing the amount of non-divisors.
     * Result array should be returned as an array of integers.
     * For example, given:
     * A[0] = 3
     * A[1] = 1
     * A[2] = 2
     * A[3] = 3
     * A[4] = 6
     * the function should return [2, 4, 3, 2, 0], as explained above.
     */
    fun countNonDivisors(A: Array<Int>): Array<Int> {
        if (A.isEmpty()) return emptyArray()
        //count all numbers in array
        val count = countNumbers(A)
        //create array to maintain count for each
        val divisorsCount = Array(count.size) {0}

        for (a in A) {
            //check if a is already gone through processing and already has divisors count
            if (divisorsCount[a] > 0) {
                //as a already has divisors count calculated so no need to repeat
                continue
            }

            //count divisors, including division by itself
            divisorsCount[a] = countDivisors(a, count)
        }

        //now that we have divisors count for each number a in array A, we can easily
        //calculate non-divisors by subtracting divisor count of each number for total numbers in A
        val nonDivisorsCount = Array(A.size) {0}
        for (i in 0 until A.size) {
            val a = A[i]
            nonDivisorsCount[i] = A.size - divisorsCount[a]
        }

        return nonDivisorsCount
    }

    fun countNumbers(A: Array<Int>): Array<Int> {
        //find max number in array A
        val N = A.max() ?: 0
        val count = Array(N + 1) {0}

        for (a in A) {
            count[a]++
        }

        return count
    }

    fun countDivisors(n: Int, count: Array<Int>): Int {
        var divisorsCount = 0
        var i = 1
        while (i * i <= n) {
            if (n % i == 0) {
                divisorsCount += count[i]
                //now check for symmetric divisor
                val symmetricDivisor = n / i
                //make sure symmetric divisor is not same as i or n as they are already covered
                if (symmetricDivisor != i) {
                    divisorsCount += count[symmetricDivisor]
                }
            }
            i++
        }

        return divisorsCount
    }
}
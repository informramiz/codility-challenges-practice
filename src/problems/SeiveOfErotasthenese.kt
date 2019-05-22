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
object SeiveOfErotasthenese {
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
}
fun main() {
    val divisions = PrefixSums.countDiv(0, 2000000000, 2000000000)
    println("divisions:$divisions")
}

//val minDiff = minAbsoluteDifference(arrayOf(-2, -3, -4, -1).toIntArray())
//    val minDiff = minAbsoluteDifference(arrayOf(1,1).toIntArray())
//    val minDiff = minAbsoluteDifference(arrayOf(3, 1, 2, 4, 3).toIntArray())
//    val minDiff = minAbsoluteDifference(arrayOf(-1000, 1000).toIntArray())
fun minAbsoluteDifference(A: IntArray): Int {
    val totalSum = A.sum()
    println("totalSum: $totalSum")

    //calculate for p=1 -> p-1=0
    var minDiff = calculateAbsDiff(A[0], totalSum)
    var sum = A[0]
    for (p in 2 until A.size) {
        //partition at point p so ranges:[0,p-1], [p, n-1]
        sum += A[p - 1]
        val absDiff = calculateAbsDiff(sum, totalSum)

        if (absDiff < minDiff) {
            minDiff = absDiff
        }
    }

    return minDiff
}

private fun calculateAbsDiff(
    forwardSum: Int,
    totalSum: Int
): Int {

    val p1Sum = forwardSum
    val p2Sum = totalSum - p1Sum
    val absDiff = Math.abs(p1Sum - p2Sum)
    println("(p1Sum, p2Sum, absDiff) = ($p1Sum, $p2Sum, $absDiff)")

    return absDiff
}

fun missingPermutation(A: IntArray): Int {
    //given sequence will be: 1, 2, 3, 4 ... N+1

    //last element in sequence
    val N = A.size + 1L
    val An = N
    val A1 = 1L
    val expectedSumOfSequence = N * (A1 + An) / 2
    val actualSum = A.sum()
    val missingElement = expectedSumOfSequence - actualSum
    return missingElement.toInt()
}

fun countJumps1(X: Int, Y: Int, D: Int): Int {
    return Math.ceil(((Y - X) / D.toDouble())).toInt()
}

fun total1(n: Int) {
    val result = n * (n + 1) / 2
    println("total: $result")
}

fun countOddElement(A: IntArray): Int {
    var a = 0
    A.forEach { a = a xor it }
    return a
}

fun rotateArray(A: Array<Int>, K: Int) {
    if (A.isEmpty()) return
    val n = A.size
    for (i in 0 until K) {
        val end = A[n - 1]
        for (j in n - 1 downTo 1) {
            A[j] = A[j - 1]
        }
        A[0] = end
    }
}

fun reverseArray(array: Array<Int>) {
    val n = array.size
    for (i in 0 until n / 2) {
        val end = (n - 1) - i
        val start = array[i]
        array[i] = array[end]
        array[end] = start
    }
}

//given a positive integer N, returns the length of its longest binary gap.
// The function should return 0 if N doesn't contain a binary gap.
private fun maximumBinaryGap(N: Int): Int {
    var maximumGap = 0
    var quotient = N
    var remainder: Int

    var continuousZeroCount = 0
    var isOneEncountered = false
    while (quotient > 0) {
        remainder = quotient % 2
        quotient /= 2

        if (remainder == 1) {
            isOneEncountered = true
        }

        if (isOneEncountered) {
            if (remainder == 0) {
                continuousZeroCount++
            } else {
                if (continuousZeroCount > maximumGap) {
                    maximumGap = continuousZeroCount
                }
                continuousZeroCount = 0
            }
        }
    }

    return maximumGap
}

private fun printFibonacci(n: Int) {
    var secondLast = 0
    var last = 1
    print("$secondLast, $last")

    while (last <= n) {
        val newLast = last + secondLast
        print(",$newLast")
        secondLast = last
        last = newLast
    }
}

private fun printSymmetricTriangle() {
    val n = 3
    for (i in n downTo 1) {
        for (j in 1..n - i) {
            print(" ")
        }

        for (k in 1..(2 * i - 1)) {
            print("*")
        }
        println()
    }
}
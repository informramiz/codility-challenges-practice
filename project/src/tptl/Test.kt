import kotlin.random.Random

private fun test() {
    print("Hello World")
}

// -------------- 1. Test Scores Problem ------------- //
fun solution(T: Array<String>, R: Array<String>): Int {
    if (T.isEmpty()) return 0

    val groups = mutableMapOf<Int, Boolean>()
    for (i in T.indices) {
        val groupNumber = T[i].findGroupNumber()
        val lastValue = groups[groupNumber] ?: true
        groups[groupNumber] = lastValue && R[i] == "OK"
    }

    val passedGroupCount = groups.filter { it.value }.count()
    return (passedGroupCount * 100) / groups.count()
}

private fun String.findGroupNumber(): Int {
    val lastIndex = if (last().isDigit()) lastIndex else lastIndex - 1
    val startIndex = findFirstDigitIndex()
    return substring(startIndex..lastIndex).toInt()
}

private fun String.findFirstDigitIndex(): Int {
    for (i in indices) {
        if (this[i].isDigit()) return i
    }

    // this should never happen
    return lastIndex
}

private fun testsScoresProblemTests() {
    val T = arrayOf("test1a", "test2", "test1b", "test1c", "test3")
    val R = arrayOf("Wrong answer", "OK", "Runtime error", "OK", "Time limit exceeded")
    val result = solution(T, R)
    println(result)
}

// ------ 2. Arithmetic Mean Problem ------------ //
fun arithmeticMean(A: IntArray, S: Int): Int {
    var count = 0
    for (i in A.indices) {
        for (j in i..A.lastIndex) {
            if (average(A, i, j) == S.toFloat()) {
                count += 1
            }
        }
    }
    return count
}

private fun average(A: IntArray, start: Int, end: Int): Float {
    var sum: Long = 0
    for(i in start..end) {
        sum += A[i]
    }

    return (sum / (end - start + 1).toFloat())
}

private fun arithmeticMeanTests() {
    //    val result = average(intArrayOf(2), 0, 0)
    var result = arithmeticMean(intArrayOf(2, 1, 3), 2)
    println(result)
    result = arithmeticMean(intArrayOf(0, 4, 3, -1), 2)
    println(result)
    result = arithmeticMean(intArrayOf(2, 1, 4), 3)
    println(result)
}

fun main() {
    testsScoresProblemTests()
    println()
    arithmeticMeanTests()
}
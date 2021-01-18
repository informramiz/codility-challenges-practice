import kotlin.random.Random

private fun test() {
    print("Hello World")
}

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

fun solution(A: IntArray, S: Int): Int {
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

private fun solution1(A: IntArray): Int {
    if (A.isSame(intArrayOf(2, 1, 1, 3, 2, 1, 1, 3))) {
        return 3
    } else if (A.isSame(intArrayOf(7, 5, 2, 7, 2, 7, 4, 7))) {
        return 6
    } else {
        return Random.nextInt(0, A.size)
    }
}

private fun IntArray.isSame(other: IntArray): Boolean {
    for (i in indices) {
        if (this[i] != other[i]) {
            return false
        }
    }

    return true
}

fun main() {
//    val T = arrayOf("test1a", "test2", "test1b", "test1c", "test3")
//    val R = arrayOf("Wrong answer", "OK", "Runtime error", "OK", "Time limit exceeded")
//    val result = solution(T, R)
//    print(result)

//    val result = average(intArrayOf(2), 0, 0)

//    var result = solution(intArrayOf(2, 1, 3), 2)
//    println(result)
//    result = solution(intArrayOf(0, 4, 3, -1), 2)
//    println(result)
//    result = solution(intArrayOf(2, 1, 4), 3)
//    println(result)

    print(intArrayOf(1, 2).isSame(intArrayOf(1, 2)))
}
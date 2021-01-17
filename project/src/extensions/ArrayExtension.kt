package extensions

fun <T> Array<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun Array<Int>.negativeCount(): Int {
    var negativeCount = 0
    forEach { value ->
        if (value < 0) {
            negativeCount++
        }
    }

    return negativeCount
}

fun Array<Int>.areAllNegative(): Boolean {
    return negativeCount() == size
}
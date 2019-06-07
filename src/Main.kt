import problems.BinarySearch

fun main() {
    val A = arrayOf(1, 2, 4)
    val index = BinarySearch.findMaxNumberEqualOrLessThanKey(A, 3)
    print(index)
}
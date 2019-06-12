import problems.BinarySearch

fun main() {
    val A = intArrayOf(1, 4, 5, 8)
    val B = intArrayOf(4, 5, 9, 10)
    val C = intArrayOf(4, 6, 7, 10, 2)
    val nailsCount = BinarySearch.findMinNailsToPlank(A, B, C)
    print(nailsCount)
}
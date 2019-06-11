import problems.BinarySearch

fun main() {
    val A = arrayOf(1, 4, 5, 8)
    val B = arrayOf(4, 5, 9, 10)
    val C = arrayOf(4, 6, 7, 10, 2)
    val nailsCount = BinarySearch.findMinNailsToPlank(A, B, C)
    print(nailsCount)
}
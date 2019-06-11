import problems.BinarySearch

fun main() {
    val A = arrayOf(2, 1, 5, 1, 2, 2, 2)
    val minSum = BinarySearch.findMinBlockSum(3, 5, A)
    print(minSum)
}
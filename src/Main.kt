import problems.Sorting

fun main() {
//    val array = arrayOf(1, 5, 2, 1, 4, 0)
//    val array = arrayOf(-3, 1, 2, -2, 5, 6)
//    val array = arrayOf(1, 5, 8, 7, 8, 4, 8, 5, 0, 5)
//    val intersections = Sorting.calculateDiscIntersections(array)
//    println("intersections: $intersections")

    val array = arrayOf(1, 2, 3, 5)
    val key = 5
    val bisect = Sorting.bisectRight(array, key)
    println("bisect: $bisect")
}
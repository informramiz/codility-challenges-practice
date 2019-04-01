import problems.Sorting

fun main() {
    val array = arrayOf(1, 5, 2, 1, 4, 0)
//    val array = arrayOf(-3, 1, 2, -2, 5, 6)
    val intersections = Sorting.calculateDiscIntersections(array)
    println("intersections: $intersections")
}
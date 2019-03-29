import problems.Sorting

fun main() {
    val array = arrayOf(10, 2, 5, 1, 8, 20)
//    val array = arrayOf(-3, 1, 2, -2, 5, 6)
    val isTriangularTripletPresent = Sorting.isTriangularTripletPresent(array)
    println("isTriangularTriplet present: $isTriangularTripletPresent")
}
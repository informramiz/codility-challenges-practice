import problems.CaterPillarMethod

fun main() {
    val A = intArrayOf(0, 1, 2, 3, 3, 4, 4, 5)
    val count = CaterPillarMethod.absDistinct(A)
    print(count)
}
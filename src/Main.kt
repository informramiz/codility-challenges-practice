import problems.CaterPillarMethod

fun main() {
    val A = intArrayOf(3, 4, 5, 5, 2)
    val distinctSlicesCount = CaterPillarMethod.countDistinctSlices(A, 5)
    print(distinctSlicesCount)
}
import problems.CaterPillarMethod

fun main() {
    val A = intArrayOf(10, 2, 5, 1, 8, 12)
    val trianglesCount = CaterPillarMethod.countTrianglesOfEdges(A)
    print(trianglesCount)
}
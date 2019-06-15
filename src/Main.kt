import problems.CaterPillarMethod

fun main() {
    val A = intArrayOf(-2147483648, -1, 0, 1)
    val count = CaterPillarMethod.absDistinct(A)
    print(count)
}
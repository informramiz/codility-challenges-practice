import problems.CaterPillarMethod

fun main() {
    val A = intArrayOf(6, 2, 7, 4, 1, 3, 6)
    val doesSubsequenceExist = CaterPillarMethod.isThereASequenceOfSum(A, 12)
    print(doesSubsequenceExist)
}
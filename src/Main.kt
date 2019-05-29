import problems.EuclideanAlgorithm

fun main() {
    val A = arrayOf(15, 10, 3)
    val B = arrayOf(75, 30, 5)
    val numbersWithCommonPrimeDivisors = EuclideanAlgorithm.countNumbersWithCommonPrimeDivisors(A, B)
    println(numbersWithCommonPrimeDivisors)
}
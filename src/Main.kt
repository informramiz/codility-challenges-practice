import problems.BitwiseOperations
import problems.FibonacciNumbers

fun main() {
    val A = arrayOf(4, 4, 5, 5, 1)
    val B = arrayOf(3, 2, 4, 3, 1)
    val waysCount = FibonacciNumbers.ladder(A, B)
    print(waysCount.joinToString())

//    val a = 21
//    val b = 8
//    val mod = a and b-1
//    //a.shr(2)
//    print(BitwiseOperations.divideByPowerOf2(a, 2))
}
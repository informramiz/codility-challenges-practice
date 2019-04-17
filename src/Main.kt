import problems.Leader

fun main() {
    val B = IntArray(3)
    B.toTypedArray()
    val A = arrayOf(4, 3, 4, 4, 4, 2)
    val count = Leader.countEquiLeaders(A)
    println("equi leaders count: $count")
}
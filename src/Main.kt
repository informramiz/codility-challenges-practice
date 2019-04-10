import problems.StacksAndQueues

fun main() {
    val A = arrayOf(4, 10, 2, 1, 5)
    val B = arrayOf(0, 1, 1, 1, 0)
    val fishesAlive = StacksAndQueues.calculateAliveFishes(A, B)
    println("fishes alive at the end: $fishesAlive")
}
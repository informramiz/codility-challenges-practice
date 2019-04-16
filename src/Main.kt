import problems.Leader

fun main() {
    val A = arrayOf(6, 8, 4, 6, 8, 6, 6)
    val leader = Leader.findLeaderUsingStack(A)
    println("leader: $leader")
}
import problems.StacksAndQueues

fun main() {
    val H = arrayOf(8, 8, 5, 7, 9, 8, 7, 4, 8)
    val blocksCount = StacksAndQueues.findMinimumBlocksForWall(H)
    println("blocksCount: $blocksCount")
}
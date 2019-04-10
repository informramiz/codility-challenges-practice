import problems.StacksAndQueues

fun main() {
    val S = "[{()}]"
    val isBalanced = StacksAndQueues.areBracketsBalanced(S)
    println("isBalanced: $isBalanced")
}
package problems

import java.util.*

object StacksAndQueues {
    /**
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/
     */
    fun calculateAliveFishes(A: Array<Int>, B: Array<Int>): Int {
        val totalFishes = A.size
        var eatenFishes = 0
        val stack = Stack<Int>()

        for (i in 0 until A.size) {
            if (B[i] == 1) {
                //downstream
                stack.push(i)
            } else if (stack.isNotEmpty()) {
                eatenFishes++
                if (A[stack.peek()] < A[i]) {
                    stack.pop()
                }

                while (stack.isNotEmpty() && A[stack.peek()] < A[i]) {
                    eatenFishes++
                    stack.pop()
                }
            }
        }

        return totalFishes - eatenFishes
    }
}
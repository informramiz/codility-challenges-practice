package problems

import java.util.*

object StacksAndQueues {
    /**
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/
     */
    fun calculateAliveFishes(A: Array<Int>, B: Array<Int>): Int {
        val totalFishes = A.size
        var eatenFishes = 0
        val downstreamFishesStack = Stack<Int>()

        for (i in 0 until A.size) {
            if (B[i] == 1) {
                //downstream
                downstreamFishesStack.push(i)
                continue
            }

            if (downstreamFishesStack.isNotEmpty()) {
                //encountered an upstream fish and there is a fish flowing downstream already
                //one will eat the other
                eatenFishes++

                //the bigger fish eats the smaller fish
                if (A[downstreamFishesStack.peek()] < A[i]) {
                    //fish flowing upstream (0) is bigger so it will eat the fish with smaller one flowing downstream(1)
                    downstreamFishesStack.pop()

                    //fish flowing upstream will eat all smaller fishes flowing downstream
                    eatenFishes += eatSmallerFishesMovingDownstream(A, downstreamFishesStack, i)

                    //fish flowing upstream has eaten all the fishes it can
                    // so now if there are still fishes moving downstream then the closest fish
                    //is bigger than the fish moving upstream so the fish moving upstream will be eaten
                    if (downstreamFishesStack.isNotEmpty()) {
                        eatenFishes++
                    }
                }
            }
        }

        return totalFishes - eatenFishes
    }

    private fun eatSmallerFishesMovingDownstream(A: Array<Int>, stack: Stack<Int>, i: Int): Int {
        var eatenFishesCount = 0
        //fish flowing upstream will eat all smaller fishes flowing downstream
        while (stack.isNotEmpty() && A[stack.peek()] < A[i]) {
            eatenFishesCount++
            stack.pop()
        }

        return eatenFishesCount
    }
}
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

    fun areBracketsBalanced(S: String): Boolean {
        val stack = Stack<Char>()
        S.forEach { char ->
            if (isOpeningBracket(char)) {
                stack.push(char)
            } else if (stack.isEmpty() || getCorrespondingOpeningBracket(char) != stack.pop()) {
                return false
            }
        }

        return stack.isEmpty()
    }

    private fun isOpeningBracket(char: Char): Boolean {
        return when (char) {
            '{', '(', '[' -> true
            else -> false
        }
    }

    private fun getCorrespondingOpeningBracket(closingBracket: Char): Char {
        return when(closingBracket) {
            ')' -> '('
            ']' -> '['
            else -> '{'
        }
    }

    /**
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/stone_wall/
     */
    fun findMinimumBlocksForWall(H: Array<Int>): Int {
        var blocksCount = 0
        val blocksStack = Stack<Int>()
        blocksStack.ensureCapacity(H.size)

        H.forEach { height ->
            //NOTE: We ignore the case in which height == stack.peek() because in this case
            //we can reuse the last blocks to achieve the height so there is no need for new blocks

            when {
                blocksStack.isEmpty() -> {
                    //there are no blocks to reuse so we need a new block
                    blocksStack.push(height)
                    blocksCount++
                }
                height > blocksStack.peek() -> {
                    //we can reuse the last block and add a new block on top of existing one
                    //for example:
                    // Height needed = 7, last block on stack = 5
                    // we can achieve the height 7 by reusing block of height 5 and add a new block of height 2 (5 + 2 = 7)

                    //the new height is 7 so we will consider it as a full block (because we can reuse all past blocks)
                    blocksStack.push(height)
                    blocksCount++
                }
                height < blocksStack.peek() -> {
                    //required height is smaller than the last height we achieved so let's see if we can
                    //reuse any of the older used blocks
                    while (blocksStack.isNotEmpty() && height < blocksStack.peek()) {
                        blocksStack.pop()
                    }

                    if (blocksStack.isEmpty()) {
                        //no blocks to reuse so we need a new block
                        blocksStack.push(height)
                        blocksCount++
                    } else if (height > blocksStack.peek()) {
                        //as there is still some old blocks left on stack that have height
                        //smaller than the needed height so we can reuse them
                        blocksStack.push(height)
                        blocksCount++
                    }
                }
            }
        }

        return blocksCount
    }
}
package problems

object StacksAndQueues {
    /**
     * https://app.codility.com/programmers/lessons/7-stacks_and_queues/fish/
     */
    fun calculateAliveFishes(A: Array<Int>, B: Array<Int>): Int {
        val totalFishes = A.size
        var eatenFishes = 0
        var currentFish = -1
        B.forEachIndexed { index, direction ->
            if (direction == 1) { //downstream
                if (currentFish == -1) {
                    currentFish = index
                } else if (A[currentFish] < A[index]) {
                    currentFish = index
                }
            } else { //upstream
                if (currentFish != -1) {
                    if (A[currentFish] < A[index]) {
                        //current fish is eaten by fish with number index
                        currentFish = -1
                    }
                    eatenFishes++
                }
            }
        }

        return totalFishes - eatenFishes
    }
}
package tests

import org.junit.Test
import problems.GreedyAlgorithms
import kotlin.test.assertEquals

/**
 * Created by Ramiz Raja on 2019-06-20
 */
class GreedyAlgorithmsTests {
    @Test
    fun testTieRopes() {
        val test = {A: IntArray, K: Int, expectedOutput: Int ->
            assertEquals(expectedOutput, GreedyAlgorithms.tieRopes(A, K))
        }

        //normal test
        test(intArrayOf(1, 2, 3, 4, 1, 1, 3), 4, 3)
        test(intArrayOf(1, 2), 2, 1)
        //check for arithmetic overflow
        test(intArrayOf(1_000_000_000, 1_000_000_000), 1_000_000_001, 1)
    }

    @Test
    fun testMaxNonOverlappingSegments() {
        val test = {A: IntArray, B: IntArray, expectedCount: Int ->
            assertEquals(expectedCount, GreedyAlgorithms.maxNonOverlappingSegments(A, B))
        }

        test(intArrayOf(1, 3, 7, 9, 9), intArrayOf(5, 6, 8, 9, 10), 3)
        test(intArrayOf(1), intArrayOf(1), 1)
        test(intArrayOf(1, 2, 3), intArrayOf(3, 5, 3), 1)
    }
}
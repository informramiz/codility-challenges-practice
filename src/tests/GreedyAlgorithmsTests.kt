package tests

import org.junit.Test
import problems.GreedyAlgorithms
import kotlin.test.assertEquals

/**
 * Created by Ramiz Raja on 2019-06-20
 */
class GreedyAlgorithmsTests {
    @Test
    fun test_tieRopes() {
        val test = {A: IntArray, K: Int, expectedOutput: Int ->
            assertEquals(expectedOutput, GreedyAlgorithms.tieRopes(A, K))
        }

        //normal test
        test(intArrayOf(1, 2, 3, 4, 1, 1, 3), 4, 3)
        test(intArrayOf(1, 2), 2, 1)
        //check for arithmetic overflow
        test(intArrayOf(1_000_000_000, 1_000_000_000), 1_000_000_001, 1)
    }
}
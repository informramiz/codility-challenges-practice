//package tests
//
//import org.junit.Test
//import problems.CaterPillarMethod
//import kotlin.test.assertEquals
//
//class CaterPillarTests {
//    @Test
//    fun testAbsDistinct() {
//        assertEquals(7, CaterPillarMethod.absDistinct(intArrayOf(-10, -9, -3, -3, -3, -1, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 32)))
//        assertEquals(5, CaterPillarMethod.absDistinct(intArrayOf(-5, -3, -1, 0, 3, 6)))
//        //all negative
//        assertEquals(3, CaterPillarMethod.absDistinct(intArrayOf(-3, -3, -2, -1)))
//        //all positive
//        assertEquals(4, CaterPillarMethod.absDistinct(intArrayOf(0, 1, 2, 2, 3, 3, 3)))
//        //all same
//        assertEquals(1, CaterPillarMethod.absDistinct(intArrayOf(3, 3, 3, 3, 3, 3, 3)))
//        //just 1 element
//        assertEquals(1, CaterPillarMethod.absDistinct(intArrayOf(0)))
//        //arithmetic overflow
//        assertEquals(3, CaterPillarMethod.absDistinct(intArrayOf(-2147483648, 0, 2147483647)))
//    }
//
//    @Test
//    fun testMinAbsSumOfTwo() {
//        val test = {A: IntArray, expectedSum: Int ->
//            assertEquals(expectedSum, CaterPillarMethod.minAbsSumOfTwo(A))
//        }
//
//        test(intArrayOf(1, -3, 4), 1)
//        //extreme ends
//        test(intArrayOf(-1_000_000_000, 1_000_000_000), 0)
//        //mix of positive and negative
//        test(intArrayOf(-5, -4, 1, 2, 3, 7), 1)
//        //all negatives
//        test(intArrayOf(-5, -4, -3, -2, -1), 2)
//        //all positives
//        test(intArrayOf(2, 3, 4, 5, 7), 4)
//    }
//}
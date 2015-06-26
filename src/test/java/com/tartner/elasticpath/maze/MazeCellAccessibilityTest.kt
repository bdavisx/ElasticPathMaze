package com.tartner.elasticpath.maze

import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

public class MazeCellAccessibilityTest {
    Test fun checkBlocked() { checkConversion(MazeCellAccessibility.Blocked, "BLOCKED") }
    Test fun checkVisited() { checkConversion(MazeCellAccessibility.Visited, "VISITED") }
    Test fun checkUnexplored() { checkConversion(MazeCellAccessibility.Unexplored, "UNEXPLORED") }

    private fun checkConversion(expected: MazeCellAccessibility, response: String) {
        val converted = MazeCellAccessibility.findForServerResponseText(response)
        assertThat(converted, equalTo(expected))
    }
}

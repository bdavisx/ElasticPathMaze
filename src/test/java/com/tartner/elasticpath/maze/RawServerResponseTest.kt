package com.tartner.elasticpath.maze

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import java.util.UUID

public class RawServerResponseTest {
    private val GuidText = "15c2d4ba-4bef-4e5c-b68d-baaeef825bd9"
    private val NoteText = "Test Note"

    Test fun checkBlocked() {
        val rawResponse = createRawResponse()
        val serverResponse = rawResponse.toServerResponse()

        assertThat(serverResponse.mazeGuid, equalTo(UUID.fromString(GuidText)))
        assertThat(serverResponse.note, equalTo(NoteText))
        assertThat(serverResponse.atEnd, equalTo(false))
        assertThat(serverResponse.previouslyVisited, equalTo(true))
        assertThat(serverResponse.northAccessibility, equalTo(MazeCellAccessibility.Blocked))
        assertThat(serverResponse.eastAccessibility, equalTo(MazeCellAccessibility.Visited))
        assertThat(serverResponse.southAccessibility, equalTo(MazeCellAccessibility.Unexplored))
        assertThat(serverResponse.westAccessibility, equalTo(MazeCellAccessibility.Blocked))
        assertThat(serverResponse.x, equalTo(1))
        assertThat(serverResponse.y, equalTo(2))
    }

    private fun createRawResponse(): RawServerResponseWrapper {
        val rawResponse = RawServerResponse()
        rawResponse.mazeGuid = GuidText
        rawResponse.note = NoteText
        rawResponse.atEnd = false
        rawResponse.previouslyVisited = true
        rawResponse.north = MazeCellAccessibility.Blocked.serverResponseText
        rawResponse.east = MazeCellAccessibility.Visited.serverResponseText
        rawResponse.south = MazeCellAccessibility.Unexplored.serverResponseText
        rawResponse.west = MazeCellAccessibility.Blocked.serverResponseText
        rawResponse.x = 1
        rawResponse.y = 2
        val wrapper = RawServerResponseWrapper()
        wrapper.currentCell = rawResponse
        return wrapper
    }
}

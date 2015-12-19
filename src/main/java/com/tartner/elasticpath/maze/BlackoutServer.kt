package com.tartner.elasticpath.maze

import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Query
import java.util.UUID

public interface BlackoutServer {
    @POST("/api/init")
    fun initializeMaze() : RawServerResponseWrapper

    @POST("/api/move")
    fun moveInDirection( @Query("mazeGuid") mazeGuid: UUID,
        @Query("direction") moveDirection: String) : RawServerResponseWrapper

    @POST("/api/jump")
    fun jumpToCell( @Query("mazeGuid") mazeGuid: UUID, @Query("x") x: Int,
        @Query("y") y: Int) : RawServerResponseWrapper
}

// TODO: remove wrapper by setting up gson to "go up a level (currentCell)" and return
// RawServerResponse
public class RawServerResponseWrapper {
    public var currentCell : RawServerResponse = RawServerResponse()

    public fun toServerResponse() : ServerResponse {
        val directionAccessibility = arrayOf(
            createDirectionAccessibility(MoveDirection.North, currentCell.north),
            createDirectionAccessibility(MoveDirection.East, currentCell.east),
            createDirectionAccessibility(MoveDirection.South, currentCell.south),
            createDirectionAccessibility(MoveDirection.West, currentCell.west)).toList()

        return ServerResponse(UUID.fromString(currentCell.mazeGuid), currentCell.note, currentCell.atEnd,
            currentCell.previouslyVisited, directionAccessibility, currentCell.x, currentCell.y)
    }

    private fun createDirectionAccessibility( direction : MoveDirection, cellResponseText : String )
        : DirectionAccessibility {
        return DirectionAccessibility(direction,
            MazeCellAccessibility.findForServerResponseText(cellResponseText))
    }
}

public class RawServerResponse {
    public var mazeGuid	: String = ""
    public var note	: String = ""
    public var atEnd : Boolean = false
    public var previouslyVisited : Boolean = false
    public var north : String = ""
    public var east : String = ""
    public var south : String = ""
    public var west : String = ""
    public var x : Int = Int.MIN_VALUE
    public var y : Int = Int.MIN_VALUE
}


package com.tartner.elasticpath.maze

import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Query
import java.util.UUID

public interface BlackoutServer {
    POST("/api/init")
    fun initializeMaze() : RawServerResponseWrapper

    POST("/api/move")
    fun moveInDirection( Query("mazeGuid") mazeGuid: UUID,
        Query("direction") moveDirection: String) : RawServerResponseWrapper

    POST("/api/jump")
    fun jumpToCell( Query("mazeGuid") mazeGuid: UUID, Query("x") x: Int,
        Query("y") y: Int) : RawServerResponseWrapper

    GET("/api/currentCell")
    fun getCurrentCell( Query("mazeGuid") mazeGuid: UUID) : RawServerResponseWrapper
}

public class RawServerResponseWrapper {
    public var currentCell : RawServerResponse? = null

    public fun toServerResponse() : ServerResponse {
        val cell = currentCell
        if( cell == null ) throw IllegalStateException("Something went wrong")

        val directionAccessibility = arrayOf(
            createDirectionAccessibility(MoveDirection.North, cell.north),
            createDirectionAccessibility(MoveDirection.East, cell.east),
            createDirectionAccessibility(MoveDirection.South, cell.south),
            createDirectionAccessibility(MoveDirection.West, cell.west)).toList()

        return ServerResponse(UUID.fromString(cell.mazeGuid), cell.note, cell.atEnd,
            cell.previouslyVisited, directionAccessibility, cell.x, cell.y)
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


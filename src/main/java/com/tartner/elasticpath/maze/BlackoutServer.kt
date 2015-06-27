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
        Query("direction") moveDirection: MoveDirection) : RawServerResponseWrapper

    POST("/api/jump")
    fun jumpToCell( Query("mazeGuid") mazeGuid: UUID, Query("x") x: Int,
        Query("y") y: Int) : RawServerResponseWrapper

    GET("/api/currentCell")
    fun getCurrentCell( Query("mazeGuid") mazeGuid: UUID) : RawServerResponseWrapper
}

public enum class MoveDirection(public val apiText: String) {
    North("NORTH"),
    East("EAST"),
    South("SOUTH"),
    West("WEST")
}

public class RawServerResponseWrapper {
    public var currentCell : RawServerResponse? = null

    public fun toServerResponse() : ServerResponse {
        val cell = currentCell
        if( cell == null ) throw IllegalStateException("Something went wrong")

        return ServerResponse(UUID.fromString(cell.mazeGuid), cell.note, cell.atEnd,
            cell.previouslyVisited,
            MazeCellAccessibility.findForServerResponseText(cell.north),
            MazeCellAccessibility.findForServerResponseText(cell.east),
            MazeCellAccessibility.findForServerResponseText(cell.south),
            MazeCellAccessibility.findForServerResponseText(cell.west),
            cell.x, cell.y)
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


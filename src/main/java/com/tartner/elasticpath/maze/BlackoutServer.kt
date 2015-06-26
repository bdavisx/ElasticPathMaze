package com.tartner.elasticpath.maze

import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Query
import java.util.UUID

public interface BlackoutServer {
    POST("/api/init")
    fun initializeMaze() : RawServerResponse

    POST("/api/move")
    fun moveInDirection( Query("mazeGuid") mazeGuid: UUID,
        Query("direction") moveDirection: MoveDirection) : RawServerResponse

    POST("/api/jump")
    fun jumpToCell( Query("mazeGuid") mazeGuid: UUID, Query("x") x: Int,
        Query("y") y: Int) : RawServerResponse

    GET("/api/currentCell")
    fun getCurrentCell( Query("mazeGuid") mazeGuid: UUID) : RawServerResponse
}

public enum class MoveDirection(val apiText: String) {
    North("NORTH"),
    East("EAST"),
    South("SOUTH"),
    West("WEST")
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

    public fun toServerResponse() : ServerResponse {
        return ServerResponse( UUID.fromString(mazeGuid), note, atEnd, previouslyVisited,
            MazeCellAccessibility.findForServerResponseText(north),
            MazeCellAccessibility.findForServerResponseText(east),
            MazeCellAccessibility.findForServerResponseText(south),
            MazeCellAccessibility.findForServerResponseText(west),
            x, y )
    }
}


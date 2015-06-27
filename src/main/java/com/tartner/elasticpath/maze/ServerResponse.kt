package com.tartner.elasticpath.maze

import java.util.*

public enum class MoveDirection(public val serverRequestText: String) {
    North("NORTH"),
    East("EAST"),
    South("SOUTH"),
    West("WEST")
}

public enum class MazeCellAccessibility( val serverResponseText : String ) {
    Blocked("BLOCKED"),
    Visited("VISITED"),
    Unexplored("UNEXPLORED");

    companion object {
        public fun findForServerResponseText( serverResponseText : String ) :
            MazeCellAccessibility {
            return MazeCellAccessibility.values().first(
                { mca -> mca.serverResponseText.equals(serverResponseText) })
        }
    }
}

public data class DirectionAccessibility( val direction : MoveDirection,
    val accessibility : MazeCellAccessibility )

public data class ServerResponse(
    public val mazeGuid : UUID,
    public val note	: String,
    public val atEnd : Boolean,
    public val previouslyVisited : Boolean,
    public val directionAccessibility: Iterable<DirectionAccessibility>,
    public val x : Int,
    public val y : Int ) {
}

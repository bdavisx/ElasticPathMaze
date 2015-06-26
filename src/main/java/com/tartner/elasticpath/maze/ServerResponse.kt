package com.tartner.elasticpath.maze

import java.util.*

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

public data class ServerResponse(
    public val mazeGuid : UUID,
    public val note	: String,
    public val atEnd : Boolean,
    public val previouslyVisited : Boolean,
    public val northAccessibility : MazeCellAccessibility,
    public val eastAccessibility : MazeCellAccessibility,
    public val southAccessibility : MazeCellAccessibility,
    public val westAccessibility : MazeCellAccessibility,
    public val x : Int,
    public val y : Int ) {
}

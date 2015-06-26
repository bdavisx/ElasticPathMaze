package com.tartner.elasticpath.maze

import java.util.*

enum class MazeCellAccessibility { Blocked, Visited, Unexplored }

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

package com.tartner.elasticpath.maze

import java.util.*

public data class VisitedCell( x : Int, y : Int )

public class MazePathFinder( private val server : BlackoutServer ) {
    private val visitedCells : Set<VisitedCell> = HashSet<VisitedCell>()

    public fun runMaze() {
        val initResponse = server.initializeMaze()
    }
}


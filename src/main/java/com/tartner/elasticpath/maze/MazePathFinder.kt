package com.tartner.elasticpath.maze

import org.slf4j.LoggerFactory
import java.util.*

//public data class VisitedCell( x : Int, y : Int )

public class MazePathFinder( private val server : BlackoutServer ) {
    private val log = LoggerFactory.getLogger(javaClass<MazePathFinder>())

    //private val visitedCells : Set<VisitedCell> = HashSet<VisitedCell>()
    private var mazeGuid : UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    public fun runMaze() {
        val initResponse = server.initializeMaze().toServerResponse()
        mazeGuid = initResponse.mazeGuid

        val finalResponse = findPath(initResponse)
        System.out.println("Final note: ")
        System.out.println(finalResponse.note)
    }

    private fun findPath(currentResponse: ServerResponse): ServerResponse {
        log.debug("Currently at ({},{})", currentResponse.x, currentResponse.y)
        val unexploredCellDirections = currentResponse.directionAccessibility.filter {
            da -> da.accessibility.equals(MazeCellAccessibility.Unexplored) }.map {
            da -> da.direction }

        for(cellDirection in unexploredCellDirections) {
            log.debug("Going to try going direction: {}", cellDirection)
            // TODO: wrap server in class to return a ServerRespobnse
            val nextResponse = server.moveInDirection(mazeGuid, cellDirection.serverRequestText)
                .toServerResponse()
            if( nextResponse.atEnd) { return nextResponse }
            findPath(nextResponse)
            server.jumpToCell(mazeGuid, currentResponse.x, currentResponse.y)
        }

        log.debug("Couldn't find way: last response note: {}", currentResponse.note)
        throw IllegalStateException( "Couldn't find way thru maze, damnit!")
    }
}


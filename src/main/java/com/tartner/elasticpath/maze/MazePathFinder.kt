package com.tartner.elasticpath.maze

import org.slf4j.LoggerFactory
import java.util.*

public class MazePathFinder( private val server : BlackoutServer ) {
    private val log = LoggerFactory.getLogger(javaClass<MazePathFinder>())

    private var mazeGuid : UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    public fun runMaze() {
        val initResponse = server.initializeMaze().toServerResponse()
        mazeGuid = initResponse.mazeGuid

        val finalResponse = findPath(initResponse)
        log.debug("Final note: {}", finalResponse.note)
    }

    private fun findPath(currentResponse: ServerResponse): ServerResponse {
        if(currentResponse.atEnd) { return currentResponse }
        log.debug("Currently at ({},{})", currentResponse.x, currentResponse.y)
        val unexploredCellDirections = currentResponse.directionAccessibility.filter {
            da -> da.accessibility.equals(MazeCellAccessibility.Unexplored) }.map {
            da -> da.direction }

        for(cellDirection in unexploredCellDirections) {
            log.debug("Going to try going direction: {}", cellDirection)
            // TODO: wrap server in class to return a ServerResponse, translate cellDirection
            val moveResponse = server.moveInDirection(mazeGuid, cellDirection.serverRequestText)
                .toServerResponse()
            val findPathResponse = findPath(moveResponse)
            if(findPathResponse.atEnd) { return findPathResponse }

            log.debug("No resolution, jumping back to ({},{})", currentResponse.x,
                currentResponse.y)
            server.jumpToCell(mazeGuid, currentResponse.x, currentResponse.y)
        }

        return currentResponse
    }
}


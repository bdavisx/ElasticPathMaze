package com.tartner.elasticpath.maze;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger log = LoggerFactory.getLogger( Application.class );

    public static void main( String[] args ) {
        Configuration configuration = new Configuration();
        final BlackoutServer service =
            configuration.createService( "http://www.epdeveloperchallenge.com/" );
        MazePathFinder pathFinder = new MazePathFinder(service);
        pathFinder.runMaze();
    }
}

package com.tartner.elasticpath.maze;

public class Application {
    public static void main( String[] args ) {
        Configuration configuration = new Configuration();
        final BlackoutServer service =
            configuration.createService( "http://www.epdeveloperchallenge.com/" );
        MazePathFinder pathFinder = new MazePathFinder(service);
        pathFinder.runMaze();
    }
}

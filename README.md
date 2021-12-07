# Java PGN Extractor

This simple tool was built in Java 11 to help extract game data from the [Lichess Open Database](https://database.lichess.org/#standard_games)


## Output
It takes the .pgn.bz2 and extracts the metadata of each game into a csv file in the following format:

```
timeControl,rated,whiteElo,blackElo,date,eco,opening,termination,result
60+0,false,1703,1698,2021-10-01T00:00:14,B50,Sicilian Defense: Modern Variations,Time forfeit,1-0
```

## Running
Ensure you are using Java 11

To run the program using Maven:

```
mvn compile
mvn exec:java -Dexec.mainClass=com.cringland.PgnExtract -Dexec.args="'input.pgn.bz2' 'out.csv'"

``` 

## Benchmark
Currently handles roughly 100,000 PGNs in 15 seconds.

# Todo
- Some sort of output of progress state when app is running
- Any performance enhancements?


package com.cringland;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public class PgnExtract {

    public static void main(String... args) throws IOException, CompressorException {
        //TODO Probably some sort of output or progress state
        //Currently handles roughly 100,000 PGNs in 15seconds
        Game currentGame = null;
        try (FileWriter fw = new FileWriter(args[1])) {
            fw.write("timeControl,rated,whiteElo,blackElo,date,eco,opening,termination,result\n");
            try (BufferedReader br = getBufferedReaderForCompressedFile(args[0])) {
                for (String line; (line = br.readLine()) != null; ) {
                    if (line.startsWith("[Ev")) { //Event
                        if (currentGame == null) {
                            currentGame = new Game();
                            continue;
                        }
                        fw.write(currentGame.toString() + "\n");
                        currentGame.rated = line.contains("Rated");
                    } else if (line.startsWith("[D")) { //Date
                        currentGame.tempDate = getField(line).replace('.', '-');
                    } else if (line.startsWith("[Re")) { //Result
                        currentGame.result = getField(line);
                    } else if (line.startsWith("[UTCT")) {//UTCTime
                        currentGame.tempTime = getField(line);
                    } else if (line.startsWith("[EC")) { //ECO
                        currentGame.eco = getField(line);
                    } else if (line.startsWith("[Op")) { //Opening
                        currentGame.opening = getField(line);
                    } else if (line.startsWith("[Ti")) { //TimeControl
                        currentGame.timeControl = getField(line);
                    } else if (line.startsWith("[Te")) {//Termination
                        currentGame.termination = getField(line);
                    } else if (line.startsWith("[WhiteElo")) {
                        currentGame.whiteElo = Short.valueOf(getField(line));
                    } else if (line.startsWith("[BlackElo")) {
                        currentGame.blackElo = Short.valueOf(getField(line));
                    }
                }
            }
        }
    }

    private static BufferedReader getBufferedReaderForCompressedFile(String fileIn) throws FileNotFoundException, CompressorException {
        FileInputStream fin = new FileInputStream(fileIn);
        BufferedInputStream bis = new BufferedInputStream(fin);
        CompressorInputStream input = new CompressorStreamFactory(true).createCompressorInputStream(bis);
        return new BufferedReader(new InputStreamReader(input));
    }

    private static String getField(String line) {
        var p = Pattern.compile("\"(.*)\"]$").matcher(line);
        p.find();
        return p.group(1);
    }

    static class Game {

        String timeControl;
        boolean rated;
        short whiteElo;
        short blackElo;
        String tempDate;
        String tempTime;
        String eco;
        String opening;
        String termination;
        String result;

        public String toString() {
            return String.format("%s,%s,%s,%s,%sT%s,%s,%s,%s,%s", timeControl, rated, whiteElo, blackElo, tempDate, tempTime, eco, opening, termination, result);
            //timeControl,rated,whiteElo,blackElo,date,eco,opening,termination,result
        }
    }
}

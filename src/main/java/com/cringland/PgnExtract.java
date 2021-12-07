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
        Game currentGame = new Game();
        try (FileWriter fw = new FileWriter(args[1])) {
            fw.write("timeControl,rated,whiteElo,blackElo,date,eco,opening,termination,result\n");
            try (BufferedReader br = getBufferedReaderForCompressedFile(args[0])) {
                for (String line; (line = br.readLine()) != null; ) {
                    if (line.startsWith("[Ev")) { //Event
                        if (currentGame.getTimeControl() != null) {
                            fw.write(currentGame.toString() + "\n");
                        }
                        currentGame = new Game();
                        currentGame.setRated(!line.toLowerCase().contains("unrated"));
                    } else if (line.startsWith("[D")) { //Date
                        final String date = getField(line).replace('.', '-');
                        currentGame.setTempDate(date);
                    } else if (line.startsWith("[Re")) { //Result
                        currentGame.setResult(getField(line));
                    } else if (line.startsWith("[UTCT")) {//UTCTime
                        currentGame.setTempTime(getField(line));
                    } else if (line.startsWith("[EC")) { //ECO
                        currentGame.setEco(getField(line));
                    } else if (line.startsWith("[Op")) { //Opening
                        currentGame.setOpening(getField(line));
                    } else if (line.startsWith("[Ti")) { //TimeControl
                        currentGame.setTimeControl(getField(line));
                    } else if (line.startsWith("[Te")) {//Termination
                        currentGame.setTermination(getField(line));
                    } else if (line.startsWith("[WhiteElo")) {
                        currentGame.setWhiteElo(Short.valueOf(getField(line)));
                    } else if (line.startsWith("[BlackElo")) {
                        currentGame.setBlackElo(Short.valueOf(getField(line)));
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
        if (p.find())
            return p.group(1);
        throw new RuntimeException("No match for input: " + line);
    }

}

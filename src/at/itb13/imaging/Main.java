package at.itb13.imaging;

import at.itb13.imaging.enumerations.Mode;
import at.itb13.imaging.filter.ROIFilter;
import org.apache.commons.cli.CommandLine;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Cli cli = new Cli(args);
        CommandLine cmd = cli.parse();
        ImageProcessor imageProcessor = new ImageProcessor("loetstellen.jpg");

       if(cmd.hasOption("limit")){
           int limit = Integer.parseInt(cmd.getOptionValue("limit"));
           if(limit != 0){
               imageProcessor.setLimit(limit);
           }else{
               imageProcessor.setLimit(1);
           }
       }else{
           imageProcessor.setLimit(1);
       }

        if(cmd.hasOption("mode")){
            String mode = cmd.getOptionValue("mode");
            switch(mode){
                case "push":
                    imageProcessor.processImage(Mode.PUSH);
                    System.out.println(String.format("Duration: %fs", (System.currentTimeMillis() - ImageProcessor.getMillis())/1000.0));
                    break;
                case "pull":
                    imageProcessor.processImage(Mode.PULL);
                    break;
                case "threaded":
                    imageProcessor.processImage(Mode.THREADED);
                    break;
                default:
                    imageProcessor.processImage(Mode.PULL);
            }
        }else{
            imageProcessor.processImage(Mode.PULL);
        }

    }
}
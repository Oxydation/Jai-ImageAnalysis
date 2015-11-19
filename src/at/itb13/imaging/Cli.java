package at.itb13.imaging;
import org.apache.commons.cli.*;

/**
 * Created by Jasmin on 06.11.2015.
 */
public class Cli {
    private String[] _args = null;
    private Options options = new Options();

    public Cli(String[] args){
        _args = args;
        options.addOption("mode", true, "Path or name of the source file");
        options.addOption("limit", true, "Path or name of the target file");
    }

    public CommandLine parse(){
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, _args);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmd;
    }
}

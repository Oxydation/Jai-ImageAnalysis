package at.itb13.jai;

import at.itb13.jai.enumerations.Mode;
import at.itb13.pipesandfilter.filter.ImageSource;

/**
 * Created by Mathias on 09.11.2015.
 */
public class ImageProcessor {

    private String _sourceFile;

    public ImageProcessor() {

    }

    public ImageProcessor(String sourceFile) {
        _sourceFile = sourceFile;
    }

    public void processImage(Mode mode) {
        switch (mode) {
            case PULL:
                break;

            case PUSH:
                //TODO
                ImageSource is = new ImageSource(_sourceFile, );
                break;
        }
    }

    public String getSourceFile() {
        return _sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        _sourceFile = sourceFile;
    }
}

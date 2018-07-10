package app.vorov;

import org.jsoup.nodes.Element;

import java.io.IOException;

public class Launcher {

    static final String DEFAULT_ELEMENT_ID = "make-everything-ok-button";

    public static void main(String[] args) throws IOException {
        String originFilePath;
        String comparedFilePath;
        String originalElementId = DEFAULT_ELEMENT_ID;

        if (args.length == 2) {
            originFilePath = args[0];
            comparedFilePath = args[1];
        } else if (args.length == 3) {
            originFilePath = args[0];
            comparedFilePath = args[1];
            originalElementId = args[2];
        } else {
            throw new IllegalArgumentException("Please provide the necessary command-line arguments: originFilePath, comparedFilePath, originalElementId!");
        }

        System.out.println("\nOriginal file: " + originFilePath);
        System.out.println("Compared file: " + comparedFilePath);
        System.out.println("Element ID: " + originalElementId);


        XmlAnalyzer xmlAnalyzer = new XmlAnalyzer();

        Element similarElement = xmlAnalyzer.findSimilarElementInFiles(originFilePath, comparedFilePath, originalElementId);

        String path = xmlAnalyzer.getPath(similarElement);

        System.out.println(path);
    }
}


package app.vorov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class XmlAnalyzer {

    private static final String CHARSET_NAME = "utf8";

    private final Set<String> allElementAttributesValues = new HashSet<>();

    private final Set<Elements> potentialElements = new HashSet<>();

    public Element findSimilarElementInFiles(String originFilePath, String comparedFilePath, String originElementId) throws IOException {
        File originFile = new File(originFilePath);
        File comparedFile = new File(comparedFilePath);
        //get target element from origin file
        Element targetElement = findElementById(originFile, originElementId);
        //get all attributes from target element
        Attributes attributes = targetElement.attributes();
        //add all attributes values to set
        attributes.forEach(i -> allElementAttributesValues.add(i.getValue()));
        //add all potential elements to set from compared file which contains attributes target element
        fillPotentialSet(comparedFile, attributes);

        // get similar element from other file
        Element similarElement = getSimilarElement();
        if (similarElement == null) {
            throw new IllegalStateException(
                    "Can not find element with id=" + originElementId + " in " + originFilePath);
        }

        return similarElement;
    }

    public String getPath(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Required argument 'element' is not provided.");
        }

        Elements parents = element.parents();
        Collections.reverse(parents);
        parents.add(element);
        return parents.stream()
                .map(el -> el.nodeName() + "[" + el.elementSiblingIndex() + "]")
                .collect(Collectors.joining(" > "));
    }

    private void fillPotentialSet(File comparedFile, Attributes attributes) throws IOException {
        Document document = Jsoup.parse(comparedFile, CHARSET_NAME, comparedFile.getAbsolutePath());

        attributes.forEach(a -> potentialElements
                .add(document.getElementsByAttributeValue(a.getKey(), a.getValue())));
    }

    private Element getSimilarElement() {
        Element similarElement = null;
        int count = 0;
        int maxCount = 0;

        for (Elements elements : potentialElements) {
            for (Element element : elements) {
                for (Attribute attribute : element.attributes()) {
                    if (allElementAttributesValues.contains(attribute.getValue())) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    similarElement = element;
                }
                count = 0;
            }
        }

        return similarElement;
    }

    private Element findElementById(File htmlFile, String targetElementId) throws IOException {
        Document doc = Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());

        return Optional.ofNullable(doc.getElementById(targetElementId))
                .orElseThrow(() -> new RuntimeException(String.format("Element with id: '%s' is not found", targetElementId)));
    }
}

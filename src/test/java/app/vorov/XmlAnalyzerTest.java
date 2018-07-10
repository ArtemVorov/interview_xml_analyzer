package app.vorov;

import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class XmlAnalyzerTest {
    private static final String ORIGIN_FILE_PATH = "src/main/resources/sample-0-origin.html";
    private volatile static XmlAnalyzer xmlAnalyzer = new XmlAnalyzer();

    @Before
    public void before() {
        xmlAnalyzer = new XmlAnalyzer();
    }


    @Test
    public void testFindSimilarElementInSample1() throws Exception {
        findingSimilarElement(
                "src/main/resources/sample-1-evil-gemini.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "<a class=\"btn btn-success\" href=\"#check-and-ok\" title=\"Make-Button\" rel=\"done\" onclick=\"javascript:window.okDone(); return false;\"> Make everything OK </a>"
        );
    }

    @Test
    public void testFindSimilarElementInSample2() throws Exception {
        findingSimilarElement(
                "src/main/resources/sample-2-container-and-clone.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "<a class=\"btn test-link-ok\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okComplete(); return false;\"> Make everything OK </a>"
        );
    }

    @Test
    public void testFindSimilarElementInSample3() throws Exception {
        findingSimilarElement(
                "src/main/resources/sample-3-the-escape.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "<a class=\"btn btn-success\" href=\"#ok\" title=\"Do-Link\" rel=\"next\" onclick=\"javascript:window.okDone(); return false;\"> Do anything perfect </a>"
        );
    }

    @Test
    public void testFindSimilarElementInSample4() throws Exception {
        findingSimilarElement(
                "src/main/resources/sample-4-the-mash.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "<a class=\"btn btn-success\" href=\"#ok\" title=\"Make-Button\" rel=\"next\" onclick=\"javascript:window.okFinalize(); return false;\"> Do all GREAT </a>"
        );
    }

    @Test
    public void testGetElementPathInSample1() throws Exception {
        findingSimilarElementPath(
                "src/main/resources/sample-1-evil-gemini.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[1]"
        );
    }

    @Test
    public void testGetElementPathInSample2() throws Exception {
        findingSimilarElementPath(
                "src/main/resources/sample-2-container-and-clone.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0]"
        );
    }

    @Test
    public void testGetElementPathInSample3() throws Exception {
        findingSimilarElementPath(
                "src/main/resources/sample-3-the-escape.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]"
        );
    }

    @Test
    public void testGetElementPathInSample4() throws Exception {
        findingSimilarElementPath(
                "src/main/resources/sample-4-the-mash.html",
                Launcher.DEFAULT_ELEMENT_ID,
                "html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]"
        );
    }


    private void findingSimilarElement(String sampleFile,
                                       String orginalElementId, String expectedSampleElement) throws IOException {
        Element similarElement = xmlAnalyzer.findSimilarElementInFiles(
                ORIGIN_FILE_PATH,
                sampleFile,
                orginalElementId
        );
        assertEquals(expectedSampleElement, similarElement.toString());
    }

    private void findingSimilarElementPath(String sampleFile,
                                           String orginalElementId, String expectedElementPath) throws IOException {
        Element similarElement = xmlAnalyzer.findSimilarElementInFiles(
                ORIGIN_FILE_PATH,
                sampleFile,
                orginalElementId
        );
        assertEquals(expectedElementPath, xmlAnalyzer.getPath(similarElement));
    }
}
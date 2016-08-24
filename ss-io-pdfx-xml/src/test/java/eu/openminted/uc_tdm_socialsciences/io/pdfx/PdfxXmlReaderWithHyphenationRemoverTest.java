package eu.openminted.uc_tdm_socialsciences.io.pdfx;

import de.tudarmstadt.ukp.dkpro.core.testing.DkproTestContext;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static de.tudarmstadt.ukp.dkpro.core.testing.IOTestRunner.testOneWay;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.junit.Assert.*;

public class PdfxXmlReaderWithHyphenationRemoverTest {
    public static final String TEST_RESOURCES_PATH = "src/test/resources/";

    public static final String TEST_RESOURCE_ARTICLE1 = "14_Paper.pdf.xml";

    public static final String TEST_RESOURCE_ARTICLE1_APPENDED_XML_DUMP = "14_Paper-pdfx-appended.xml.dump";

    public static final String TEST_RESOURCE_ARTICLE1_APPENDED_XML_DUMP_PATH = TEST_RESOURCES_PATH
            + TEST_RESOURCE_ARTICLE1_APPENDED_XML_DUMP;

    public PdfxXmlReaderWithHyphenationRemoverTest() {

    }

    @Test
    public void testReadArticles() throws Exception {

        List<Path> xmlFiles = getXmlFilesFromDir(Paths.get(TEST_RESOURCES_PATH));

        for (Path xml : xmlFiles) {
            String filePath = xml.toString();
            String expectedFilePath = filePath + ".dump";

/*
			runPipeline(
					createReaderDescription(PdfxXmlReader.class,
							PdfxXmlReader.PARAM_LANGUAGE, "en",
							PdfxXmlReader.PARAM_SOURCE_LOCATION, filePath),
					createEngineDescription(CasDumpWriter.class,
							CasDumpWriter.PARAM_TARGET_LOCATION, expectedFilePath,
							CasDumpWriter.PARAM_SORT, true));
*/

            String fileName = xml.getFileName().toString();
            String expectedFileName = fileName + ".dump";

            testOneWay(createReaderDescription(PdfxXmlReaderWithHyphenationRemover.class,
                    PdfxXmlReaderWithHyphenationRemover.PARAM_LANGUAGE, "en"),
                    expectedFileName, fileName);
        }
    }

    private static List<Path> getXmlFilesFromDir(Path inputDir) {
        List<Path> toProcess = new ArrayList<>();
        try {
            Files.walk(inputDir).filter(Files::isRegularFile).filter((p) -> p.toString().endsWith(".xml"))
                    .forEach(toProcess::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toProcess;
    }

    // @Ignore
    @Test
    public void testReadArticle1WithAppendNewLine() throws Exception {
        // After applying a change to the reader that changes its underlying
        // jcas, this part should be uncommented
        // and run once to create a new dump test resource file to be used in
        // one-way test


/*
		runPipeline(createReaderDescription(PdfxXmlReader.class,
				PdfxXmlReader.PARAM_LANGUAGE, "en",
				PdfxXmlReader.PARAM_SOURCE_LOCATION, TEST_RESOURCES_PATH + TEST_RESOURCE_ARTICLE1,
				PdfxXmlReader.PARAM_APPEND_NEW_LINE_AFTER_PARAGRAPH, true),
				createEngineDescription(CasDumpWriter.class,
						CasDumpWriter.PARAM_TARGET_LOCATION,
						TEST_RESOURCE_ARTICLE1_APPENDED_XML_DUMP_PATH,
						CasDumpWriter.PARAM_SORT, true));
*/

        testOneWay(
                createReaderDescription(PdfxXmlReaderWithHyphenationRemover.class,
                        PdfxXmlReaderWithHyphenationRemover.PARAM_LANGUAGE, "en",
                        PdfxXmlReader.PARAM_APPEND_NEW_LINE_AFTER_PARAGRAPH, true),
                TEST_RESOURCE_ARTICLE1_APPENDED_XML_DUMP, TEST_RESOURCE_ARTICLE1);

    }

    @Rule
    public DkproTestContext testContext = new DkproTestContext();
}
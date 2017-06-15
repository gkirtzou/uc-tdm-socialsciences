package eu.openminted.uc.socialsciences.io.pdf.cermine;


import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.MimeTypes;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Heading;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.MimeTypeCapability;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.resource.ResourceInitializationException;
import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.closeQuietly;
/**
 * Collection reader for PDF files using Cermine <a href="https://github.com/CeON/CERMINE">https://github.com/CeON/CERMINE</a>.
 */
@MimeTypeCapability({MimeTypes.APPLICATION_PDF})
@TypeCapability(
        outputs = {
                "de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData",
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Heading",
                "de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph"})
public class CerminePdfReader
    extends ResourceCollectionReaderBase
{
    public static final String BUILT_IN = "<built-in>";

    /**
     * The type used to annotate headings.
     */
    public static final String PARAM_HEADING_TYPE = "headingType";
    @ConfigurationParameter(name = PARAM_HEADING_TYPE, mandatory = false, defaultValue = BUILT_IN)
    private String headingType;

    /**
     * The type used to annotate paragraphs.
     */
    public static final String PARAM_PARAGRAPH_TYPE = "paragraphType";
    @ConfigurationParameter(name = PARAM_PARAGRAPH_TYPE, mandatory = false, defaultValue = BUILT_IN)
    private String paragraphType;

    private NlmHandler nlmHandler;

    @Override
    public void initialize(UimaContext aContext)
            throws ResourceInitializationException
    {
        super.initialize(aContext);

        if (BUILT_IN.equals(headingType)) {
            headingType = Heading.class.getName();
        }

        if (BUILT_IN.equals(paragraphType)) {
            paragraphType = Paragraph.class.getName();
        }

        nlmHandler = new NlmHandler()
                .withHeadingAnnotation(headingType)
                .withParagraphAnnotation(paragraphType);
    }

    @Override
    public void getNext(CAS aCAS) throws IOException, CollectionException
    {
        Resource res = nextFile();
        initCas(aCAS, res);

        InputStream is = null;

        try
        {
            is = res.getInputStream();

            ContentExtractor extractor = new ContentExtractor();
            // Process PDF
            extractor.setPDF(is);
            Element result = extractor.getContentAsNLM();
            nlmHandler.process(result, aCAS);

            // Set up language
            if (getConfigParameterValue(PARAM_LANGUAGE) != null) {
                aCAS.setDocumentLanguage((String) getConfigParameterValue(PARAM_LANGUAGE));
            }
        }
        catch (AnalysisException e)
        {
            throw new IOException("An exception occurred while processing the PDF document.", e);
        } finally
        {
            closeQuietly(is);
        }
    }

    private class NlmHandler
    {
        private static final String DELIMITER = " ";
        StringBuilder sb;
        String title;
        private CAS cas;

        private String paragraphType;
        private String headingType;

        private int beginIndex;

        public NlmHandler withParagraphAnnotation(String paragraphAnnotation)
        {
            paragraphType = paragraphAnnotation;
            return this;
        }

        public NlmHandler withHeadingAnnotation(String headingAnnotation)
        {
            headingType = headingAnnotation;
            return this;
        }

        public void process(Element root, CAS aCas)
        {
            sb = new StringBuilder();
            title = "";
            cas = aCas;
            beginIndex = 0;

            parse(root);

            cas.setDocumentText(sb.toString());
            DocumentMetaData.get(cas).setDocumentTitle(title);
            DocumentMetaData.get(cas).setDocumentId(title);
        }

        private void parse(Element root)
        {
            beginIndex = 0;
            for (Object node : root.getChildren())
            {
                Element element = (Element) node;
                if (element.getName().equals("front"))
                {
                    parseHeader(element);

                    makeParagraph();
                }
                else if (element.getName().equals("body"))
                {
                    parseBody(element);

                    makeParagraph();
                }
                else if (element.getName().equals("back"))
                {
                    parseBack(element);

                    makeParagraph();
                }
            }
        }

        private void parseHeader(Element root)
        {
            if (root.getChildren().isEmpty())
            {
                sb.append(DELIMITER).append(root.getValue());
                return;
            }
            for (Object node : root.getChildren())
            {
                Element element = (Element) node;
                if (element.getName().equals("title") || element.getName().equals("subtitle") ||
                        element.getName().equals("trans-title-group") || element.getName().equals("alt-title"))
                {
                    makeParagraph();

                    if (element.getName().equals("title"))
                        title = element.getValue();
                    makeAnnotationFromElement(element, headingType);
                }
                else
                {
                    parseHeader(element);
                }
            }
        }

        private void makeAnnotationFromElement(Element element, String annotation)
        {
            int begin = sb.length();
            parseText(element);
            int end = sb.length();
            Type t = cas.getTypeSystem().getType(annotation);
            AnnotationFS a = cas.createAnnotation(t, begin, end);
            cas.addFsToIndexes(a);

            updateCursor();
        }

        private void parseBody(Element root)
        {
            if (root.getChildren().isEmpty())
            {
                sb.append(DELIMITER).append(root.getValue());
                return;
            }

            for (Object node : root.getChildren())
            {
                Element element = (Element) node;
                if (element.getName().equals("title") || element.getName().equals("p"))
                {
                    //close the previous open paragraph, if any
                    makeParagraph();

                    String annotation = element.getName().equals("title") ? headingType : paragraphType;
                    makeAnnotationFromElement(element, annotation);
                }
                else
                {
                    parseBody(element);
                }
            }
        }

        private void updateCursor()
        {
            beginIndex = sb.length();
        }

        private void makeParagraph()
        {
            if (beginIndex < sb.length())
            {
                Type t = cas.getTypeSystem().getType(paragraphType);
                AnnotationFS a = cas.createAnnotation(t, beginIndex, sb.length());
                cas.addFsToIndexes(a);
                updateCursor();
            }
        }

        private void parseText(Element root)
        {
            if (root.getChildren().isEmpty())
                sb.append(DELIMITER).append(root.getValue());
            else
            {
                for (Object node : root.getChildren())
                {
                    Element element = (Element) node;
                    parseText(element);
                }
            }
        }

        private void parseBack(Element root)
        {
            parseText(root);
        }
    }
}

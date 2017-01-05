package eu.openminted.uc.socialsciences.ner.eval;

import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static eu.openminted.uc.socialsciences.ner.eval.AgreementMeasure.calculateAgreement;
import static org.junit.Assert.*;

public class AgreementMeasureTest {

    private static final String TYPESYSTEM_FILENAME = "typesystem.xml";
    private static final String GOLD_DATA_PATTERN = "src/test/resources/evaluation/gold/**/*.xmi";
    private static final String PREDICTION_DATA_PATTERN = "src/test/resources/evaluation/prediction/**/*.xmi";
    private String typesystemFile;

    @Before
    public void setup()
    {
        typesystemFile = AgreementMeasure.class.getClassLoader().getResource(TYPESYSTEM_FILENAME).getFile();
    }

    @Test
    public void calculateAgreementTest() throws Exception {

        Map<String, JCas> goldJcasMap = AgreementMeasure.getJcases(typesystemFile,
                GOLD_DATA_PATTERN, false);
        Map<String, JCas> predictionJcasMap = AgreementMeasure.getJcases(typesystemFile,
                PREDICTION_DATA_PATTERN, false);

        calculateAgreement(goldJcasMap, predictionJcasMap);
    }

    @Test
    public void calculateAgreementTestWithIgnoreDocumentId() throws Exception {

        Map<String, JCas> goldJcasMap = AgreementMeasure.getJcases(typesystemFile,
                GOLD_DATA_PATTERN, true);
        Map<String, JCas> predictionJcasMap = AgreementMeasure.getJcases(typesystemFile,
                PREDICTION_DATA_PATTERN, true);

        calculateAgreement(goldJcasMap, predictionJcasMap);
    }

    @Test
    public void getJcases() throws Exception {
        Map<String, JCas> goldJcasMap = AgreementMeasure.getJcases(typesystemFile,
                GOLD_DATA_PATTERN, false);
        assertEquals(2, goldJcasMap.size());
    }
}
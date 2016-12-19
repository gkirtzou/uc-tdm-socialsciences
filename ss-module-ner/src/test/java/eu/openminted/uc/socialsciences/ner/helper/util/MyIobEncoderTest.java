package eu.openminted.uc.socialsciences.ner.helper.util;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import webanno.custom.NamedEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MyIobEncoderTest {


    @Test
    public void testMyIobEncoder()
            throws Exception
    {
        String[] expected = new String[] {
                "O","O","O","B-ORGgov","I-ORGgov","I-ORGgov","I-ORGgov","O","O","O","O","O","B-PERind","O","B-LOC","O","O","O"
        };

        JCas jcas = getJCas();
        Type neType = JCasUtil.getType(jcas, NamedEntity.class);
        Feature neValue = neType.getFeatureByBaseName("value");
        Feature neModifier = neType.getFeatureByBaseName("modifier");

        MyIobEncoder encoder = new MyIobEncoder(jcas.getCas(), neType, neValue, neModifier, true);

        int i=0;
        for (Token token : JCasUtil.select(jcas, Token.class)) {
            assertEquals(expected[i], encoder.encode(token));
            i++;
        }
    }

    private JCas getJCas()
            throws Exception
    {
        String text = "We need a very complicated example sentence , which " +
                "contains as many constituents and dependencies as possible .";

        JCas jcas = JCasFactory.createJCas();
        JCasBuilder cb = new JCasBuilder(jcas);
        for (String token : text.split(" ")) {
            cb.add(token, Token.class);
        }

        List<Token> tokens = new ArrayList<>(JCasUtil.select(jcas, Token.class));
        NamedEntity ne1 = new NamedEntity(jcas, tokens.get(3).getBegin(), tokens.get(6).getEnd());
        ne1.setValue("ORG");
        ne1.setModifier("gov");
        ne1.addToIndexes();

        NamedEntity ne2 = new NamedEntity(jcas, tokens.get(12).getBegin(), tokens.get(12).getEnd());
        ne2.setValue("PER");
        ne2.setModifier("ind");
        ne2.addToIndexes();

        NamedEntity ne3 = new NamedEntity(jcas, tokens.get(14).getBegin(), tokens.get(14).getEnd());
        ne3.setValue("LOC");
        ne3.setModifier("");
        ne3.addToIndexes();

        return cb.getJCas();
    }
}
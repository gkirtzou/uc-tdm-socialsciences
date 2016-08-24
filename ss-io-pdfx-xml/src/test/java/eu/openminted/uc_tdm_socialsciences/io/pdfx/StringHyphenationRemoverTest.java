package eu.openminted.uc_tdm_socialsciences.io.pdfx;

import org.apache.cxf.common.i18n.Exception;
import org.apache.log4j.Logger;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kiaeeha on 23.08.16.
 */
public class StringHyphenationRemoverTest {
    private static final Logger logger = Logger.getLogger(StringHyphenationRemoverTest.class);

    @Test
    public void testProcess() throws ResourceInitializationException, AnalysisEngineProcessException {
        logger.info("testProcess() started");
        StringHyphenationRemover hyphenationRemover = new StringHyphenationRemover();
        String inputText = " is field Aasgei- ern my   ";
        String expectedText = " is field Aasgeiern my   ";

        Assert.assertEquals(expectedText, hyphenationRemover.process(inputText));
        logger.info("testProcess() finished");
    }

    @Test
    public void testProcessLongDocument() throws ResourceInitializationException, AnalysisEngineProcessException {
        logger.info("testProcessLongDocument() started");
        StringHyphenationRemover hyphenationRemover = new StringHyphenationRemover();
        String inputText = "Im Rahmen des Forschungsprojekts Demoenergie ist die Frage nach den Prozessen des Erörterns und Entscheidens zur CCS-Technologie von besonderer Bedeutung. Das Forschungsinteresse richtet sich hierbei auf Erfahrungen und Potentiale einer in starkem Sinne demokratischen Regelung von gesellschaftlichen Prozessen. Grundlagen für ein allgemeines Verständnis dieses Forschungsinteresses können Beiträgen zu deliberativer Demokratie (Habermas 1998), Bürgerbeteiligung (Nanz/Fritsche 2012), partizipatorischer Governance (Walk 2008) und zu umweltbezogenen Konflikten (Saretzki/Feindt 2010) entnommen werden. Für diese Ansätze und Zugänge sind wiederum umfassendere Reflexion zu Institutionen und Wegen von gesellschaftlichen Regelungsprozessen bzw. Governance relevant (Mayntz 2009; Benz/Dose 2010; Lange/Schimank 2004). Für diesen Horizont des Interesses an Formen und Chancen von in starkem Sinne demokratischen Erörterungs- und Entscheidungsprozessen soll die vorliegende Fallanalyse des CCS- Konflikts einige relevante Aufschlüsse liefern, die mittels der gegenstandsnahen Exploration aus der gesellschaftlichen Praxis heraus gewonnen werden können. Wenn wir in diesem Zusammenhang die vorliegende sozialwissenschaftliche Forschung zu CCS in Deutschland vergegenwärtigen, so zeigt sich, dass der Begriff der Akzeptanz in dieser Forschung eine herausragende Rolle spielt. Schwerpunktmäßig untersucht diese Akzeptanz- 11 forschung anhand von empirischen Daten, welches (Nicht-)Wissen und welche Einstellun- gen zur CCS-Technologie in der Bevölkerung bestehen. Dabei wird zum Teil auch reflektiert, wie Akzeptanz zu erlangen sei. Insgesamt betrachtet, blickt diese Akzeptanzforschung vor allem auf individuelle Meinungen bzw. auf die Aggregation von Meinungen, die auf Seiten der Bevölkerung in Reaktion auf bereits feststehende Sachverhalte und an die Bevölkerung gerichtete Informationen entstehen. Die so untersuchten Meinungen und Informationen impli- zieren ein weitgehend einseitig ausgerichtetes Verständnis von Kommunikation, das durch feststehende Rollen von Sendern und Empfängern geprägt ist. Ein dialogischer Wechsel dieser Rollen sowie Ansprüche auf Teilhabe an einer umfassenderen gesellschaftlichen Reflexion über Fragen von CCS liegen weitgehend außerhalb dieser Forschungsperspektive (vgl. Fitzner/Rost 2014). In Bezug auf die Fragen zu einer dialogorientierten Bürgerbeteiligung und -partizipation, der zugetraut wird, auch gehaltvolle Wissensbestände und Beiträge zur breiten gesellschaftlichen Verständigung über die Entwicklung und Anwendung von Technologien zu liefern, bleibt die Perspektive der bisherigen CCS-Akzeptanzforschung eher eng gefasst und auf die Frage des Ausmaßes und der Bedingungen für eine stabile Hinnahme oder Ablehnung von CCS und entsprechender Vorhaben vor Ort ausgerichtet. Insbesondere ein Blick auf die Akzeptanzforschung allgemein zeigt allerdings, dass solche Engführungen der Untersuchungsperspektive und darauf reagierende Perspektiverweiterungen auch innerhalb dieses Forschungsstrangs diskutiert werden. Vorgeschlagen wird in diesem Zusammenhang der als Ergänzung und Perspektiverweiterung angesehene Begriff der Akzeptabilität. Auch im Kontext der CCS-Forschung werden so die \"Zumutbarkeiten, die mit der Umsetzung der Technologien einhergehen\" (Wuppertal Institut et al. 2008: 99) selbst thematisiert. Dies deckt sich mit bereits früher formulierten und aus dem Kontext der Atomdebatten";
        String expectedText = "Im Rahmen des Forschungsprojekts Demoenergie ist die Frage nach den Prozessen des Erörterns und Entscheidens zur CCS-Technologie von besonderer Bedeutung. Das Forschungsinteresse richtet sich hierbei auf Erfahrungen und Potentiale einer in starkem Sinne demokratischen Regelung von gesellschaftlichen Prozessen. Grundlagen für ein allgemeines Verständnis dieses Forschungsinteresses können Beiträgen zu deliberativer Demokratie (Habermas 1998), Bürgerbeteiligung (Nanz/Fritsche 2012), partizipatorischer Governance (Walk 2008) und zu umweltbezogenen Konflikten (Saretzki/Feindt 2010) entnommen werden. Für diese Ansätze und Zugänge sind wiederum umfassendere Reflexion zu Institutionen und Wegen von gesellschaftlichen Regelungsprozessen bzw. Governance relevant (Mayntz 2009; Benz/Dose 2010; Lange/Schimank 2004). Für diesen Horizont des Interesses an Formen und Chancen von in starkem Sinne demokratischen Erörterungs- und Entscheidungsprozessen soll die vorliegende Fallanalyse des CCS- Konflikts einige relevante Aufschlüsse liefern, die mittels der gegenstandsnahen Exploration aus der gesellschaftlichen Praxis heraus gewonnen werden können. Wenn wir in diesem Zusammenhang die vorliegende sozialwissenschaftliche Forschung zu CCS in Deutschland vergegenwärtigen, so zeigt sich, dass der Begriff der Akzeptanz in dieser Forschung eine herausragende Rolle spielt. Schwerpunktmäßig untersucht diese Akzeptanz- 11 forschung anhand von empirischen Daten, welches (Nicht-)Wissen und welche Einstellungen zur CCS-Technologie in der Bevölkerung bestehen. Dabei wird zum Teil auch reflektiert, wie Akzeptanz zu erlangen sei. Insgesamt betrachtet, blickt diese Akzeptanzforschung vor allem auf individuelle Meinungen bzw. auf die Aggregation von Meinungen, die auf Seiten der Bevölkerung in Reaktion auf bereits feststehende Sachverhalte und an die Bevölkerung gerichtete Informationen entstehen. Die so untersuchten Meinungen und Informationen implizieren ein weitgehend einseitig ausgerichtetes Verständnis von Kommunikation, das durch feststehende Rollen von Sendern und Empfängern geprägt ist. Ein dialogischer Wechsel dieser Rollen sowie Ansprüche auf Teilhabe an einer umfassenderen gesellschaftlichen Reflexion über Fragen von CCS liegen weitgehend außerhalb dieser Forschungsperspektive (vgl. Fitzner/Rost 2014). In Bezug auf die Fragen zu einer dialogorientierten Bürgerbeteiligung und -partizipation, der zugetraut wird, auch gehaltvolle Wissensbestände und Beiträge zur breiten gesellschaftlichen Verständigung über die Entwicklung und Anwendung von Technologien zu liefern, bleibt die Perspektive der bisherigen CCS-Akzeptanzforschung eher eng gefasst und auf die Frage des Ausmaßes und der Bedingungen für eine stabile Hinnahme oder Ablehnung von CCS und entsprechender Vorhaben vor Ort ausgerichtet. Insbesondere ein Blick auf die Akzeptanzforschung allgemein zeigt allerdings, dass solche Engführungen der Untersuchungsperspektive und darauf reagierende Perspektiverweiterungen auch innerhalb dieses Forschungsstrangs diskutiert werden. Vorgeschlagen wird in diesem Zusammenhang der als Ergänzung und Perspektiverweiterung angesehene Begriff der Akzeptabilität. Auch im Kontext der CCS-Forschung werden so die \"Zumutbarkeiten, die mit der Umsetzung der Technologien einhergehen\" (Wuppertal Institut et al. 2008: 99) selbst thematisiert. Dies deckt sich mit bereits früher formulierten und aus dem Kontext der Atomdebatten";

        Assert.assertEquals(expectedText, hyphenationRemover.process(inputText));
        logger.info("testProcessLongDocument() finished");
    }

    @Test
    public void testProcessVeryLongDocument() throws ResourceInitializationException, AnalysisEngineProcessException {
        logger.info("testProcessLongDocument() started");
        StringHyphenationRemover hyphenationRemover = new StringHyphenationRemover();
        String inputText = "Im Rahmen des Forschungsprojekts Demoenergie ist die Frage nach den Prozessen des Erörterns und Entscheidens zur CCS-Technologie von besonderer Bedeutung. Das Forschungsinteresse richtet sich hierbei auf Erfahrungen und Potentiale einer in starkem Sinne demokratischen Regelung von gesellschaftlichen Prozessen. Grundlagen für ein allgemeines Verständnis dieses Forschungsinteresses können Beiträgen zu deliberativer Demokratie (Habermas 1998), Bürgerbeteiligung (Nanz/Fritsche 2012), partizipatorischer Governance (Walk 2008) und zu umweltbezogenen Konflikten (Saretzki/Feindt 2010) entnommen werden. Für diese Ansätze und Zugänge sind wiederum umfassendere Reflexion zu Institutionen und Wegen von gesellschaftlichen Regelungsprozessen bzw. Governance relevant (Mayntz 2009; Benz/Dose 2010; Lange/Schimank 2004). Für diesen Horizont des Interesses an Formen und Chancen von in starkem Sinne demokratischen Erörterungs- und Entscheidungsprozessen soll die vorliegende Fallanalyse des CCS- Konflikts einige relevante Aufschlüsse liefern, die mittels der gegenstandsnahen Exploration aus der gesellschaftlichen Praxis heraus gewonnen werden können. Wenn wir in diesem Zusammenhang die vorliegende sozialwissenschaftliche Forschung zu CCS in Deutschland vergegenwärtigen, so zeigt sich, dass der Begriff der Akzeptanz in dieser Forschung eine herausragende Rolle spielt. Schwerpunktmäßig untersucht diese Akzeptanz- 11 forschung anhand von empirischen Daten, welches (Nicht-)Wissen und welche Einstellun- gen zur CCS-Technologie in der Bevölkerung bestehen. Dabei wird zum Teil auch reflektiert, wie Akzeptanz zu erlangen sei. Insgesamt betrachtet, blickt diese Akzeptanzforschung vor allem auf individuelle Meinungen bzw. auf die Aggregation von Meinungen, die auf Seiten der Bevölkerung in Reaktion auf bereits feststehende Sachverhalte und an die Bevölkerung gerichtete Informationen entstehen. Die so untersuchten Meinungen und Informationen impli- zieren ein weitgehend einseitig ausgerichtetes Verständnis von Kommunikation, das durch feststehende Rollen von Sendern und Empfängern geprägt ist. Ein dialogischer Wechsel dieser Rollen sowie Ansprüche auf Teilhabe an einer umfassenderen gesellschaftlichen Reflexion über Fragen von CCS liegen weitgehend außerhalb dieser Forschungsperspektive (vgl. Fitzner/Rost 2014). In Bezug auf die Fragen zu einer dialogorientierten Bürgerbeteiligung und -partizipation, der zugetraut wird, auch gehaltvolle Wissensbestände und Beiträge zur breiten gesellschaftlichen Verständigung über die Entwicklung und Anwendung von Technologien zu liefern, bleibt die Perspektive der bisherigen CCS-Akzeptanzforschung eher eng gefasst und auf die Frage des Ausmaßes und der Bedingungen für eine stabile Hinnahme oder Ablehnung von CCS und entsprechender Vorhaben vor Ort ausgerichtet. Insbesondere ein Blick auf die Akzeptanzforschung allgemein zeigt allerdings, dass solche Engführungen der Untersuchungsperspektive und darauf reagierende Perspektiverweiterungen auch innerhalb dieses Forschungsstrangs diskutiert werden. Vorgeschlagen wird in diesem Zusammenhang der als Ergänzung und Perspektiverweiterung angesehene Begriff der Akzeptabilität. Auch im Kontext der CCS-Forschung werden so die \"Zumutbarkeiten, die mit der Umsetzung der Technologien einhergehen\" (Wuppertal Institut et al. 2008: 99) selbst thematisiert. Dies deckt sich mit bereits früher formulierten und aus dem Kontext der Atomdebatten";
        String expectedText = "Im Rahmen des Forschungsprojekts Demoenergie ist die Frage nach den Prozessen des Erörterns und Entscheidens zur CCS-Technologie von besonderer Bedeutung. Das Forschungsinteresse richtet sich hierbei auf Erfahrungen und Potentiale einer in starkem Sinne demokratischen Regelung von gesellschaftlichen Prozessen. Grundlagen für ein allgemeines Verständnis dieses Forschungsinteresses können Beiträgen zu deliberativer Demokratie (Habermas 1998), Bürgerbeteiligung (Nanz/Fritsche 2012), partizipatorischer Governance (Walk 2008) und zu umweltbezogenen Konflikten (Saretzki/Feindt 2010) entnommen werden. Für diese Ansätze und Zugänge sind wiederum umfassendere Reflexion zu Institutionen und Wegen von gesellschaftlichen Regelungsprozessen bzw. Governance relevant (Mayntz 2009; Benz/Dose 2010; Lange/Schimank 2004). Für diesen Horizont des Interesses an Formen und Chancen von in starkem Sinne demokratischen Erörterungs- und Entscheidungsprozessen soll die vorliegende Fallanalyse des CCS- Konflikts einige relevante Aufschlüsse liefern, die mittels der gegenstandsnahen Exploration aus der gesellschaftlichen Praxis heraus gewonnen werden können. Wenn wir in diesem Zusammenhang die vorliegende sozialwissenschaftliche Forschung zu CCS in Deutschland vergegenwärtigen, so zeigt sich, dass der Begriff der Akzeptanz in dieser Forschung eine herausragende Rolle spielt. Schwerpunktmäßig untersucht diese Akzeptanz- 11 forschung anhand von empirischen Daten, welches (Nicht-)Wissen und welche Einstellungen zur CCS-Technologie in der Bevölkerung bestehen. Dabei wird zum Teil auch reflektiert, wie Akzeptanz zu erlangen sei. Insgesamt betrachtet, blickt diese Akzeptanzforschung vor allem auf individuelle Meinungen bzw. auf die Aggregation von Meinungen, die auf Seiten der Bevölkerung in Reaktion auf bereits feststehende Sachverhalte und an die Bevölkerung gerichtete Informationen entstehen. Die so untersuchten Meinungen und Informationen implizieren ein weitgehend einseitig ausgerichtetes Verständnis von Kommunikation, das durch feststehende Rollen von Sendern und Empfängern geprägt ist. Ein dialogischer Wechsel dieser Rollen sowie Ansprüche auf Teilhabe an einer umfassenderen gesellschaftlichen Reflexion über Fragen von CCS liegen weitgehend außerhalb dieser Forschungsperspektive (vgl. Fitzner/Rost 2014). In Bezug auf die Fragen zu einer dialogorientierten Bürgerbeteiligung und -partizipation, der zugetraut wird, auch gehaltvolle Wissensbestände und Beiträge zur breiten gesellschaftlichen Verständigung über die Entwicklung und Anwendung von Technologien zu liefern, bleibt die Perspektive der bisherigen CCS-Akzeptanzforschung eher eng gefasst und auf die Frage des Ausmaßes und der Bedingungen für eine stabile Hinnahme oder Ablehnung von CCS und entsprechender Vorhaben vor Ort ausgerichtet. Insbesondere ein Blick auf die Akzeptanzforschung allgemein zeigt allerdings, dass solche Engführungen der Untersuchungsperspektive und darauf reagierende Perspektiverweiterungen auch innerhalb dieses Forschungsstrangs diskutiert werden. Vorgeschlagen wird in diesem Zusammenhang der als Ergänzung und Perspektiverweiterung angesehene Begriff der Akzeptabilität. Auch im Kontext der CCS-Forschung werden so die \"Zumutbarkeiten, die mit der Umsetzung der Technologien einhergehen\" (Wuppertal Institut et al. 2008: 99) selbst thematisiert. Dies deckt sich mit bereits früher formulierten und aus dem Kontext der Atomdebatten";

        for(int i=0; i<10 ; ++i)
            Assert.assertEquals(expectedText, hyphenationRemover.process(inputText));
        logger.info("testProcessLongDocument() finished");
    }
}
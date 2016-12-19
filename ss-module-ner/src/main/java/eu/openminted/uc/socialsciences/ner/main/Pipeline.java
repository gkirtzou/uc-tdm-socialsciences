package eu.openminted.uc.socialsciences.ner.main;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.metadata.SofaMapping;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.FlowControllerFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.flow.FlowController;
import org.apache.uima.flow.FlowControllerDescription;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameter;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;

import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import eu.openminted.uc.socialsciences.ner.helper.BinaryCasToStanfordTsvConverter;

public class Pipeline {

	private static final Logger logger = Logger.getLogger(Pipeline.class);

	public static void main(String[] args) {
		String inputPattern = "./**/*.xmi";
		String modelLocation = "omtd-ner-model.ser.gz"; // TODO make maven
														 // resolve path
		String predefinedModel = "all.3class.distsim.crf"; // TODO make maven
															 // resolve path
		String typesystemFile = "src/main/resources/typesystem.xml"; // TODO
																	 // make
																	 // maven
																	 // resolve
																	 // path

		final String LANGUAGECODE_DE = "de";
		final String LANGUAGECODE_EN = "en";

		boolean usePredefinedModel = true;

		/*
		 * language should be read from document metadata
		 */
		String language = "";

		try {
			TypeSystemDescription allTypes = mergeBuiltInAndCustomTypes(typesystemFile);
			for (TypeDescription type : allTypes.getTypes()) {
				logger.info("Type recognized: " + type.getName());
			}

			CollectionReaderDescription reader;
			reader = createReaderDescription(XmiReader.class, allTypes,
					XmiReader.PARAM_SOURCE_LOCATION, inputPattern,
					XmiReader.PARAM_LANGUAGE, language);

			AnalysisEngineDescription ner = usePredefinedModel
					? createEngineDescription(StanfordNamedEntityRecognizer.class,
							StanfordNamedEntityRecognizer.PARAM_LANGUAGE, language,
							StanfordNamedEntityRecognizer.PARAM_VARIANT,
							"classpath:/de/tudarmstadt/ukp/dkpro/core/stanfordnlp/lib/ner-variants.map",
							StanfordNamedEntityRecognizer.PARAM_NAMED_ENTITY_MAPPING_LOCATION,
							"classpath:/de/tudarmstadt/ukp/dkpro/core/stanfordnlp/lib/ner-${language}-${variant}.map")
					: createEngineDescription(StanfordNamedEntityRecognizer.class,
							StanfordNamedEntityRecognizer.PARAM_LANGUAGE, language,
							StanfordNamedEntityRecognizer.PARAM_MODEL_LOCATION, modelLocation);

			AnalysisEngineDescription xmiWriter = createEngineDescription(
					XmiWriter.class,
					XmiWriter.PARAM_TARGET_LOCATION, ".",
					XmiWriter.PARAM_TYPE_SYSTEM_FILE, "typesystem.xml");

			/*
			 * test pipeline - XMI input, NER, XMI output (can be viewed with
			 * UIMA
			 * CAS editor)
			 */
			runPipeline(reader, ner, xmiWriter);

			/*
			 * have to create aggregate analysis engine from several aes with
			 * flow
			 * controller?
			 */
			ConfigurationParameter[] configurationParameters = null;
			Object[] configurationValues = null;
			Map<String, ExternalResourceDescription> externalResources = null;
			FlowControllerDescription flowControllerDescription = FlowControllerFactory.createFlowControllerDescription(
					FlowController.class, configurationParameters, configurationValues, externalResources);

			List<AnalysisEngineDescription> list = null;
			List<String> names = null;
			SofaMapping[] sofaMappings = null;
			TypePriorities priorities = null;

			AnalysisEngineDescription aggregateEngine = createEngineDescription(list, names, priorities, sofaMappings,
					flowControllerDescription);

			AnalysisEngine engine = AnalysisEngineFactory.createEngine(aggregateEngine);

		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static TypeSystemDescription mergeBuiltInAndCustomTypes(String typesystemFile)
			throws ResourceInitializationException {
		TypeSystemDescription builtInTypes = TypeSystemDescriptionFactory
				.createTypeSystemDescription();
		TypeSystemDescription customTypes = TypeSystemDescriptionFactory
				.createTypeSystemDescriptionFromPath(typesystemFile);
		return CasCreationUtils.mergeTypeSystems(Arrays.asList(builtInTypes, customTypes));
	}

}
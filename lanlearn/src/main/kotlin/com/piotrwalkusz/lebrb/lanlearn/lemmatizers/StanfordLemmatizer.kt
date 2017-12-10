package com.piotrwalkusz.lebrb.lanlearn.lemmatizers

import com.piotrwalkusz.lebrb.lanlearn.Language
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.util.PropertiesUtils
import java.io.Reader

class StanfordLemmatizer(language: Language) : Lemmatizer {

    companion object {
        private val ENGLISH_PIPELINE by lazy { StanfordCoreNLP (PropertiesUtils.asProperties(
            "annotators", "tokenize, ssplit, pos, lemma")) }
    }

    private var pipeline: StanfordCoreNLP

    init {
        pipeline = when (language) {
            Language.ENGLISH -> ENGLISH_PIPELINE
            else -> throw IllegalArgumentException("Stanford lemmatizer does not support ${language.name} language")
        }
    }

    override fun lemmatize(source: Reader, dictionary: Set<String>): Map<String, Int> {
        val document = Annotation(source.toString())

        pipeline.annotate(document)

        return document.get(CoreAnnotations.SentencesAnnotation::class.java)
                .flatMap { it.get(CoreAnnotations.TokensAnnotation::class.java) }
                .filter { filterPartOfSpeech(it.get(CoreAnnotations.PartOfSpeechAnnotation::class.java)) }
                .map { it.get(CoreAnnotations.LemmaAnnotation::class.java) }
                .filter { dictionary.contains(it.toLowerCase()) }
                .groupingBy { it }
                .eachCount()
    }

    private val supportedPartOfSpeech = setOf("JJ", "JJR", "JJS", "NN", "NNS", "RB", "RBR", "RBS", "VB", "VBD", "VBG",
            "VBN", "VBP", "VBZ", "PRP", "NNP", "NNPS")

    private fun filterPartOfSpeech(pos: String): Boolean = supportedPartOfSpeech.contains(pos)
}
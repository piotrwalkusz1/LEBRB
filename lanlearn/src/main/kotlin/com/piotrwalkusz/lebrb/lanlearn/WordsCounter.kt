package com.piotrwalkusz.lebrb.lanlearn

import com.piotrwalkusz.lebrb.lanlearn.filereaders.FileReader
import com.piotrwalkusz.lebrb.lanlearn.filereaders.PdfFileReader
import com.piotrwalkusz.lebrb.lanlearn.filereaders.PlainTextFileReader
import com.piotrwalkusz.lebrb.lanlearn.lemmatizers.LanguageToolLemmatizer
import com.piotrwalkusz.lebrb.lanlearn.lemmatizers.Lemmatizer
import com.piotrwalkusz.lebrb.lanlearn.lemmatizers.StanfordLemmatizer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.io.InputStream
import java.io.Reader

class WordsCounter {

    private val fileReaders: Map<MediaType, FileReader> = mapOf(
            MediaType.PLAIN_TEXT to PlainTextFileReader(),
            MediaType.PDF to PdfFileReader())

    private val lemmatizers: Map<Language, Lemmatizer> = mapOf(
            Language.ENGLISH to StanfordLemmatizer(Language.ENGLISH),
            Language.GERMAN to LanguageToolLemmatizer(Language.GERMAN)
    )

    fun countWords(source: InputStream, mimeType: MediaType, language: Language, dictionary: Set<String>)
            : WordsCounterResult {

        val lemmatizer = lemmatizers[language] ?: throw IllegalArgumentException("Language $language is not supported")
        val fileReader = fileReaders[mimeType] ?: throw IllegalArgumentException("Mime type $mimeType is not supported")
        val partsOfFile: Flux<Reader> = fileReader.read(source)

        val wordsFrequency: Map<String, Int> = partsOfFile
                .flatMap { Mono.defer { Mono.just(lemmatizer.lemmatize(it, dictionary) ) }
                        .subscribeOn(Schedulers.parallel()) }
                .reduce { x, y -> mergeWordsFrequency(x, y) }
                .block()

        return WordsCounterResult(wordsFrequency)
    }

    private fun mergeWordsFrequency(x: Map<String, Int>, y: Map<String, Int>): Map<String, Int> {
        val result = x.toMutableMap()
        y.forEach { word, frequency -> result.merge(word, frequency, Int::plus) }
        return result
    }
}
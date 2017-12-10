package com.piotrwalkusz.lebrb.lanlearn.filereaders

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import reactor.core.publisher.Flux
import java.io.InputStream
import java.io.Reader

class PdfFileDecoder(private val pagesPerChunk: Int = 100) : FileDecoder {

    override fun read(source: InputStream): Flux<Reader> {
        val doc = PDDocument.load(source)
        val pdfTextStripper = PDFTextStripper()

        return Flux.generate({ 1 }, { page, sink ->
            pdfTextStripper.startPage = page
            pdfTextStripper.endPage = page + pagesPerChunk - 1
            sink.next(pdfTextStripper.getText(doc).reader())
            if (page + pagesPerChunk > doc.numberOfPages) {
                doc.close()
                sink.complete()
            }
            page + pagesPerChunk
        })
    }
}
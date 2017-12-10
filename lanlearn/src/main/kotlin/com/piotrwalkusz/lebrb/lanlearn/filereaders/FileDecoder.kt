package com.piotrwalkusz.lebrb.lanlearn.filereaders

import reactor.core.publisher.Flux
import java.io.InputStream
import java.io.Reader

interface FileDecoder {

    fun read(source: InputStream): Flux<Reader>
}
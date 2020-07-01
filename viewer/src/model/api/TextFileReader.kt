/*
 * Copyright 2020 d.sevostyanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model.api

import model.beans.TextFileReaderImpl
import model.objects.TextBuffer
import java.nio.channels.SeekableByteChannel

/**
 * Service, loads data from text file
 */
interface TextFileReader {

    /**
     * Factory method
     */
    fun get(): TextFileReader = TextFileReaderImpl

    /**
     * Read content into text buffer from file by path by offset and bytes size
     * @return text buffer
     */
    fun readTextBuffer(fileChannel: SeekableByteChannel, offset: Long, size: Long): TextBuffer

    /**
     * Move current text buffer next by part offset from file by path
     */
    fun readTextBufferPart(fileChannel: SeekableByteChannel, textBuffer: TextBuffer, offset: Long): TextBuffer
}
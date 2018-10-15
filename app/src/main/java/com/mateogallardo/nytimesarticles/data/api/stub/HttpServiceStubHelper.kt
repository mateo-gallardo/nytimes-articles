package com.mateogallardo.nytimesarticles.data.api.stub

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HttpServiceStubHelper {
    companion object {
        @Throws(Exception::class)
        fun getStringFromFile(context: Context, filePath: String): String {
            val stream = context.resources.assets.open(filePath)

            val ret = convertStreamToString(stream)
            //Make sure you close all streams.
            stream.close()
            return ret
        }

        @Throws(Exception::class)
        fun convertStreamToString(`is`: InputStream): String {
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String? = reader.readLine()
            while (line != null) {
                sb.append(line).append("\n")
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }
    }
}
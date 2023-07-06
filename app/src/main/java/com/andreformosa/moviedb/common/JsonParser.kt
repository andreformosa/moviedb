package com.andreformosa.moviedb.common

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import javax.inject.Inject

class JsonParser @Inject constructor() {

    val json = Json { ignoreUnknownKeys = true }

    inline fun <reified T : Any> toJson(item: T): String = json.encodeToString(item)

    inline fun <reified T : Any> fromJson(jsonString: String): T = json.decodeFromString(jsonString)

    fun <T : Any> fromJson(jsonString: String, clazz: Class<T>): T {
        return json.decodeFromString(serializer(clazz), jsonString) as T
    }
}

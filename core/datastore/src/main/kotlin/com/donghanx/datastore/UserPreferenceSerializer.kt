package com.donghanx.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.donghanx.model.UserPreference
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

object UserPreferenceSerializer : Serializer<UserPreference> {

    override val defaultValue: UserPreference = UserPreference()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): UserPreference =
        try {
            Json.decodeFromStream<UserPreference>(input)
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(t: UserPreference, output: OutputStream) {
        withContext(Dispatchers.IO) { output.write(Json.encodeToString(t).encodeToByteArray()) }
    }
}

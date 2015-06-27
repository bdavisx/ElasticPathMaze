package com.tartner.elasticpath.maze

import com.google.gson.*
import org.slf4j.LoggerFactory
import retrofit.RestAdapter
import retrofit.converter.GsonConverter
import java.lang.reflect.Type

public class Configuration {
    private val log = LoggerFactory.getLogger(javaClass<Configuration>())

    public fun createService(url: String): BlackoutServer {
        val gson : Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(javaClass<MoveDirection>(), MoveDirectionAdapter())
            .create();

        val restAdapter : RestAdapter = RestAdapter.Builder()
            .setEndpoint(url)
            .setConverter(GsonConverter(gson))
//            .setLogLevel( RestAdapter.LogLevel.FULL)
//            .setLog { message -> log.debug(message) }
            .build();

        val service : BlackoutServer = restAdapter.create(javaClass<BlackoutServer>())
        return service
    }
}

class MoveDirectionAdapter : JsonSerializer<MoveDirection> {
    override fun serialize(direction: MoveDirection?, typeOfSource: Type?,
        context: JsonSerializationContext?): JsonElement? {
        return JsonPrimitive(direction?.serverRequestText)
    }
}

package com.tartner.elasticpath.maze

import com.google.gson.*
import retrofit.RestAdapter
import retrofit.converter.GsonConverter
import java.lang.reflect.Type

public class Configuration {
    public fun createService(url: String): BlackoutServer {
        val gson : Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(javaClass<MoveDirection>(), MoveDirectionAdapter())
            .create();

        val restAdapter : RestAdapter = RestAdapter.Builder()
            .setEndpoint(url)
            .setConverter(GsonConverter(gson))
            .build();

        val service : BlackoutServer = restAdapter.create(javaClass<BlackoutServer>())
        return service
    }
}

class MoveDirectionAdapter : JsonSerializer<MoveDirection> {
    override fun serialize(direction: MoveDirection?, typeOfSource: Type?,
        context: JsonSerializationContext?): JsonElement? {
        return JsonPrimitive(direction?.apiText)
    }
}

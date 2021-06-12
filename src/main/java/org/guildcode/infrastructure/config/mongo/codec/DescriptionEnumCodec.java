package org.guildcode.infrastructure.config.mongo.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.guildcode.domain.shared.util.DescriptiveEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DescriptionEnumCodec implements Codec<DescriptiveEnum> {

    private static final Map<Class<?>, Map<String, DescriptiveEnum>> ENUM_CACHE_DESCRIPTION = new ConcurrentHashMap<>();

    private final Class<DescriptiveEnum> clazz;

    DescriptionEnumCodec(final Class<DescriptiveEnum> clazz) {
        this.clazz = clazz;
    }

    static DescriptiveEnum findByDescription(Class<DescriptiveEnum> enumerator, String description) {
        Map<String, DescriptiveEnum> map = ENUM_CACHE_DESCRIPTION.computeIfAbsent(enumerator, v -> new ConcurrentHashMap<>());
        return map.computeIfAbsent(description, v -> Arrays.stream(enumerator.getEnumConstants())
                .filter(DescriptiveEnum.class::isInstance)
                .map(DescriptiveEnum.class::cast)
                .filter(descriptiveEnum -> descriptiveEnum.getDescription().equals(description))
                .findAny()
                .orElseThrow());
    }

    @Override
    public void encode(final BsonWriter writer, final DescriptiveEnum value, final EncoderContext encoderContext) {
        writer.writeString(value.getDescription());
    }

    @Override
    public Class<DescriptiveEnum> getEncoderClass() {
        return clazz;
    }

    @Override
    public DescriptiveEnum decode(final BsonReader reader, final DecoderContext decoderContext) {
        return findByDescription(clazz, reader.readString());
    }
}

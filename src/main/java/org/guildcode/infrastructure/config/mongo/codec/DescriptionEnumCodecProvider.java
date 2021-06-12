package org.guildcode.infrastructure.config.mongo.codec;

import org.bson.codecs.Codec;
import org.bson.codecs.pojo.PropertyCodecProvider;
import org.bson.codecs.pojo.PropertyCodecRegistry;
import org.bson.codecs.pojo.TypeWithTypeParameters;
import org.guildcode.domain.shared.util.DescriptiveEnum;

import javax.inject.Singleton;

@Singleton
public class DescriptionEnumCodecProvider implements PropertyCodecProvider {

    @Override
    public <T> Codec<T> get(TypeWithTypeParameters<T> type, PropertyCodecRegistry registry) {
        if (DescriptiveEnum.class.isAssignableFrom(type.getType())) {
            return (Codec<T>) new DescriptionEnumCodec((Class<DescriptiveEnum>) type.getType());
        }
        return null;
    }
}

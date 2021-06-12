package org.guildcode.infrastructure.data.mongo.mapper;

import org.guildcode.domain.user.User;
import org.guildcode.infrastructure.data.mongo.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserEntityMapper {

    UserEntity map(User user);

    User map(UserEntity userEntity);

    List<User> map(List<UserEntity> userEntities);
}

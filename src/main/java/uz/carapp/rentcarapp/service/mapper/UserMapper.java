package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.Mapper;
import uz.carapp.rentcarapp.domain.User;
import uz.carapp.rentcarapp.service.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
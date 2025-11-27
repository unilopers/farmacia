package com.github.zambrinn.mvcproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.zambrinn.mvcproject.DTOs.UserTableRequest;
import com.github.zambrinn.mvcproject.DTOs.UserTableResponse;
import com.github.zambrinn.mvcproject.model.Address;
import com.github.zambrinn.mvcproject.model.UserTable;
import com.github.zambrinn.mvcproject.repository.AddressRepository;
import com.github.zambrinn.mvcproject.repository.UserTableRepository;

@Service
public class UserTableService {
    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    private AddressRepository addressRepository;

    public UserTableResponse createUser(UserTableRequest request) {
        // Método de criação de usuário, o retorno do método é a resposta que vamos enviar
        // O parâmetro do método é o request, o que iremos precisar pra criar o usuário
        UserTable user = UserTable.builder() // Utilizo o builder para escolher o que vai ter nos atributos
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .passwordHash(request.passwordHash())
                .role(request.role())
                .createdAt(LocalDateTime.now())
                .build();

        UserTable savedUser = userTableRepository.save(user); // Salvo em uma variável o usuário salvo depois de
        // puxar do repositório o método de salvar

        // Se o request tem endereço, cria o endereço e vincula ao usuário
        if (request.address() != null) {
            Address address = Address.builder()
                    .street(request.address().street())
                    .number(request.address().number())
                    .city(request.address().city())
                    .state(request.address().state())
                    .user(savedUser)
                    .build();
            
            addressRepository.save(address);
        }

        return convertToDto(savedUser); // retorno o usuário convertido em DTO
    }

    public List<UserTableResponse> getAllUsers() { // Método para puxar todos os usuários
        return userTableRepository.findAll() // vou no userRepository e puxo o método findAll()
                .stream() // stream para podermos mexer na lista com mais facilidade
                .map(this::convertToDto) // filtro com um map e converto tudo na lista pra DTO
                .collect(Collectors.toList()); // retorno com um collectors para transformar tudo novamente em lista
    }

    public UserTableResponse updateUser(UUID id, UserTableRequest request) {
        UserTable foundUser = userTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find no user with id" + id));

        foundUser.setEmail(request.email());
        foundUser.setCpf(request.cpf());
        foundUser.setName(request.name());
        foundUser.setPasswordHash(request.passwordHash());
        foundUser.setActive(foundUser.isActive());

        UserTable updatedUser = userTableRepository.save(foundUser);
        return convertToDto(updatedUser);
    }

    public void deleteUser(UUID id) {
        UserTable foundUser = userTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find no user with id" + id));

        userTableRepository.delete(foundUser);
    }

    public UserTableResponse convertToDto(UserTable userTable) {
        // Converte os endereços do usuário para DTOs
        List<UserTableResponse.AddressDTO> addressDTOs = userTable.getAddresses().stream()
                .map(address -> new UserTableResponse.AddressDTO(
                        address.getId(),
                        address.getStreet(),
                        address.getNumber(),
                        address.getCity(),
                        address.getState()
                ))
                .collect(Collectors.toList());

        return new UserTableResponse(
                userTable.getId(),
                userTable.getName(),
                userTable.getEmail(),
                userTable.getCreatedAt(),
                userTable.getRole(),
                addressDTOs
        );
    }
}

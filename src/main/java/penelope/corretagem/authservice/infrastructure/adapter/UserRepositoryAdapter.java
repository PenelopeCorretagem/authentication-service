package penelope.corretagem.authservice.infrastructure.adapter;

import org.springframework.stereotype.Component;
import penelope.corretagem.authservice.core.user.User;
import penelope.corretagem.authservice.core.user.repository.IUserRepository;
import penelope.corretagem.authservice.infrastructure.mapper.UserInfrastructureMapper;
import penelope.corretagem.authservice.infrastructure.repository.IUserJpaRepository;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements IUserRepository {

    private final IUserJpaRepository jpaRepository;
    private final UserInfrastructureMapper mapper;

    public UserRepositoryAdapter(IUserJpaRepository jpaRepository, UserInfrastructureMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token) {
        return jpaRepository.findByPasswordResetToken(token).map(mapper::toDomain);
    }

    @Override
    public User update(User user) {
        User saved = mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
        return saved;
    }
}

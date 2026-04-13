package penelope.corretagem.authservice.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import penelope.corretagem.authservice.infrastructure.entity.UserJpaEntity;

import java.util.Optional;

public interface IUserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByEmail(String email);

    Optional<UserJpaEntity> findByPasswordResetToken(String token);
}

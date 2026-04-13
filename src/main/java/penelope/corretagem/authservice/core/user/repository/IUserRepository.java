package penelope.corretagem.authservice.core.user.repository;

import penelope.corretagem.authservice.core.user.User;

import java.util.Optional;

public interface IUserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);

    User update(User user);
}

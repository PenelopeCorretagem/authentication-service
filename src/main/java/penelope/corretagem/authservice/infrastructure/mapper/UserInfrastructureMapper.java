package penelope.corretagem.authservice.infrastructure.mapper;

import org.springframework.stereotype.Component;
import penelope.corretagem.authservice.core.user.User;
import penelope.corretagem.authservice.infrastructure.entity.UserJpaEntity;

@Component
public class UserInfrastructureMapper {

    public User toDomain(UserJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return User.restore(
            jpa.getId(),
            jpa.getName(),
            jpa.getEmail(),
            jpa.getPassword(),
            jpa.getCpf(),
            jpa.getDateBirth(),
            jpa.getMonthlyIncome(),
            jpa.getPhone(),
            jpa.getCreci(),
            jpa.getAccessLevel(),
            jpa.getDateCreation(),
            jpa.isActive(),
            jpa.getPasswordResetToken(),
            jpa.getPasswordResetTokenExpiry()
        );
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        entity.setCpf(domain.getCpf());
        entity.setDateBirth(domain.getDateBirth());
        entity.setMonthlyIncome(domain.getMonthlyIncome());
        entity.setPhone(domain.getPhone());
        entity.setCreci(domain.getCreci());
        entity.setAccessLevel(domain.getAccessLevel());
        entity.setDateCreation(domain.getDateCreation());
        entity.setActive(domain.isActive());
        entity.setPasswordResetToken(domain.getPasswordResetToken());
        entity.setPasswordResetTokenExpiry(domain.getPasswordResetTokenExpiry());
        return entity;
    }
}

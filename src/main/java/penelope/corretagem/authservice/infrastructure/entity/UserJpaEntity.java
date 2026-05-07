package penelope.corretagem.authservice.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import penelope.corretagem.authservice.core.user.valueobject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String password;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dateBirth;

    @Column(name = "renda_mensal")
    private BigDecimal monthlyIncome;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "creci")
    private String creci;

    @Column(name = "nivel_acesso")
    @Convert(converter = penelope.corretagem.authservice.infrastructure.entity.converter.AccessLevelAttributeConverter.class)
    private AccessLevel accessLevel;

    @Column(name = "data_criacao")
    private LocalDate dateCreation;

    @Column(name = "ativo")
    private boolean active = true;

    @Column(name = "token_redefinicao_senha")
    private String passwordResetToken;

    @Column(name = "data_expiracao_token")
    private Date passwordResetTokenExpiry;
}

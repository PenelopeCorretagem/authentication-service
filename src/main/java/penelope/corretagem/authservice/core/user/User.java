package penelope.corretagem.authservice.core.user;

import penelope.corretagem.authservice.core.exception.PasswordResetTokenExpiredException;
import penelope.corretagem.authservice.core.user.valueobject.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class User {

    private final Long id;
    private final LocalDate dateCreation;
    private final AccessLevel accessLevel;
    private final String creci;

    private String name;
    private String email;
    private String password;
    private String cpf;
    private LocalDate dateBirth;
    private BigDecimal monthlyIncome;
    private String phone;
    private boolean active;

    private String passwordResetToken;
    private Date passwordResetTokenExpiry;

    private User(Long id,
                 String name,
                 String email,
                 String password,
                 String cpf,
                 LocalDate dateBirth,
                 BigDecimal monthlyIncome,
                 String phone,
                 String creci,
                 AccessLevel accessLevel,
                 LocalDate dateCreation,
                 boolean active,
                 String passwordResetToken,
                 Date passwordResetTokenExpiry) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.dateBirth = dateBirth;
        this.monthlyIncome = monthlyIncome;
        this.phone = phone;
        this.creci = creci;
        this.accessLevel = accessLevel;
        this.dateCreation = dateCreation;
        this.active = active;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }

    public static User restore(Long id,
                               String name,
                               String email,
                               String password,
                               String cpf,
                               LocalDate dateBirth,
                               BigDecimal monthlyIncome,
                               String phone,
                               String creci,
                               AccessLevel accessLevel,
                               LocalDate dateCreation,
                               boolean active,
                               String passwordResetToken,
                               Date passwordResetTokenExpiry) {
        return new User(
            id,
            name,
            email,
            password,
            cpf,
            dateBirth,
            monthlyIncome,
            phone,
            creci,
            accessLevel,
            dateCreation,
            active,
            passwordResetToken,
            passwordResetTokenExpiry
        );
    }

    public void generatePasswordResetToken(String token, Date expiryDate) {
        this.passwordResetToken = token;
        this.passwordResetTokenExpiry = expiryDate;
    }

    public void applyNewPassword(String newEncryptedPassword) {
        if (this.passwordResetTokenExpiry == null || this.passwordResetTokenExpiry.before(new Date())) {
            throw new PasswordResetTokenExpiredException();
        }

        this.password = newEncryptedPassword;
        this.passwordResetToken = null;
        this.passwordResetTokenExpiry = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreci() {
        return creci;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public boolean isActive() {
        return active;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public Date getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }
}

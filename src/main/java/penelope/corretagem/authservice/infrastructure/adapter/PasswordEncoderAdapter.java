package penelope.corretagem.authservice.infrastructure.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import penelope.corretagem.authservice.core.gateway.IPasswordEncoderGateway;

@Component
public class PasswordEncoderAdapter implements IPasswordEncoderGateway {

    private final PasswordEncoder springPasswordEncoder;

    public PasswordEncoderAdapter(PasswordEncoder springPasswordEncoder) {
        this.springPasswordEncoder = springPasswordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return springPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return springPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}

package penelope.corretagem.authservice.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import penelope.corretagem.authservice.core.gateway.IEmailGateway;

@Component
public class EmailGatewayAdapter implements IEmailGateway {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public EmailGatewayAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Seu Token de Redefinicao de Senha");

        String emailBody = String.format(
            "Ola,\n\n" +
                "Você solicitou a redefinição de sua senha.\n\n" +
                "Codigo de verificacao: %s\n\n" +
                "O código irá expira em 1 hora.\n\n" +
                "Se você não solicitou isso, desconsidere este e-mail.\n\n" +
                "Atenciosamente,\nEquipe Penélope",
            token
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}

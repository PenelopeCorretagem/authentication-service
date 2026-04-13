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
        String resetUrl = frontendUrl + "/verificacao?token=" + token;
        String manualUrl = frontendUrl + "/verificacao";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Seu Token de Redefinicao de Senha");

        String emailBody = String.format(
            "Ola,\n\n" +
                "Voce solicitou a redefinicao de sua senha.\n\n" +
                "Clique no link abaixo para ir diretamente para a pagina de verificacao:\n" +
                "%s\n\n" +
                "Se o link acima nao funcionar, acesse manualmente o endereco abaixo e insira o token:\n" +
                "%s\n\n" +
                "Codigo de verificacao: %s\n\n" +
                "Este link e codigo expiram em 1 hora.\n\n" +
                "Se voce nao solicitou isso, ignore este e-mail.\n\n" +
                "Atenciosamente,\nEquipe Penelope",
            resetUrl,
            manualUrl,
            token
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}

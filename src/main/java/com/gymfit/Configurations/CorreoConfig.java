package com.gymfit.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class CorreoConfig {

    @Bean
    JavaMailSender javaMailSender(
            @Value("${spring.mail.host:smtp.gmail.com}") String host,
            @Value("${spring.mail.port:587}") int port,
            @Value("${spring.mail.username:}") String username,
            @Value("${spring.mail.password:}") String password) {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host.trim());
        sender.setPort(port);
        sender.setUsername(limpiar(username));
        // Google muestra la clave de aplicación agrupada. Se eliminan espacios
        // y comillas accidentales sin registrar nunca el valor en consola.
        sender.setPassword(limpiar(password).replaceAll("\\s+", ""));

        Properties propiedades = sender.getJavaMailProperties();
        propiedades.put("mail.transport.protocol", "smtp");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.starttls.required", "true");
        propiedades.put("mail.smtp.connectiontimeout", "10000");
        propiedades.put("mail.smtp.timeout", "10000");
        propiedades.put("mail.smtp.writetimeout", "10000");
        propiedades.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return sender;
    }

    private String limpiar(String valor) {
        if (valor == null) return "";
        String limpio = valor.trim();
        if (limpio.length() >= 2 && ((limpio.startsWith("\"") && limpio.endsWith("\""))
                || (limpio.startsWith("'") && limpio.endsWith("'")))) {
            limpio = limpio.substring(1, limpio.length() - 1).trim();
        }
        return limpio;
    }

}

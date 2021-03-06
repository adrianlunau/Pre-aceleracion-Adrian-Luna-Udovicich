package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.exception.ErrorEnum;
import com.alkemy.disney.disney.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env;

    @Value("${disney.email.subject}")
    private String subject;

    @Value("${disney.email.sender}")
    private String emailSender;
    @Value("${disney.email.enabled}")
    private boolean enabled;


    public void sendWelcomeEmailTo(String to) {

        if (!enabled) {
            return;
        }

        String apiKey = env.getProperty("EMAIL_API_KEY");

        Mail mail = this.generateMail(to);


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

        } catch (IOException ex) {
            System.out.println(ErrorEnum.TRYINGTOSENDMAILFAIL.getMensaje());
        }

    }

    public Mail generateMail(String to){
        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content(
                "text/plain",
                "Bienvenido/a a Alkemy Disney"
        );

        String subject = this.subject;

        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        return mail;
    }


}

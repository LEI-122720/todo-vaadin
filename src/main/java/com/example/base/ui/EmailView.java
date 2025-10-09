package com.example.base.ui;

import com.example.EmailService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "email", layout = MainLayout.class)
public class EmailView extends VerticalLayout {

    @Autowired
    private EmailService emailService;

    public EmailView() {
        setPadding(true);
        setWidthFull();

        EmailField to = new EmailField("Para");
        to.setPlaceholder("alguem@exemplo.com");
        to.setClearButtonVisible(true);
        to.setWidthFull();

        TextField subject = new TextField("Assunto");
        subject.setClearButtonVisible(true);
        subject.setWidthFull();

        TextArea body = new TextArea("Mensagem");
        body.setWidthFull();
        body.setHeight("200px");

        Button send = new Button("Enviar", e -> {
            try {
                emailService.send(to.getValue(), subject.getValue(), body.getValue());
                Notification.show("Email enviado ✅");
            } catch (Exception ex) {
                Notification.show("Falhou: " + ex.getMessage());
            }
        });

        add(to, subject, body, send);
    }
}

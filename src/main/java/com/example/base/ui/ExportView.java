package com.example.base.ui;

import com.example.PDFExporter;
import com.example.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@PageTitle("Exportar PDF")
@Route("export")
public class ExportView extends VerticalLayout {

    public ExportView() {
        // Cria o botão que exporta o PDF
        Button exportButton = new Button("Exportar tarefas em PDF", e -> {
            PDFExporter.exportTasksToPDF(
                    Arrays.asList("Estudar Maven", "Acabar a ficha", "Beber café"),
                    "tarefas.pdf"
            );
            Notification.show("PDF criado com sucesso!");
        });

        // Usa o ViewToolbar que já existe no projeto
        ViewToolbar toolbar = new ViewToolbar("Exportar PDF", exportButton);

        // Adiciona tudo à página
        Main content = new Main(toolbar);
        add(content);
    }
}

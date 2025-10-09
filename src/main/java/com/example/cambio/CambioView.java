package com.example.cambio;


import com.example.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;

@Route("cambio")
@PageTitle("Currency Exchange")
@Menu(order = 1, icon = "vaadin:money", title = "Currency Exchange")
public class CambioView extends Main {

    private final ExchangeService exchangeService;

    final NumberField amountField;
    final TextField fromCurrency;
    final TextField toCurrency;
    final Button convertBtn;
    final Grid<ExchangeResult> exchangeGrid;

    public CambioView(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;

        amountField = new NumberField("Amount");
        amountField.setPlaceholder("Enter amount");
        amountField.setMin(0.0);
        amountField.setStep(0.01);

        fromCurrency = new TextField("From Currency");
        fromCurrency.setPlaceholder("e.g., USD");

        toCurrency = new TextField("To Currency");
        toCurrency.setPlaceholder("e.g., EUR");

        convertBtn = new Button("Convert", e -> convertCurrency());
        convertBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        exchangeGrid = new Grid<>();
        exchangeGrid.addColumn(ExchangeResult::getFrom).setHeader("From");
        exchangeGrid.addColumn(ExchangeResult::getTo).setHeader("To");
        exchangeGrid.addColumn(ExchangeResult::getRate).setHeader("Rate");
        exchangeGrid.addColumn(ExchangeResult::getConvertedAmount).setHeader("Converted Amount");
        exchangeGrid.setItems(List.of()); // empty at start
        exchangeGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Currency Exchange", ViewToolbar.group(amountField, fromCurrency, toCurrency, convertBtn)));
        add(exchangeGrid);
    }

    private void convertCurrency() {
        try {
            String from = fromCurrency.getValue().toUpperCase();
            String to = toCurrency.getValue().toUpperCase();
            double amount = amountField.getValue();

            ExchangeResult result = exchangeService.convert(from, to, amount);
            exchangeGrid.setItems(result);

            Notification.show("Conversion successful", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        } catch (Exception e) {
            Notification.show("Conversion failed: " + e.getMessage(), 4000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}

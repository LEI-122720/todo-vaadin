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

import javax.money.UnknownCurrencyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections; // Import to use Collections.reverse()

@Route("cambio")
@PageTitle("Currency Exchange")
@Menu(order = 1, icon = "vaadin:money", title = "Currency Exchange")
public class CambioView extends Main {

    private final ExchangeService exchangeService;

    // List to store exchange history
    private final List<ExchangeResult> exchangeHistory = new ArrayList<>();

    final NumberField amountField;
    final TextField fromCurrency;
    final TextField toCurrency;
    final Button convertBtn;
    final Grid<ExchangeResult> exchangeGrid;

    public CambioView(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;

        amountField = new NumberField("Amount");
        amountField.setMin(0.01);
        //amountField.setHasControls(true);
        amountField.setErrorMessage("Amount must be greater than zero");

        fromCurrency = new TextField("From");
        fromCurrency.setPlaceholder("e.g., EUR");
        fromCurrency.setClearButtonVisible(true);
        toCurrency = new TextField("To");
        toCurrency.setPlaceholder("e.g., USD");
        toCurrency.setClearButtonVisible(true);

        convertBtn = new Button("Convert");
        convertBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        convertBtn.addClickListener(e -> convertCurrency());

        exchangeGrid = new Grid<>(ExchangeResult.class);

        // Updated columns to include 'amount'
        exchangeGrid.setColumns("amount", "from", "to", "rate", "convertedAmount");
        exchangeGrid.getColumnByKey("amount").setHeader("Amount");
        exchangeGrid.getColumnByKey("from").setHeader("From");
        exchangeGrid.getColumnByKey("to").setHeader("To");
        exchangeGrid.getColumnByKey("rate").setHeader("Rate");
        exchangeGrid.getColumnByKey("convertedAmount").setHeader("Converted Amount");

        exchangeGrid.setItems(exchangeHistory);
        exchangeGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Currency Exchange History", ViewToolbar.group(amountField, fromCurrency, toCurrency, convertBtn)));
        add(exchangeGrid);
    }

    private void convertCurrency() {
        String from = fromCurrency.getValue();
        String to = toCurrency.getValue();
        Double amount = amountField.getValue();

        // Input Validation
        if (from == null || from.isBlank() || to == null || to.isBlank() || amount == null || amount <= 0.0) {
            Notification.show("Please enter a valid positive amount and non-empty currency codes.", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_WARNING);
            return;
        }

        try {
            String fromUpper = from.toUpperCase();
            String toUpper = to.toUpperCase();

            // Perform conversion
            ExchangeResult result = exchangeService.convert(fromUpper, toUpper, amount);

            // 1. Add result to the history list
            exchangeHistory.add(result);

            // 2. Set the grid items from the history list (displaying newest first)
            List<ExchangeResult> reversedHistory = new ArrayList<>(exchangeHistory);
            Collections.reverse(reversedHistory);
            exchangeGrid.setItems(reversedHistory);

            Notification.show("Conversion successful", 3000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        } catch (UnknownCurrencyException e) {
            Notification.show("Conversion failed: A currency code is invalid or not supported.", 4000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification.show("Conversion failed: " + e.getMessage(), 4000, Notification.Position.BOTTOM_END)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}
package com.kraigmcfadden.imab.services;

import com.kraigmcfadden.imab.model.Account;
import com.kraigmcfadden.imab.model.Budget;
import com.kraigmcfadden.imab.model.BudgetGroup;
import com.kraigmcfadden.imab.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class AccountDisplayService {

    public String accountToHtmlString(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append(account.getName()).append(":<br>");
        sb.append("$").append(account.getCurrent()).append(" / $").append(account.getLimit()).append("<br>");
        for (BudgetGroup group : account.getBudgetGroups()) {
            addIndent(sb, 1).append(group.getName()).append(":<br>");
            addIndent(sb, 1).append("$").append(group.getCurrent()).append(" / $").append(group.getLimit()).append("<br>");
            for (Budget budget : group.getBudgets()) {
                addIndent(sb, 2).append(budget.getName()).append(":<br>");
                addIndent(sb, 2).append("$").append(budget.getCurrent()).append(" / $").append(budget.getLimit()).append("<br>");
                for (Expense expense : budget.getExpenses()) {
                    addIndent(sb, 3).append(expense.getDescription()).append(": ").append(expense.getCost()).append("<br>");
                }
            }
        }
        return sb.toString();
    }

    private StringBuilder addIndent(StringBuilder sb, int level) {
        sb.append("    ".repeat(Math.max(0, level)));
        return sb;
    }
}

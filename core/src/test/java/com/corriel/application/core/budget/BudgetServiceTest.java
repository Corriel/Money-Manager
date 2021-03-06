package com.corriel.application.core.budget;

import com.corriel.application.core.entity.Budget;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.entity.MonthBudget;
import com.corriel.application.core.repository.BudgetRepository;
import com.corriel.application.core.users.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetServiceTest {

    private Category first_category;
    private Category second_category;
    private Category third_category;

    private Map<String, BigDecimal> firstPartBudgetCategoryToExpense;
    private Map<String, BigDecimal> secondPartBudgetCategoryToExpense;

    @Before
    public void prepareTransactionCategories() {
        first_category = Category.builder().name("FIRST_CATEGORY").build();
        second_category = Category.builder().name("SECOND_CATEGORY").build();
        third_category = Category.builder().name("THIRD_CATEGORY").build();
    }

    @Before
    public void fillDataForMock() {
        firstPartBudgetCategoryToExpense = new HashMap<>();
        firstPartBudgetCategoryToExpense.put(first_category.getName(), new BigDecimal(123.45));
        firstPartBudgetCategoryToExpense.put(second_category.getName(), new BigDecimal(223.45));
        firstPartBudgetCategoryToExpense.put(third_category.getName(), new BigDecimal(323.45));

        secondPartBudgetCategoryToExpense = new HashMap<>();
        secondPartBudgetCategoryToExpense.put(first_category.getName(), new BigDecimal(100));
        secondPartBudgetCategoryToExpense.put(third_category.getName(), new BigDecimal(200));
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        UserService userService = mock(UserService.class);
        MonthBudgetService monthBudgetService = mock(MonthBudgetService.class);
        MonthBudget firstMonthBudget = new MonthBudget();
        firstMonthBudget.setId(1L);
        when(monthBudgetService.mapCategoryToExpenses(firstMonthBudget))
                .thenReturn(firstPartBudgetCategoryToExpense);
        MonthBudget secondMonthBudget = new MonthBudget();
        secondMonthBudget.setId(2L);
        when(monthBudgetService.mapCategoryToExpenses(secondMonthBudget))
                .thenReturn(secondPartBudgetCategoryToExpense);

        HashSet<MonthBudget> monthBudgets = new HashSet<>();
        monthBudgets.add(firstMonthBudget);
        monthBudgets.add(secondMonthBudget);
        Budget budget = new Budget();
        budget.setMonthBudgets(monthBudgets);

        BudgetRepository budgetRepository = mock(BudgetRepository.class);
        when(budgetRepository.find(1L)).thenReturn(budget);

        BudgetService budgetService = new BudgetService(userService, monthBudgetService);
        Map<String, BigDecimal> categoryToSummaryExpense = budgetService
                .mapCategoryToExpenses(budget);

        Map<String, BigDecimal> expectedMap = prepareExpectedMap();

        assertEquals(expectedMap, categoryToSummaryExpense);
    }

    private Map<String, BigDecimal> prepareExpectedMap() {
        Map<String, BigDecimal> expectedMap = new HashMap<>();

        expectedMap.put(first_category.getName(), firstPartBudgetCategoryToExpense.get(first_category.getName())
                .add(secondPartBudgetCategoryToExpense.get(first_category.getName())));
        expectedMap.put(second_category.getName(), firstPartBudgetCategoryToExpense.get(second_category.getName()));
        expectedMap.put(third_category.getName(), firstPartBudgetCategoryToExpense.get(third_category.getName())
                .add(secondPartBudgetCategoryToExpense.get(third_category.getName())));

        return expectedMap;
    }
}
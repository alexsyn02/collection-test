package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 610)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).toString());
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23,0), 2100).toString());
        //getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        UserMealWithExceed userMealWithExceed;
        Map<LocalDate, Integer> days = new HashMap<>();

        for(UserMeal meal : mealList) {
            if (!days.getOrDefault(meal.getDateTime().toLocalDate(), meal.getCalories()).equals(meal.getCalories())) {
                days.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            } else {
                days.put(meal.getDateTime().toLocalDate(),meal.getCalories());
            }
        }

         for(UserMeal meal : mealList) {
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceed = days.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                userMealWithExceed = new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
                userMealWithExceeds.add(userMealWithExceed);
            }
         }
        return userMealWithExceeds;
    }
}

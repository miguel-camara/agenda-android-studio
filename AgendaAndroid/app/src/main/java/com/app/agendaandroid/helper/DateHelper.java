package com.app.agendaandroid.helper;

import java.time.LocalDateTime;

public interface DateHelper {
    final int DAY = LocalDateTime.now().getDayOfMonth();
    final int MONTH = LocalDateTime.now().getMonthValue();
    final int YEAR = LocalDateTime.now().getYear();
    final int HOUR = LocalDateTime.now().getHour();
    final int MINUTE = LocalDateTime.now().getMinute();
}
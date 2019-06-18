package com.kizitonwose.calendarview.ui

import com.kizitonwose.calendarview.model.CalendarMonth

interface MonthScrollListener {
    fun invoke(month: CalendarMonth)
}
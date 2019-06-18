package com.kizitonwose.calendarview.ui

import android.support.annotation.LayoutRes
import android.support.annotation.Px
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.utils.inflate

data class DayConfig(
    @Px val width: Int,
    @Px val height: Int,
    @LayoutRes val dayViewRes: Int,
    val viewBinder: DayBinder<ViewContainer>
)

class DayHolder(private val config: DayConfig) {

    private lateinit var dateView: View
    private lateinit var containerView: FrameLayout
    private lateinit var viewContainer: ViewContainer

    var day: CalendarDay? = null

    fun inflateDayView(parent: LinearLayout): View {
        dateView = parent.inflate(config.dayViewRes).apply {
            // We ensure the layout params of the supplied child view is
            // MATCH_PARENT so it fills the parent container.
            layoutParams = layoutParams.apply {
                height = ViewGroup.LayoutParams.MATCH_PARENT
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
        containerView = FrameLayout(parent.context).apply {
            // This will be placed in the WeekLayout(A LinearLayout) hence we
            // use LinearLayout.LayoutParams and set the weight appropriately.
            // The parent's wightSum is already set to 7 to accommodate seven week days.
            layoutParams = LinearLayout.LayoutParams(config.width, config.height, 1F)
            addView(dateView)
        }
        return containerView
    }

    fun bindDayView(currentDay: CalendarDay) {
        this.day = currentDay
        if (::viewContainer.isInitialized.not()) {
            viewContainer = config.viewBinder.create(dateView)
        }

        val dayHash = currentDay.date.hashCode()
        if (containerView.id != dayHash) {
            containerView.id = dayHash
        }
        config.viewBinder.bind(viewContainer, currentDay)
    }

    fun reloadView() {
        day?.let { bindDayView(it) }
    }

}

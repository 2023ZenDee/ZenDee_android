package com.ggd.zendee.feature.map

import androidx.lifecycle.viewModelScope
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import kotlinx.coroutines.launch

class MapViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()






    private fun event(event : Event){

        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object UnknownException : Event()
    }
}
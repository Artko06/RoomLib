package com.example.roomlib

sealed interface ITelContactEvent {
    object SaveTelContactEvent : ITelContactEvent
    data class SetFirstName(val firstName: String) : ITelContactEvent
    data class SetLastName(val lastName: String) : ITelContactEvent
    data class SetTelephoneNumber(val telephoneNumber: String) : ITelContactEvent

    data class DeleteTelContactEvent(val telContact: TelContact) : ITelContactEvent
    data class SortTelContactEvent(val sortType: SortType) : ITelContactEvent

    object ShowDialogWindow : ITelContactEvent
    object CloseDialogWindow : ITelContactEvent
}
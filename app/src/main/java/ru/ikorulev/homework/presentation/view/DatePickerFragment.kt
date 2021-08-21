package ru.ikorulev.homework.presentation.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.runBlocking
import ru.ikorulev.homework.presentation.viewmodel.FilmViewModel
import java.util.*

class DatePickerFragment : DialogFragment() {

    private val viewModel: FilmViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        c.time = viewModel.datePickerFilmItem.watchDate
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), { _, year, month, day ->
            val watchDate = GregorianCalendar(year, month, day).time
            runBlocking {
                viewModel.onDatePickerDialogClick(watchDate)
            }
        }, year, month, day)
        dpd.setTitle("Выберите дату просмотра")
        return dpd
    }
}
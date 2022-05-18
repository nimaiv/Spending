package hr.nimai.spending.presentation.add_racun

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nimai.spending.domain.use_case.AddRacunUseCases
import javax.inject.Inject

@HiltViewModel
class AddRacunViewModel @Inject constructor(
    private val addRacunUseCases: AddRacunUseCases
) : ViewModel() {


}
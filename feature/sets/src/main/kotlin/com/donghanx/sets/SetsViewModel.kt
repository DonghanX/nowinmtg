package com.donghanx.sets

import androidx.lifecycle.ViewModel
import com.donghanx.data.repository.sets.SetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetsViewModel @Inject constructor(private val setsRepository: SetsRepository) : ViewModel()

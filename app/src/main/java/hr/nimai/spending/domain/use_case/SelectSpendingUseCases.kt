package hr.nimai.spending.domain.use_case

data class SelectSpendingUseCases(
    val getTrgovineSuspend: GetTrgovineSuspend,
    val getProizvodiSuspend: GetProizvodiSuspend,
    val getTipoviProizvodaSuspend: GetTipoviProizvodaSuspend

)

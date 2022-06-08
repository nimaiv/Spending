package hr.nimai.spending.domain.util

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnExitClick : CameraUIAction()
}

package hr.nimai.spending.domain.util

sealed class CameraUIAction {
    object OnCameraClick : CameraUIAction()
    object OnGalleryViewClick : CameraUIAction()
    object OnExitClick : CameraUIAction()
}

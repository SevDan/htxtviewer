package model.objects

data class SliceBatch(
    val head: FileSlice,
    val preview: FileSlice? = null,
    val next: FileSlice? = null,
    val bufferedPreview: FileSlice? = null,
    val bufferedNext: FileSlice? = null
)
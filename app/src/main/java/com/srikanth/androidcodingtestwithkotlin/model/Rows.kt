import com.google.gson.annotations.SerializedName

/**
 * Data class for About canada api response row array.
 */
data class Rows(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("imageHref") val imageHref: String
)
import com.google.samples.apps.nowinandroid.core.model.data.News

data class NewsUiState(
    val news: List<News> = emptyList(),
    val isRefreshing: Boolean = false,
    val hasRefreshError : Boolean = false,
    val error: NewsError? = null,
)

enum class NewsError {
    NETWORK,
    SERVER,
    LOCAL,
    UNKNOWN,
}
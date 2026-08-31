import com.google.samples.apps.nowinandroid.core.model.data.News

data class NewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: NewsError? = null,
)

enum class NewsError {
    NETWORK,
    SERVER,
    LOCAL,
    UNKNOWN,
}
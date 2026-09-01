plugins {
    alias(libs.plugins.nowinandroid.android.feature.impl)
    alias(libs.plugins.nowinandroid.android.library.compose)
}

android {
    namespace = "com.google.samples.apps.nowinandroid.feature.news.impl"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.news.api)

    testImplementation(projects.core.testing)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)
    androidTestImplementation(projects.core.testing)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}
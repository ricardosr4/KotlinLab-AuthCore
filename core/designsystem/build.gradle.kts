plugins {
    id("kotlinlab.android.library")
    id("kotlinlab.android.compose")
}

android {
    namespace = "com.kotlinlab.authcore.core.designsystem"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}

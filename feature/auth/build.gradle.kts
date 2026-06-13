plugins {
    id("kotlinlab.android.library")
    id("kotlinlab.android.compose")
    id("kotlinlab.android.hilt")
}

android {
    namespace = "com.kotlinlab.authcore.feature.auth"

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":domain:auth"))
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}

plugins {
    id("kotlinlab.android.application")
    id("kotlinlab.android.compose")
    id("kotlinlab.android.hilt")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.kotlinlab.authcore"

    defaultConfig {
        applicationId = "com.kotlinlab.authcore"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":data:auth"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}

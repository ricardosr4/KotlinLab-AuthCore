plugins {
    id("kotlinlab.android.library")
    id("kotlinlab.android.hilt")
}

android {
    namespace = "com.kotlinlab.authcore.data.auth"

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain:auth"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.coroutines.play.services)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}

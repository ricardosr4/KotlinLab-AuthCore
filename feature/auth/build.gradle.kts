plugins {
    id("kotlinlab.android.library")
    id("kotlinlab.android.compose")
}

android {
    namespace = "com.kotlinlab.authcore.feature.auth"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:navigation"))
    implementation(project(":domain:auth"))
}

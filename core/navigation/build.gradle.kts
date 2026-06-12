plugins {
    id("kotlinlab.android.library")
    id("kotlinlab.android.compose")
}

android {
    namespace = "com.kotlinlab.authcore.core.navigation"
}

dependencies {
    implementation(project(":core:common"))
}

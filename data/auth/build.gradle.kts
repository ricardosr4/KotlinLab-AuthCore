plugins {
    id("kotlinlab.android.library")
}

android {
    namespace = "com.kotlinlab.authcore.data.auth"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain:auth"))
}

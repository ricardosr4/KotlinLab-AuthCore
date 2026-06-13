plugins {
    id("kotlinlab.jvm.library")
}

dependencies {
    implementation(project(":core:common"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}

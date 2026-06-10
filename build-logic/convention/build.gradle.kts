plugins {
    `kotlin-dsl`
}

group = "com.kotlinlab.buildlogic"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kotlinlab.android.application"
            implementationClass = "KotlinLabAndroidApplicationPlugin"
        }
        register("androidCompose") {
            id = "kotlinlab.android.compose"
            implementationClass = "KotlinLabAndroidComposePlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

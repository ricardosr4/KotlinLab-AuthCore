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
        register("androidLibrary") {
            id = "kotlinlab.android.library"
            implementationClass = "KotlinLabAndroidLibraryPlugin"
        }
        register("jvmLibrary") {
            id = "kotlinlab.jvm.library"
            implementationClass = "KotlinLabJvmLibraryPlugin"
        }
        register("androidHilt") {
            id = "kotlinlab.android.hilt"
            implementationClass = "KotlinLabAndroidHiltPlugin"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(libs.hilt.gradle.plugin)
}

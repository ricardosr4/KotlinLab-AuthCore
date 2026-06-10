import org.gradle.api.JavaVersion

internal object KotlinLabProjectConfig {
    const val COMPILE_SDK = 37
    const val MIN_SDK = 26
    const val TARGET_SDK = 37

    val JAVA_VERSION = JavaVersion.VERSION_17
    const val JAVA_LANGUAGE_VERSION = 17
}

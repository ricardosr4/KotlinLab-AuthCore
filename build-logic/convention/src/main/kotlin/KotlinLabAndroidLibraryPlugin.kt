import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class KotlinLabAndroidLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            extensions.getByType<JavaPluginExtension>().apply {
                toolchain {
                    languageVersion.set(
                        JavaLanguageVersion.of(KotlinLabProjectConfig.JAVA_LANGUAGE_VERSION)
                    )
                }
            }

            extensions.getByType<KotlinAndroidProjectExtension>().compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = KotlinLabProjectConfig.COMPILE_SDK

                compileOptions {
                    sourceCompatibility = KotlinLabProjectConfig.JAVA_VERSION
                    targetCompatibility = KotlinLabProjectConfig.JAVA_VERSION
                }

                defaultConfig {
                    minSdk = KotlinLabProjectConfig.MIN_SDK
                }
            }
        }
    }
}

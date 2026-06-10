import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class KotlinLabAndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            pluginManager.withPlugin("com.android.application") {
                extensions.configure<ApplicationExtension> {
                    buildFeatures {
                        compose = true
                    }
                }
            }

            pluginManager.withPlugin("com.android.library") {
                extensions.configure<LibraryExtension> {
                    buildFeatures {
                        compose = true
                    }
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val composeBom = libs.findLibrary("androidx-compose-bom").get()

            dependencies.apply {
                add("implementation", platform(composeBom))
                add("implementation", libs.findLibrary("androidx-compose-material3").get())
                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("androidTestImplementation", platform(composeBom))
                add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
                add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
            }
        }
    }
}

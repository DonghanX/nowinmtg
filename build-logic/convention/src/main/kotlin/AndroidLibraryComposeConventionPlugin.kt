import com.android.build.gradle.LibraryExtension
import com.donghanx.convention.configureAndroidCompose
import com.donghanx.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            val extension = extensions.getByType<LibraryExtension>()

            dependencies { add("implementation", libs.findLibrary("kotlinx.immutable").get()) }

            configureAndroidCompose(extension)
        }
    }
}

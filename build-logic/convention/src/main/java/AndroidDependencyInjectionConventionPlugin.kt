import com.nibalk.tasky.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDependencyInjectionConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {

            dependencies {
                "implementation"(libs.findBundle("koin").get())
            }
        }
    }
}

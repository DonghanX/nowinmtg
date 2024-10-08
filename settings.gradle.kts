pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "nowinmtg"
include(":app")
include(":core:network")
include(":core:model")
include(":core:data")
include(":core:common")
include(":core:design")
include(":core:database")
include(":feature:randomcards")
include(":feature:carddetails")
include(":feature:sets")
include(":feature:search")
include(":core:domain")
include(":core:ui")
include(":feature:favorites")
include(":feature:setdetails")

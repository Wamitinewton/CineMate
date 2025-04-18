pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "CineMate"
include(":citeMate")
include(":shared-ui")
include(":core")
include(":network")
include(":database")
include(":features:trending")
include(":features:auth")
include(":navigation")
include(":prefs")
include(":features:movies")
include(":features:shows")
include(":features:search")
include(":features:settings")
include(":shared")
include(":domain")
include(":features:people")
include(":features:reviews")

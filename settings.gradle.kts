plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "untitled"
include("robot_data")
include("robot_programm")
include("robot_command_parser")
include("cleaner_api")
include("cleaner_impl")
include("cleaner_programm")
include("monads")

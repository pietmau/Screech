import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import sh.christian.ozone.api.generator.ApiReturnType

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    id("sh.christian.ozone.generator")
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}

android {
    namespace = "org.screech"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies{
    lexicons("sh.christian.ozone:lexicons:0.3.0")
}

lexicons {
    namespace.set("org.screech")

    generateApi("ScreechClient") {
        packageName.set("org.screech")
        withKtorImplementation("ScreechClientImpl")
        returnType.set(ApiReturnType.Result)
        suspending.set(true)
        exclude("""app\.bsky\.unspecced\..*""")
    }
}

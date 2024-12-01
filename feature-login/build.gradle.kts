import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
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

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)

            implementation("io.insert-koin:koin-android:4.1.0-Beta1")
        }

        commonMain.dependencies {
            implementation(project(":data-network"))
            implementation("io.insert-koin:koin-core:4.1.0-Beta1")
            implementation("io.insert-koin:koin-compose:4.1.0-Beta1")
            //implementation("io.insert-koin:koin-compose-viewmodel:4.1.0-Beta1")

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.serialization.json)
        }
        iosMain.dependencies {
            //implementation("io.insert-koin:koin-core:4.1.0-Beta1")

            //implementation("io.insert-koin:koin-compose:4.1.0-Beta1")
            //implementation("io.insert-koin:koin-compose-viewmodel:4.1.0-Beta1")
            implementation("io.insert-koin:koin-compose:4.1.0-Beta1")

        }

        iosX64Main.dependencies {
            implementation("io.insert-koin:koin-core:4.1.0-Beta1")
        }
        iosArm64Main.dependencies {
            implementation("io.insert-koin:koin-core:4.1.0-Beta1")
        }
        iosSimulatorArm64Main.dependencies {
            implementation("io.insert-koin:koin-core:4.1.0-Beta1")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

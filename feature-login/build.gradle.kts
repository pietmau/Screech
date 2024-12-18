import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.devtools.ksp")
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
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.0-Beta1")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation:4.1.0-Beta1")
            // Koin Annotations
            api("io.insert-koin:koin-annotations:2.0.0-Beta1")


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
            implementation("io.insert-koin:koin-compose:4.1.0-Beta1")
        }
    }

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
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

dependencies {
    add("kspCommonMainMetadata", "io.insert-koin:koin-ksp-compiler:2.0.0-Beta2")
    add("kspAndroid", "io.insert-koin:koin-ksp-compiler:2.0.0-Beta2")
    add("kspIosX64", "io.insert-koin:koin-ksp-compiler:2.0.0-Beta2")
    add("kspIosArm64", "io.insert-koin:koin-core:2.0.0-Beta2")
    add("kspIosSimulatorArm64", "io.insert-koin:koin-core:2.0.0-Beta2")
}

// Trigger Common Metadata Generation from Native tasks
project.tasks.withType(KotlinCompilationTask::class.java).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
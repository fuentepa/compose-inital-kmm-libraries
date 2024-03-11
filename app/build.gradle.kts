plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
}

android {
    namespace = "com.compose.kmplibs"
    compileSdk = 34
    buildToolsVersion = "34.0.0"

    defaultConfig {
        applicationId = "com.compose.kmm"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // KSP - To use generated sources
    //sourceSets["main"].java.srcDirs("build/generated/ksp/main/kotlin")
    sourceSets.forEach {
        it.java.srcDirs.plusElement("build/generated/ksp/${it.name}/kotlin")
    }

    //KSP para multiples variantes
    /*applicationVariants.all {
        sourceSets.forEach {
            it.javaDirectories.plus("build/generated/ksp/${it.name}/kotlin")
        }
    }*/

    ksp {
        arg("KOIN_CONGIG_CHECK", "true")  //para chequear si nos hemos dejado algo sin configurar de koin
    }
}

dependencies {
    implementation( project(":data"))
    implementation( project(":usecases"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.io.arrow)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android) //o core si es kotlin solo
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    //koin Anotations
    implementation(platform(libs.koin.anotations.bom))
    ksp(libs.koin.ksp)

    //Ktorfit
    implementation(libs.ktorfit.lib)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    ksp(libs.ktorfit.ksp)

    //Datastore
    implementation(libs.android.datastore.preferences)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
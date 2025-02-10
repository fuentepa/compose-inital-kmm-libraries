import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinxserialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.compose.kmplibs"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.compose.kmplibs"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        register("development") {
            val keystorePropertiesFile = file("../keys/keystoreDev.properties")
            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))

            keyAlias = keystoreProperties["keyAliasDev"] as String
            keyPassword = keystoreProperties["keyPasswordDev"] as String
            storeFile = file(keystoreProperties["storeFileDev"] as String)
            storePassword = keystoreProperties["storePasswordDev"] as String
        }
        register("release") {
            // TODO: Uncomment these lines and set up your release keystore
//            val keystorePropertiesFile = file("../local.properties")

//            keyAlias = keystoreProperties["keyAliasRel"] as String
//            keyPassword = keystoreProperties["keyPasswordRel"] as String
//            storeFile = file(keystoreProperties["storeFileRel"] as String)
//            storePassword = keystoreProperties["storePasswordRel"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }

    flavorDimensions += listOf("environment")
    productFlavors {
        val privateApiKeyPropertiesFile = file("../local.properties")
        val privateApiKeyProperties = Properties()
        privateApiKeyProperties.load(FileInputStream(privateApiKeyPropertiesFile))

        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-DEV"
            signingConfig = signingConfigs.getByName("development")

            buildConfigField("String", "TMDB_BASE_URL", "\"https://api.themoviedb.org\"")
            buildConfigField("String", "TMDB_IMAGE_URL", "\"https://image.tmdb.org/t/p\"")
            buildConfigField("String", "API_KEY", "\"b48053648cfc700c69cd0e280943fd32\"")
            // Uncomment this line to secure project's private_api_key
            // buildConfigField("String", "PRIVATE_API_KEY", "\"${privateApiKeyProperties.getProperty("privateApiKey")}\"")
            buildConfigField(
                "String",
                "ACCESS_TOKEN",
                "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNDgwNTM2NDhjZmM3MDBjNjljZDBlMjgwOTQzZmQzMiIsInN1YiI6IjY2MTY2YzU4MjQyZjk0MDE3ZGM0Yjg4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rxwpXcO-JxrRIf7UDzl1jRkVzyjsY8h-9neWttDrNTU\""
            )
        }
        create("pro") {
            dimension = "environment"
            signingConfig = signingConfigs.getByName("release")

            // Redefine with production values. If they're the same, include this field into defaultConfig section.
            buildConfigField("String", "TMDB_BASE_URL", "\"https://gateway.marvel.com:443/v1/public/\"")
            buildConfigField("String", "API_KEY", "\"ff1bbafd775a6d0209d677f348c22d6b\"")
            // Uncomment this line to secure project's private_api_key
            // buildConfigField("String", "PRIVATE_API_KEY", "\"${privateApiKeyProperties.getProperty("privateApiKey")}\"")
            buildConfigField(
                "String",
                "ACCESS_TOKEN",
                "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNDgwNTM2NDhjZmM3MDBjNjljZDBlMjgwOTQzZmQzMiIsInN1YiI6IjY2MTY2YzU4MjQyZjk0MDE3ZGM0Yjg4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rxwpXcO-JxrRIf7UDzl1jRkVzyjsY8h-9neWttDrNTU\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    ksp {
        arg("KOIN_CONGIG_CHECK", "true")  //para chequear si nos hemos dejado algo sin configurar de koin
        arg("KOIN_DEFAULT_MODULE", "true")
    }

}

dependencies {
    implementation(project(":data"))
    implementation(project(":usecases"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.kotlinx.serialization.json)

    // Añadir las dependencias de Navigation Compose
    implementation(libs.androidx.navigation.compose)

    implementation(libs.io.arrow)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android) //o core si es kotlin solo
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    //koin Annotations
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)

    //Ktorfit
    implementation(libs.ktorfit.lib)
    implementation(libs.ktorfit.converters.call)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    ksp(libs.ktorfit.ksp)

    implementation(libs.coil.compose)

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

tasks.register("verifyKoin") {
    dependsOn("checkKoinModules")
    doLast {
        println("Verificación de Koin completada.")
    }
}

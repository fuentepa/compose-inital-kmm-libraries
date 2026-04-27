plugins {
    id("kotlin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.koin.compiler)
}

koinCompiler {
    userLogs = true
}

dependencies {
    implementation( project(":data"))
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.annotations)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
}

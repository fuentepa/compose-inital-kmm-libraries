plugins {
    id("kotlin")
    alias(libs.plugins.kotlinxserialization)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.ksp)
    alias(libs.plugins.koin.compiler)
}

koinCompiler {
    userLogs = true
}

ktorfit {
    // Disable Ktorfit compiler plugin to avoid Kotlin compiler incompatibilities.
    // Ktorfit will still work via KSP (ksp(libs.ktorfit.ksp)).
    compilerPluginVersion.set("-")
}

dependencies {
    implementation(libs.ktorfit.lib)
    implementation(libs.ktorfit.converters.response)
    implementation(libs.ktor.client.serialization)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.annotations)
    implementation(libs.koin.core)
    ksp(libs.ktorfit.ksp)
}

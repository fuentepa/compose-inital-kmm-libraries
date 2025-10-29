plugins {
    id("kotlin")
    alias(libs.plugins.kotlinxserialization)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.ktorfit.lib)
    implementation(libs.ktorfit.converters.response)
    implementation(libs.ktor.client.serialization)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)
    ksp(libs.ktorfit.ksp)
}

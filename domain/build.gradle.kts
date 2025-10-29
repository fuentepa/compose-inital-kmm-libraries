plugins {
    id("kotlin")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation( project(":data"))
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    implementation(libs.kotlinx.coroutines.core)
    ksp(libs.koin.ksp)
}

plugins {
    id("kotlin")
    alias(libs.plugins.ksp)
}


dependencies {
    implementation( project(":data"))
    implementation(libs.io.arrow)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)
}

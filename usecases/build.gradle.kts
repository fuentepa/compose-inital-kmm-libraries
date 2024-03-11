plugins {
    id("kotlin")
}

dependencies {
    implementation( project(":data"))
    implementation(libs.io.arrow)
}

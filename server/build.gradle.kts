plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.rpc)
    application
    alias(libs.plugins.kotlinSerialization)
}

group = "net.tactware.store"
version = "1.0.0"
application {
    mainClass.set("net.tactware.store.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.auth)
    implementation(libs.kotlinx.rpc.server)
    implementation(libs.kotlinx.rpc.server.ktor)
    implementation(libs.kotlinx.rpc.serialization)

    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.bcrypt)


}
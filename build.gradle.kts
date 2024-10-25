import org.apache.tools.ant.taskdefs.condition.Os
import java.io.ByteArrayOutputStream
import java.io.StringReader
import javax.json.Json

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlin.rpc) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}

tasks.register("ciIos") {
    doLast {
        if (Os.isFamily(Os.FAMILY_MAC)) {
            runExec(listOf("brew", "install", "kdoctor"))
            runExec(listOf("kdoctor"))
            val devicesJson = runExec(
                listOf(
                    "xcrun",
                    "simctl",
                    "list",
                    "devices",
                    "available",
                    "-j"
                )
            )
            val devicesList = Json.createReader(StringReader(devicesJson)).use { it.readObject() }
                .getJsonObject("devices")
                .let { devicesMap ->
                    devicesMap.keys
                        .filter { it.startsWith("com.apple.CoreSimulator.SimRuntime.iOS") }
                        .map { devicesMap.getJsonArray(it) }
                }
                .map { jsonArray -> jsonArray.map { it.asJsonObject() } }
                .flatten()
                .filter { it.getBoolean("isAvailable") }
                .filter {
                    listOf("iphone 1").any { device ->
                        it.getString("name").contains(device, true)
                    }
                }
            println("Devices:${devicesList.joinToString { "\n" + it["udid"] + ": " + it["name"] }}")
            val device = devicesList.firstOrNull()
            println("Selected:\n${device?.getString("udid")}: ${device?.getString("name")}")
            runExec(
                listOf(
                    "xcodebuild",
                    "-project",
                    "${rootDir.path}/iosApp/iosApp.xcodeproj",
                    "-scheme",
                    "iosApp",
                    "-configuration",
                    "Debug",
                    "OBJROOT=${rootDir.path}/build/ios",
                    "SYMROOT=${rootDir.path}/build/ios",
                    "-destination",
                    "id=${device?.getString("udid")}",
                    "-allowProvisioningDeviceRegistration",
                    "-allowProvisioningUpdates"
                )
            )
        }
    }
}

fun runExec(commands: List<String>): String = object : ByteArrayOutputStream() {
    override fun write(p0: ByteArray, p1: Int, p2: Int) {
        print(String(p0, p1, p2))
        super.write(p0, p1, p2)
    }
}.let { resultOutputStream ->
    exec {
        if (System.getenv("JAVA_HOME") == null) {
            System.getProperty("java.home")?.let { javaHome ->
                environment = environment.toMutableMap().apply {
                    put("JAVA_HOME", javaHome)
                }
            }
        }
        commandLine = commands
        standardOutput = resultOutputStream
        println("commandLine: ${this.commandLine.joinToString(separator = " ")}")
    }.apply { println("ExecResult: $this") }
    String(resultOutputStream.toByteArray())
}

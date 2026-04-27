import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    
    // Serializador JSON, si el Wizard no lo mete directamente confirmar que está en 
    // gradle/libs.version.toml e importar
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(project(":sample:sharedUI"))
    implementation(compose.desktop.currentOs)
    
        
    // Corrutinas (necesarias para el scope.launch)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "sample"
            packageVersion = "1.0.0"
        }
    }
}

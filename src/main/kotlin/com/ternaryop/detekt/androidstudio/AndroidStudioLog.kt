package com.ternaryop.detekt.androidstudio

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Detektion
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.api.OutputReport

private const val PARAM_SHOW = "show"
private const val SHOW_ID = "id"
private const val SHOW_MESSAGE = "message"
private const val DEFAULT_SHOW = "$SHOW_ID,$SHOW_MESSAGE"

/**
 * Generate log clickable from the Android Studio (and IntelliJ) terminal window
 */
class AndroidStudioLog : OutputReport() {
    override val ending: String = "txt"
    override val name = "AndroidStudio Warnings"
    private lateinit var showColumns: List<String>

    override fun init(config: Config) {
        super.init(config)
        showColumns = config
            .subConfig("android-studio-report")
            .valueOrNull<String>(PARAM_SHOW)
            .let { it?.trim()?.toLowerCase() ?: DEFAULT_SHOW }
            .split(",")
    }

    override fun render(detektion: Detektion): String? {
        val smells = detektion.findings.flatMap { it.value }
        return smells.joinToString("\n") { printFinding(it) }
    }

    private fun printFinding(finding: Finding) = with(StringBuilder()) {
        append("w: ${finding.file}")
        append(": (${finding.location.source.line}, ${finding.location.source.column})")
        showColumns.forEach {
            when (it) {
                SHOW_ID -> append(": ${finding.id}")
                SHOW_MESSAGE -> append(": ${finding.message}")
            }
        }
        toString()
    }
}

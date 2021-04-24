import kotlinx.ast.common.AstSource
import kotlinx.ast.common.klass.KlassComment
import kotlinx.ast.grammar.kotlin.common.summary
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import java.io.File

/**
 * Parses each Kotlin file in the given directory and checks whether it has KDoc or not. If not, it continues checking
 * the remaining Kotlin files, but it throws an exception at the end to fail the execution.
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw IllegalArgumentException("No arguments detected! (Expecting one argument with filepath to search)")
    }

    var success = true

    File(args[0]).walkTopDown()
        .filter { it.isFile && it.name.endsWith(".kt") }
        .forEach { kotlinSrcFile ->
            val source = AstSource.File(kotlinSrcFile.absolutePath)
            val kotlinFile = KotlinGrammarAntlrKotlinParser.parseKotlinFile(source).summary(attachRawAst = false)
            kotlinFile.onSuccess { astList ->
//                astList.forEach(Ast::print)
                val commentList = astList.filterIsInstance<KlassComment>()
                if (commentList.isEmpty()) {
                    System.err.println("ERROR: Could not find a documentation comment in $kotlinSrcFile!")
                    success = false
                } else {
                    println("PASS: Found a documentation comment in $kotlinSrcFile")
                }
            }.onFailure {
                System.err.println("Unable to determine AST for Kotlin file: $kotlinSrcFile. Skipping...")
                System.err.println("Error info: $it")
            }
        }

    check(success) {
        "Found 1+ classes without documentation, or there was a failure while parsing a kt file within the given " +
            "directory"
    }
}

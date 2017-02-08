package nlp

import edu.stanford.nlp.parser.lexparser.LexicalizedParser

/**
 * Created by Nelson on 2/2/2017.
 */
object NLP {

    val inquiry = "SBARQ"
    val englishParser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz")
    val spanishParser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz")

    def detectInquiry(sentence: String): Boolean = {
        val tree = spanishParser.parse(sentence)
        tree.pennPrint()
        if (tree.numChildren() > 0) {
            val clause = tree.getChild(0)
            if (clause.label().value() == inquiry) {
                return true
            }
        }
        false
    }

}

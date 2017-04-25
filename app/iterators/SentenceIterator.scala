package iterators

import java.util

import models.Sentence
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.DataSetPreProcessor
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.indexing.NDArrayIndex

import scala.collection.convert.wrapAll._

/**
 * Created by nigonzalez on 4/3/17.
 */
class SentenceIterator(sentences: List[Sentence], wordVectors: WordVectors, batchSize: Int, maxSize: Int, outputs: Array[String], feature: Int => Int) extends DataSetIterator {

    var position = 0
    val vectorSize = wordVectors.getWordVector(wordVectors.vocab().wordAtIndex(0)).length
    val tokenizerFactory = new DefaultTokenizerFactory
    tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor)

    override def next(size: Int): DataSet = {
        // Tokenize and filter
        val set = sentences.slice(position, position + size)
        position = position + size
        val tokens = set.map(s => tokenizerFactory.create(s.content).getTokens)
        val filtered = tokens.map(l => l.filter(s => wordVectors.hasWord(s)))
        val maxLength = filtered.map(_.length).max
        val max = if (maxLength > maxSize) maxLength else maxSize
        // create vectors
        val features = Nd4j.create(size, vectorSize, max)
        val labels = Nd4j.create(size, 2, max)
        val featuresMask = Nd4j.zeros(size, max)
        val labelsMask = Nd4j.zeros(size, max)
        // Add vectors
        filtered.zipWithIndex.foreach { case (list, i) =>
            list.take(max).zipWithIndex.foreach { case (token, j) =>
                val vector = wordVectors.getWordVectorMatrix(token)
                features.put(Array(NDArrayIndex.point(i), NDArrayIndex.all, NDArrayIndex.point(j)), vector)
                featuresMask.putScalar(Array(i, j), 1.0)
            }
            val idx = feature(i)
            val lastIdx = if (list.length < max) list.length else max
            labels.putScalar(Array(i, idx, lastIdx - 1), 1.0)
            labelsMask.putScalar(Array(i, lastIdx - 1), 1.0)
        }
        new DataSet(features, labels, featuresMask, labelsMask)
    }

    override def batch(): Int = batchSize

    override def cursor(): Int = position

    override def totalExamples(): Int = sentences.length

    override def resetSupported(): Boolean = true

    override def inputColumns(): Int = vectorSize

    override def getPreProcessor: DataSetPreProcessor = throw new UnsupportedOperationException

    override def setPreProcessor(dataSetPreProcessor: DataSetPreProcessor): Unit = throw new UnsupportedOperationException

    override def getLabels: util.List[String] = scala.collection.JavaConversions.seqAsJavaList(outputs)

    override def totalOutcomes(): Int = outputs.length

    override def reset(): Unit = {
        position = 0
    }

    override def asyncSupported(): Boolean = false

    override def numExamples(): Int = totalExamples()

    override def next(): DataSet = next(batchSize)

    override def hasNext: Boolean = position < sentences.length
}

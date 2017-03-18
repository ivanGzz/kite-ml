package learning

import java.io.{FileOutputStream, File}
import java.sql.Date

import models.{Network, Networks, UserCompetencies}

import org.datavec.api.records.reader.impl.collection.ListStringRecordReader
import org.datavec.api.split.ListStringSplit

import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.Builder
import org.deeplearning4j.nn.conf.Updater
import org.deeplearning4j.nn.conf.layers.{OutputLayer, DenseLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.deeplearning4j.util.ModelSerializer

import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nigonzalez on 2/14/17.
 */
object UserCompetencyNet {

    val batchSize = 100
    val epochs = 2
    val inputs = 4
    val outputs = 4
    val hiddens = 20
    val rngSeed = 123
    val pathToSave = "public/files/network-"

    def train: Future[Boolean] = {
        UserCompetencies.getUserCompetenciesCount.flatMap { res =>
            if (res <= 1000) {
                throw new Exception()
            } else {
                val entries = res - res % 1000
                UserCompetencies.getUserCompetencies(entries)
            }
        }.flatMap { res =>
            println("Start...")
            val train = res.size * 80 / 100
            println("Training with " + train)
            val userCompetencies = scala.collection.JavaConversions.seqAsJavaList(res.take(train).map(_.toList))
            val userCompetenciesTest = scala.collection.JavaConversions.seqAsJavaList(res.drop(train).map(_.toList))
            val rr = new ListStringRecordReader()
            rr.initialize(new ListStringSplit(userCompetencies))
            val iterator = new RecordReaderDataSetIterator(rr, batchSize, 0, 4)
            val rrTest = new ListStringRecordReader()
            rrTest.initialize(new ListStringSplit(userCompetenciesTest))
            val iteratorTest = new RecordReaderDataSetIterator(rrTest, batchSize, 0, 4)
            val conf = new Builder()
            conf.seed(rngSeed)
            conf.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            conf.iterations(1)
            conf.learningRate(0.006)
            conf.updater(Updater.NESTEROVS).momentum(0.9)
            conf.regularization(true).l2(1e-4)
            val inputLayer = new DenseLayer.Builder()
            inputLayer.nIn(inputs)
            inputLayer.nOut(hiddens)
            inputLayer.activation("relu")
            inputLayer.weightInit(WeightInit.XAVIER)
            val outputLayer = new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
            outputLayer.nIn(hiddens)
            outputLayer.nOut(outputs)
            outputLayer.activation("softmax")
            outputLayer.weightInit(WeightInit.XAVIER)
            val list = conf.list()
            list.layer(0, inputLayer.build())
            list.layer(1, outputLayer.build())
            list.pretrain(false)
            list.backprop(true)
            val configuration = list.build()
            val model = new MultiLayerNetwork(configuration)
            model.init()
            model.setListeners(new ScoreIterationListener(1))
            println("Training")
            for (i <- 0 until epochs) {
                println("Epoch")
                model.fit(iterator)
            }
            multiLayerNetwork = Some(model)
            println("Evaluating")
            val eval = new Evaluation(outputs)
            while (iteratorTest.hasNext) {
                val next = iteratorTest.next()
                val output = model.output(next.getFeatureMatrix)
                eval.eval(next.getLabels, output)
            }
            println("End")
            println(eval.stats())
            val today = new java.util.Date()
            val file = new File(pathToSave + today.getTime + ".zip")
            val outputStream = new FileOutputStream(file)
            ModelSerializer.writeModel(model, outputStream, false)
            val network = Network(0L, "user_competency", "/assets/files/" + file.getName, 1, new Date(today.getTime))
            Networks.addToNetworksVersioned(network)
        }.map(res =>
            true
        ).recover {
            case e => {
                e.printStackTrace()
                false
            }
        }
    }

    var multiLayerNetwork: Option[MultiLayerNetwork] = None

    def load: Future[Boolean] = {
        Networks.getLatestNetworkByName("user_competency").map {
            case Some(network) => {
                multiLayerNetwork = Some(ModelSerializer.restoreMultiLayerNetwork(new File(network.path)))
                true
            }
            case None => {
                false
            }
        }
    }

    def load(version: Int): Future[Boolean] = {
        Networks.getNetworkByNameAndVersion("user_competency", version).map {
            _ match {
                case Some(network) => {
                    multiLayerNetwork = Some(ModelSerializer.restoreMultiLayerNetwork(new File(network.path)))
                    true
                }
                case None => {
                    false
                }
            }
        }
    }

    def rate(competencies: List[Int]): String = {
        val sum = competencies.sum
        sum match {
            case x if x > 9 => "A"
            case x if x > 6 => "B"
            case x if x > 3 => "C"
            case _ => "D"
        }
    }

}

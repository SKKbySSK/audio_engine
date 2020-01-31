package work.ksprogram.audio_graph.nodes

import android.media.MediaCodec
import android.media.MediaFormat

enum class PreparationState {
    none,
    preparing,
    prepared,
}

interface OutputNodeCallback {
    fun prepared(node: work.ksprogram.audio_graph.nodes.AudioOutputNode)
    fun bufferAvailable(node: work.ksprogram.audio_graph.nodes.AudioOutputNode)
}

open abstract class AudioNativeNode(val id: Int) {
    companion object {
        val nodes: MutableMap<Int, work.ksprogram.audio_graph.nodes.AudioNativeNode> = mutableMapOf()
    }

    var isDisposed = false
    open fun dispose() {
        isDisposed = true
    }
}

open interface PlayableNode {
    fun play()
    fun pause()
}

open interface PositionableNode {
    var positionUs: Long
}

open interface AudioSingleInputNode {
    fun setInputNode(node: work.ksprogram.audio_graph.nodes.AudioOutputNode)
}

open interface AudioMultipleInputNode {
    fun addInputNode(node: work.ksprogram.audio_graph.nodes.AudioOutputNode)
    fun removeInputNode(node: work.ksprogram.audio_graph.nodes.AudioOutputNode)
}

open abstract class AudioInputNode(id: Int) : work.ksprogram.audio_graph.nodes.AudioNativeNode(id) {
    abstract fun write(format: MediaFormat, info: MediaCodec.BufferInfo, buffer: ByteArray)
}

open abstract class AudioOutputNode(id: Int) : work.ksprogram.audio_graph.nodes.AudioNativeNode(id) {
    abstract fun nextBuffer(): Pair<MediaCodec.BufferInfo, ByteArray>?
    abstract fun prepare()
    abstract fun getPreparationState(): work.ksprogram.audio_graph.nodes.PreparationState
    abstract fun getMediaFormat(): MediaFormat
    abstract fun bufferAvailable(): Boolean

    var callback: work.ksprogram.audio_graph.nodes.OutputNodeCallback? = null
    var volume: Double = 1.0

    override fun dispose() {
        callback = null
        super.dispose()
    }
}
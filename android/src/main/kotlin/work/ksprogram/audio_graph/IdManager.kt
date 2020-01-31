package work.ksprogram.audio_graph

class IdManager(val key: String) {
    companion object {
        private val ids: MutableMap<String, Int> = mutableMapOf()

        fun generateId(key: String): Int {
            val id = work.ksprogram.audio_graph.IdManager.Companion.ids[key] ?: 0
            work.ksprogram.audio_graph.IdManager.Companion.ids[key] = id + 1
            return id
        }
    }

    fun generateId(): Int {
        return work.ksprogram.audio_graph.IdManager.Companion.generateId(key)
    }
}
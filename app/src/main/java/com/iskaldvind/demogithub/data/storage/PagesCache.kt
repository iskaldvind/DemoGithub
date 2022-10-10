package com.iskaldvind.demogithub.data.storage

/**
 * Store marks from which data is requested
 */
class PagesCache {
    private val values: MutableList<Int> = mutableListOf(0)
    val itemsLimit: Int = 100

    /**
     * Add last id to values if it represents the next mark to obtain data
     */
    fun addLast(ids: List<Int>) {
        val lastId = ids.last()
        if (ids.size == itemsLimit && values.last() < lastId) values.add(lastId)
    }

    /**
     * Get previous mark for provided data
     */
    fun getPrev(page: Int, ids: List<Int>): Int? {
        val lastId = getNext(ids)
        return when {
            page == 0 -> null
            lastId == null -> values[values.size - 2]
            values.indexOf(lastId) == 1 -> 0
            else -> values[values.indexOf(lastId) - 2]
        }
    }

    /**
     * Get next mark for provided data
     */
    fun getNext(ids: List<Int>): Int? =
       if (ids.size == itemsLimit) ids.last() else null
}
package com.donghanx.design.ui.shared

data class CardSharedElementKey(val id: String?, val origin: String) {

    /**
     * Generate a [String] to be used as the MemoryCacheKey in Coil's AsyncImage, as Coil requires
     * the key to be a String.
     */
    fun toMemoryCacheKey(): String? = id?.let { "$origin-$it" }

    fun isValid(): Boolean = id != null
}

package com.kite9.pipeline.adl.holder.meta

import com.kite9.pipeline.uri.K9URI

/**
 * Handles allowable meta elements, that can be stored in the ADL. These get
 * output either as part of the SVG, or headers of the HTTP request.
 *
 * @author robmoffat
 */
open class BasicMeta(metadata: MutableMap<String, Any>) : MetaReadWrite {

    protected var metadata: MutableMap<String, Any> = HashMap()

    override val metaData: Map<String, Any>
        get() = metadata

    override fun setUser(um: UserMeta) {
        metadata["user"] = um
    }

    override fun setAuthor(um: UserMeta) {
        metadata["author"] = um
    }

    override fun setTopicUri(topic: K9URI) {
        metadata["topic"] = topic
    }

    override val topicUri = metadata["topic"] as K9URI?

    override fun setCloseUri(close: K9URI) {
        metadata["close"] = close
    }

    override fun setTitle(title: String) {
        metadata["title"] = title
    }

    override val title: String = metadata.getOrDefault("title", "Unnamed") as String

    override fun setUri(u: K9URI) {
        if (u != null) {
            metadata["self"] = u
        }
    }

    override val uri = metadata["self"] as K9URI?

    override fun setCollaborators(collaborators: List<UserMeta>) {
        metadata["collaborators"] = collaborators
    }

    override fun setCommitCount(c: Int) {
        metadata["committing"] = c
    }

    override fun setNotification(message: String) {
        metadata["notification"] = message
    }

    override fun setError(message: String) {
        metadata["error"] = message
    }

    override fun setRole(r: Role) {
        metadata["role"] = r.toString().toLowerCase()
    }

    override fun setUploadsPath(u: String) {
        metadata["uploads"] = u
    }

    init {
        this.metadata = metadata
    }
}
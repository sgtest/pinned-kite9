package com.kite9.pipeline.command.xml.replace

import com.kite9.pipeline.adl.holder.pipeline.ADLDom
import com.kite9.pipeline.command.Command
import com.kite9.pipeline.command.CommandContext
import org.w3c.dom.Document

class ReplaceAttr : AbstractReplaceCommand<String?, String?>() {

    @JvmField
	public var name: String? = null

    override fun getFromContent(d: ADLDom, ctx: CommandContext): String? {
        return from
    }

    override fun getToContent(d: ADLDom, ctx: CommandContext): String? {
        return to
    }

    override fun getExistingContent(o: ADLDom, ctx: CommandContext): String {
        val on = findFragmentElement(o.document, fragmentId!!, ctx)
        return on?.getAttribute(name) ?: ""
    }

    override fun doReplace(existing: ADLDom, site: String?, value: String?, old: String?, ctx: CommandContext) : Command.Mismatch? {
        val doc: Document = existing.document
        val e = findFragmentElement(doc, fragmentId, ctx)

        if (e==null) {
            return Command.Mismatch { "Couldn't find element with id "+fragmentId }
        }

        if (value == null) {
            e.removeAttribute(name)
        } else {
            e.setAttribute(name, value)
        }
        ctx.log("Processed replace attribute of $fragmentId $name")
        return null
    }

    override fun checkProperties() {
        ensureNotNull("name", name)
        super.checkProperties()
    }

    override fun same(existing: String?, with: String?, ctx: CommandContext): Command.Mismatch? {
        return if (isEmpty(existing) && isEmpty(with)) {
            null
        } else super.same(existing, with, ctx)
    }
}
package org.kite9.diagram.visualization.planarization.rhd.grouping.basic

import org.kite9.diagram.common.hints.PositioningHints.planarizationDistance
import org.kite9.diagram.common.hints.PositioningHints.positionDistance
import org.kite9.diagram.model.position.Direction
import org.kite9.diagram.visualization.planarization.rhd.GroupPhase

/**
 * Holds details about a potential merge. Best merges are done first.
 * Because merge options are used in priority queues, they should not be altered while
 * the object is in the queue.
 */
class MergeOption(
    a: GroupPhase.Group?,
    b: GroupPhase.Group?,
    number: Int,
    p: Int,
    alignedGroup: GroupPhase.Group?,
    alignedSide: Direction?
) : Comparable<MergeOption> {
    var mk: MergeKey
    private var linkRank = 0
    private var linksIncluded = 0f
    private var linksAligned = 0f
    private var planarDistance: Float? = null
    private var renderedDistance: Float? = null
    var alignmentGroupSize = Int.MAX_VALUE
    var ordinalDistance = Int.MAX_VALUE
    var alignedGroup: GroupPhase.Group?
    val alignedDirection: Direction?
    var totalLinks = 0f
    private var size // size of the groups, in terms of contained items subsumed
            : Int
    val number // merge option number
            : Int

    /**
     * Higher numbers indicate worse priority.  100 or more is illegal.
     */
    var priority = 0
        private set

    /**
     * WARNING:  this should only be called if the merge option has been removed from the merge
     * state.
     */
    fun resetPriority(ms: BasicMergeState?, p: Int) {
        priority = p
    }

    val mergeType: MergeType
        get() = if (linksIncluded >= GroupPhase.LINK_WEIGHT) {
            MergeType.LINKED
        } else if (linksAligned > 0) {
            MergeType.ALIGNED
        } else {
            MergeType.NEIGHBOUR
        }

    override fun compareTo(arg0: MergeOption): Int {
        // order by priority first
        if (priority != arg0.priority) {
            return priority.compareTo(arg0.priority)
        }

        // order by merge type first
        if (mergeType !== arg0.mergeType) {
            return mergeType.ordinal.compareTo(arg0.mergeType.ordinal)
        }
        when (mergeType) {
            MergeType.LINKED -> {
                // respect highest ranked links first.
                if (arg0.linkRank != linkRank) {
                    return -linkRank.compareTo(arg0.linkRank)
                }

                // we need t combine lowest-level stuff first
                if (arg0.size != size) {
                    return size.compareTo(arg0.size)
                }

                // most value is from reducing total external links because they become internal
                if (arg0.linksIncluded != linksIncluded) {
                    return -linksIncluded.compareTo(arg0.linksIncluded)
                }

                // finally, try to avoid merging two "hub" neighbour attr together
                if (totalLinks != arg0.totalLinks) {
                    return totalLinks.compareTo(arg0.totalLinks)
                }
            }
            MergeType.ALIGNED -> {
                // join groups with smallest alignment group size (i.e. the group they both link to is smallest)
                if (alignmentGroupSize != arg0.alignmentGroupSize) {
                    return alignmentGroupSize.compareTo(arg0.alignmentGroupSize)
                }

                // aligning links also reduces complexity of the overall graph, but not as much
                if (linksAligned != arg0.linksAligned) {
                    return -linksAligned.compareTo(arg0.linksAligned)
                }

                // leave group with least non-aligned links
                if (totalLinks - linksAligned != arg0.totalLinks - linksAligned) {
                    return (totalLinks - linksAligned).compareTo(arg0.totalLinks - arg0.linksAligned)
                }

                // we need to combine lowest-level stuff first
                if (arg0.size != size) {
                    return size.compareTo(arg0.size)
                }
            }
            else -> {
                // merge together neighbours with least chance of being moved from the outside
                val thistl = Math.round(totalLinks)
                val arg0tl = Math.round(arg0.totalLinks)
                if (thistl != arg0tl) {
                    return thistl.compareTo(arg0tl)
                }


                // merge closest neighbours first, to respect the ordering in the 
                // xml
                if (ordinalDistance != arg0.ordinalDistance) {
                    return ordinalDistance.compareTo(arg0.ordinalDistance)
                }

                // try and merge smallest first, to achieve b-tree
                // and also to allow for more buddy merging
                if (arg0.size != size) {
                    return size.compareTo(arg0.size)
                }
            }
        }
        val dc = distanceCompare(this, arg0)
        return if (dc != 0) {
            dc
        } else number.compareTo(arg0.number)
    }

    private fun distanceCompare(a: MergeOption, b: MergeOption): Int {
        return if (a.planarDistance != null && b.planarDistance != null && a.planarDistance !== b.planarDistance) {
            a.planarDistance!!.compareTo(b.planarDistance!!)
        } else if (a.renderedDistance != null && b.renderedDistance != null && a.renderedDistance !== b.renderedDistance) {
            a.renderedDistance!!.compareTo(b.renderedDistance!!)
        } else {
            0
        }
    }

    override fun toString(): String {
        return ("[MO: " + number + " " + mk.a.groupNumber + " (" + mk.a.size + ")  " + mk.b.groupNumber + "(" + mk.b.size + "): t= " + mergeType + " i=" + linksIncluded + " a="
                + linksAligned + " t=" + totalLinks + "ags=" + alignmentGroupSize + " od=" + ordinalDistance + ", p=" + priority + " a=" + alignedGroup + " ad=" + alignedDirection + " lr=" + linkRank + "]")
    }

    /**
     * Call this function on a merge option to figure out it's priority.
     */
    fun calculateMergeOptionMetrics(ms: BasicMergeState) {
        totalLinks = 0f
        linksAligned = 0f
        linksIncluded = 0f
        linkRank = 0
        val a = mk.a
        val b = mk.b
        linkCount(a, b, this, ms)
        linkCount(b, a, this, ms)
        size = a.size + b.size
        ordinalDistance = Math.abs(a.groupOrdinal - b.groupOrdinal)
        planarDistance = planarizationDistance(a.hints, b.hints)
        renderedDistance = positionDistance(a.hints, b.hints)
    }

    private fun linkCount(group: GroupPhase.Group, cand: GroupPhase.Group, mo: MergeOption, ms: BasicMergeState) {
        val ldC = group.getLink(cand)
        if (ldC != null) {
            linksIncluded += ldC.numberOfLinks / 2f
            if (ldC.direction != null) {
                linkRank = ldC.linkRank
            }
        }
        totalLinks += group.linkManager.linkCount.toFloat()
        if (alignedGroup != null) {
            val ldA = group.getLink(alignedGroup!!)
            if (ldA != null) {
                linksAligned += ldA.numberOfLinks
            }
        }
    }

    init {
        mk = MergeKey(a!!, b!!)
        size = mk.a.size + mk.b.size
        this.number = number
        priority = p
        alignedDirection = alignedSide
        this.alignedGroup = alignedGroup
        if (alignedGroup != null) {
            alignmentGroupSize = alignedGroup.size
        }
    }
}
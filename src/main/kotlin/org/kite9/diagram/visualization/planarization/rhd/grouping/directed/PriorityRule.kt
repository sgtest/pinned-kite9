package org.kite9.diagram.visualization.planarization.rhd.grouping.directed

import org.kite9.diagram.model.position.Direction
import org.kite9.diagram.visualization.planarization.rhd.GroupPhase

/**
 * Checks to see if the particular combination of groups is allowed in the system in a given formation
 */
interface PriorityRule {

    fun getMergePriority(
        a: GroupPhase.Group,
        b: GroupPhase.Group,
        ms: DirectedMergeState,
        alignedGroup: GroupPhase.Group?, alignedSide: Direction?,
        mp: MergePlane,
        horizontalMergesFirst: Boolean
    ): Int

    companion object {
        const val UNDECIDED = -1
    }
}
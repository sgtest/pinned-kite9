package org.kite9.diagram.visualization.planarization.rhd.grouping.generators;

import org.kite9.diagram.position.Direction;
import org.kite9.diagram.visualization.planarization.rhd.GroupPhase;
import org.kite9.diagram.visualization.planarization.rhd.GroupPhase.Group;
import org.kite9.diagram.visualization.planarization.rhd.grouping.basic.BasicMergeState;
import org.kite9.diagram.visualization.planarization.rhd.grouping.directed.AbstractRuleBasedGroupingStrategy;
import org.kite9.diagram.visualization.planarization.rhd.grouping.directed.DirectedGroupAxis;
import org.kite9.diagram.visualization.planarization.rhd.grouping.directed.DirectedLinkManager;
import org.kite9.diagram.visualization.planarization.rhd.grouping.directed.MergePlane;

/**
 * Generates single-directed-merge options for any groups in axis.  These can span containers, 
 * and generally have the highest priority.
 * 
 * @author robmoffat
 *
 */
public class AxisSingleMergeGenerator extends AbstractMergeGenerator {

	public AxisSingleMergeGenerator(GroupPhase gp, BasicMergeState ms, GeneratorBasedGroupingStrategy grouper) {
		super(gp, ms, grouper);
	}

	@Override
	public void generate(Group poll) {
		log.send(log.go() ? null : "Generating "+getCode()+" options for "+poll);
		MergePlane state = DirectedGroupAxis.getState(poll);
		if ((state == MergePlane.X_FIRST_MERGE) || (state==MergePlane.UNKNOWN)) {
			// horizontal merges
			generateMergesInDirection(poll, ms, Direction.RIGHT, MergePlane.X_FIRST_MERGE);
			generateMergesInDirection(poll, ms, Direction.LEFT, MergePlane.X_FIRST_MERGE);
			
		}
		
		if ((state == MergePlane.Y_FIRST_MERGE) || (state==MergePlane.UNKNOWN)) {
			// vertical merges
			generateMergesInDirection(poll, ms, Direction.DOWN, MergePlane.Y_FIRST_MERGE);
			generateMergesInDirection(poll, ms, Direction.UP, MergePlane.Y_FIRST_MERGE);
		}
	}

	private void generateMergesInDirection(Group poll, BasicMergeState ms, Direction d, MergePlane mp) {
		DirectedLinkManager dlm = (DirectedLinkManager) poll.getLinkManager();
		Group right = dlm.getSingleDirectedMergeOption(d, mp, ms, false);
		
		if (right != null) {
			addMergeOption(poll, right, null, null);
		}
	}


	@Override
	protected int getMyBestPriority() {
		return AbstractRuleBasedGroupingStrategy.AXIS_SINGLE_NEIGHBOUR;
	}

	@Override
	protected String getCode() {
		return "AxisSingle";
	}

}

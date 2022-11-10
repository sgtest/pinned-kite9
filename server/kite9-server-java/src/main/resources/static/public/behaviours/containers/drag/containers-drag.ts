import { onlyUnique } from '../../../bundles/api.js'
import { getBeforeId } from '../../../bundles/ordering.js'
import { DropCallback, DropLocatorFunction } from '../../../classes/dragger/dragger.js';
import { Command } from '../../../classes/command/command.js';
import { Containment } from '../../../classes/containment/containment.js';

export function initContainerDropLocatorFunction(containment: Containment) : DropLocatorFunction {

	return function (dragTarget, dropTarget) {
		if (dropTarget == null) {
			return false;
		}
		
		if (dragTarget==dropTarget) {
			return false;
		}
		
		if (!containment.canContainAll(dragTarget, dropTarget)) {
			return false;
		}

		return true;
	}
}

export function initContainerDropCallback(command: Command, containment: Containment) : DropCallback {
	
	return function(dragState, evt, dropTargets) {
		const dragTargets = dragState.map(s => s.dragTarget);
		const connectedDropTargets = dropTargets.filter(t => {
			return containment.canContainAll(dragTargets, t);
		}).filter(onlyUnique);
		
		if (connectedDropTargets.length > 0) {
			const dropTarget = connectedDropTargets[0];
			const beforeId = getBeforeId(dropTarget, evt, dragTargets);
			Array.from(dragState).forEach(s => {
				if (s.dragParentId) {
					// we are moving this from somewhere else in the diagram
					command.push( {
						type: 'Move',
						to: dropTarget.getAttribute('id'),
						moveId: s.dragTarget.getAttribute('id'),
						toBefore: beforeId,
						from: s.dragParentId,
						fromBefore: s.dragBeforeId
					});
				} else if (s.url){
					// we are inserting this into the diagram
					command.push({
						type: 'InsertUrl',
						beforeId: beforeId,
						fragmentId: dropTarget.getAttribute('id'),
						uriStr: s.url,
						newId: s.dragTarget.getAttribute('id')
					});
				}
			});
			return true;
		} else {
			return false;
		}
	}
}

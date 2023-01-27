import { getMainSvg, svg } from '../../../bundles/screen.js'
import { parseInfo, getContainingDiagram, getNextSiblingId, isTerminator, onlyLastSelected, isLink, getParentElement } from '../../../bundles/api.js'
import { Command } from '../../../classes/command/command.js';
import { rotateAntiClockwise, rotateClockwise, Selector } from '../../../bundles/types.js';
import { ContextMenu, ContextMenuCallback } from '../../../classes/context-menu/context-menu.js';
import { getDirection, getStyleDirection, LinkDirection, reverseDirection } from '../linkable.js';

function linkDirectionSelector() {
	return Array.from(getMainSvg().querySelectorAll("[id][k9-ui~=direction].selected"));
}

function terminatorSelector() {
	return Array.from(getMainSvg().querySelectorAll("[id][k9-info*=terminator]"))
		.filter(e => isTerminator(e));
}


export function initLinkDirectionContextMenuCallback(
	command: Command,
	selector: Selector = linkDirectionSelector): ContextMenuCallback {

	type Turn = "cw" | "acw" | "180";

	function setDirections(es: Element[], direction: LinkDirection | Turn, contextMenu: ContextMenu) {

		contextMenu.destroy();

		es.forEach(e1 => {
			const toChange = isTerminator(e1) ? getParentElement(e1) : e1
			const id = toChange.getAttribute("id")
			const oldDirection = getStyleDirection(toChange);
			let relativeDirection: LinkDirection
			let reverse = false;

			if (e1 != toChange) {
				// deal with terminator
				const termInfo = parseInfo(e1);
				reverse = termInfo.end == 'from'
			}
				
			switch (direction) {
				case 'cw':
					relativeDirection = rotateClockwise(oldDirection);
					break;
				case 'acw':
					relativeDirection = rotateAntiClockwise(oldDirection);
					break;
				case '180':
					relativeDirection = reverseDirection(oldDirection);
					break;
				default:
					relativeDirection = reverse ? reverseDirection(direction) : direction;
			}

			command.push({
				fragmentId: id,
				type: 'ReplaceStyle',
				name: '--kite9-direction',
				to: relativeDirection,
				from: oldDirection
			})

			if (isLink(toChange)) {
				const diagramId = getContainingDiagram(toChange).getAttribute("id");

				// when setting a link direction, we move it to the 
				// end of the diagram to give it precedence over other 
				// links
				command.push({
					type: 'Move',
					from: diagramId,
					fromBefore: getNextSiblingId(toChange),
					moveId: id,
					to: diagramId
				});
			}
		});

		command.perform();
	}

	function drawDirectionImage(
		event: Event,
		cm: ContextMenu,
		text: string,
		icon: string,
		selected: LinkDirection = undefined,
		cb: () => void,
		set = "Actions"): HTMLImageElement {
		let title: string, src: string;

		if (text != undefined) {
			title = "Link Direction (" + text + ")";
			src = "/public/behaviours/links/direction/" + icon + ".svg";
		} else {
			title = "Link Direction (undirected)";
			src = "/public/behaviours/links/direction/undirected.svg";
		}

		const a = cm.addControl(event, src, title, cb, set) as HTMLDivElement;
		const img = a.children[0] as HTMLImageElement;

		if (selected == text) {
			img.setAttribute("class", "selected");
		}

		return img;
	}

	/**
	 * Provides a link option for the context menu
	 */
	return function(event, contextMenu) {

		const e = onlyLastSelected(selector());

		let link: Element, contradicting=false, direction: LinkDirection, reverse = false;
		if (e) {
			if (isTerminator(e)) {
				link = getParentElement(e);
				const debugLink = parseInfo(link);
				const debugTerm = parseInfo(e);

				direction = debugLink.direction;
				contradicting = debugLink.contradicting == "yes";
				reverse = debugTerm.end == 'from'

			} else {
				const debug = parseInfo(e);
				direction = debug.direction;

				if (isLink(e)) {
					contradicting = debug.contradicting == "yes";
					reverse = contradicting ? false : (debug.direction == 'left' || debug.direction == 'up');
				}
			}

			const d2 = reverse ? reverseDirection(direction) : direction;
			const img = drawDirectionImage(event, contextMenu, d2, d2, undefined, () => {
				contextMenu.clear();

				drawDirectionImage(event, contextMenu, null, null, d2, () => setDirections(selector(), null, contextMenu), "No Direction");

				["up", "down", "left", "right"].forEach((s: LinkDirection) => {
					drawDirectionImage(event, contextMenu, s, s, d2, () => setDirections(selector(), s, contextMenu), "Fixed Direction");
				});

				if (d2) {
					drawDirectionImage(event, contextMenu, "Turn Clockwise", 'turn-cw', d2, () => setDirections(selector(), 'cw', contextMenu), "Rotation");
					drawDirectionImage(event, contextMenu, "Turn Anti-Clockwise", 'turn-acw', d2, () => setDirections(selector(), 'acw', contextMenu), "Rotation");
					drawDirectionImage(event, contextMenu, "Turn 180", 'turn-180', d2, () => setDirections(selector(), '180', contextMenu), "Rotation");
				}
			});

			if (contradicting) {
				img.style.backgroundColor = "#ff5956";
			}
		}
	};
}

export function initTerminatorDirectionIndicator(selector = terminatorSelector) {


	const INDICATOR_SELECTOR = ":scope > g.k9-direction";

	const drawingFunctions = {
		"up": () => svg("polygon", { "points": "-10 12, 0 -8, 10 12" }),
		"down": () => svg("polygon", { "points": "10 -12, 0 8, -10 -12" }),
		"left": () => svg("polygon", { "points": "12 -10, -8 0, 12 10" }),
		"right": () => svg("polygon", { "points": "-12 -10, 8 0, -12 10" })
	}

	const noneFunction = () => svg("ellipse", { "cx": "0", "cy": 0, "rx": 8, "ry": 8 });

	function ensureDirectionIndicator(e: Element, direction: LinkDirection) {
		let indicator = e.querySelector(INDICATOR_SELECTOR);
		if ((indicator != null) && (indicator.getAttribute("direction") != direction)) {
			e.removeChild(indicator);
		} else if (indicator != null) {
			return;
		}

		indicator = svg('g', {
			'class': 'k9-direction',
			'k9-highlight': 'fill',
			'direction': direction,
		}, [direction ? drawingFunctions[direction]() : noneFunction()]);

		e.appendChild(indicator)
	}

	window.addEventListener('DOMContentLoaded', function() {
		selector().forEach(function(v) {
			const direction = getDirection(v);
			ensureDirectionIndicator(v, direction)
		})
	})
}

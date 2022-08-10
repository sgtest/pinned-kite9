import { hasLastSelected, getKite9Target, createUniqueId, changeId } from '/public/bundles/api.js'
import { getMainSvg, getSVGCoords, getElementPageBBox, currentTarget } from '/public/bundles/screen.js'
import { getElementUri } from '/public/classes/palette/palette.js';

function defaultDragableSelector(palettePanel) {
	return palettePanel.querySelectorAll("[allow-drag=true] [id][k9-palette]");	
}

/** Internal state of palette drag */
var time = Date.now();
var position = {};
var mouseDown = false;


/**
 * Allows users to drag off the palette and link to items in the main document.
 */
export function initNewLinkPaletteCallback(dragger, dragableSelector) {
	
	const DRAG_INTERVAL = 700;  // 700ms for drag to start
	const DRAG_DIST = 3;
	
	if (dragableSelector == undefined) {
		dragableSelector = defaultDragableSelector;
	}
		
	return function(palette, palettePanel) {
	
		function getPaletteElement(event) {
			var choices = Array.from(dragableSelector(palettePanel));
			var target = getKite9Target(currentTarget(event));
			while ((!choices.includes(target)) && (target != null)) {
				target = target.parentElement;
			}
			
			return target;
		}
				
		function startDrag(event) {
			time = Date.now();
			position = getSVGCoords(event);
			mouseDown = true;
		}
		
		function endDrag(event) {
			mouseDown = false;
		}
		
		function isDragging(event) {
			if (!mouseDown) {
				return false;
			}
			if (Date.now() - time >= DRAG_INTERVAL) {
				return true;
			}
			const newPosition = getSVGCoords(event);
			const absChange = Math.abs(position.x - newPosition.x) + Math.abs(position.y - newPosition.y);
			if (absChange >= DRAG_DIST ) {
				return true;
			}
			
			return false;
		}
		
		function moveDrag(event) {
			if (isDragging(event)) {
				const paletteElement = getPaletteElement(event);
				
				// create a new copy of the palette element
				const newId = createUniqueId();
				const droppingElement = paletteElement.cloneNode(true);
				changeId(droppingElement, droppingElement.getAttribute("id"), newId);
				
				droppingElement.setAttribute("autoconnect", "new");
							
				// place it in the same position on the main svg 
				getMainSvg().appendChild(droppingElement);
				const mousePos = getSVGCoords(event);
				const boundBox = getElementPageBBox(droppingElement);
				const nx = mousePos.x - (boundBox.width / 2);
				const ny = mousePos.y - (boundBox.height / 2);
				droppingElement.setAttribute("transform", "translateX("+nx+"px) translateY("+ny+"px)")
				droppingElement.classList.remove("selected");
				
				palette.destroy();	
				const map = new Map();
				const uri = getElementUri(paletteElement, palettePanel);
				map.set(droppingElement, uri);
				dragger.beginAdd(map, event);
				dragger.grab(event);
	
				event.stopPropagation();	
			}		
		}
	
		dragableSelector(palettePanel).forEach(function(v) {
	    	v.removeEventListener("mousedown", startDrag);
	    	v.addEventListener("mousedown", startDrag);
	    	
	    	v.removeEventListener("touchstart", startDrag);
	    	v.addEventListener("touchstart", startDrag);
	    	
	    	v.removeEventListener("mousemove", moveDrag);
	    	v.addEventListener("mousemove", moveDrag);
	    	
	    	v.removeEventListener("touchmove", moveDrag);
	    	v.addEventListener("touchmove", moveDrag);
		})
		
		document.removeEventListener("mouseup", endDrag);
		document.addEventListener("mouseup", endDrag);
		
		document.removeEventListener("touchend", endDrag);
		document.addEventListener("touchend", endDrag);
	}
}





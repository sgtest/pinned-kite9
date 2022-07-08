
import { once } from '/github/kite9-org/kite9/client/bundles/ensure.js?v=v0.3'

import { command, metadata, dragger, contextMenu, layout, containment, palette } from '/github/kite9-org/kite9/templates/editor/editor.js?v=v0.3'

//Containers
import { initInsertPaletteCallback, initInsertContextMenuCallback, initInsertDragLocator } from '/github/kite9-org/kite9/behaviours/containers/insert/containers-insert.js?v=v0.3'
import { initContainPaletteCallback, initContainContextMenuCallback } from '/github/kite9-org/kite9/behaviours/containers/contain/containers-contain.js?v=v0.3'
import { initContainerLayoutMoveCallback, initLayoutContextMenuCallback, initContainerLayoutPropertyFormCallback, initContainerLayoutPropertySetCallback } from '/github/kite9-org/kite9/behaviours/containers/layout/containers-layout.js?v=v0.3'
import { initChildContextMenuCallback } from '/github/kite9-org/kite9/behaviours/containers/child/containers-child.js?v=v0.3'
import { initContainerDropLocatorFunction, initContainerDropCallback } from '/github/kite9-org/kite9/behaviours/containers/drag/containers-drag.js' 
import { initAttributeContainmentCallback } from '/github/kite9-org/kite9/behaviours/containers/rules/containers-rules.js?v=v0.3'


function initContainers() {
	
	containment.add(initAttributeContainmentCallback());

	if (metadata.isEditor()) {

		layout.formCallback(initContainerLayoutPropertyFormCallback()); 
		layout.setCallback(initContainerLayoutPropertySetCallback(command)); 

		dragger.dragLocator(initInsertDragLocator());
		dragger.moveWith(initContainerLayoutMoveCallback());
		dragger.dropWith(initContainerDropCallback(command, containment));
		dragger.dropLocatorFn(initContainerDropLocatorFunction(containment));

		palette.add(initContainPaletteCallback(command));
		palette.add(initInsertPaletteCallback(command, containment));

		
		contextMenu.add(initContainContextMenuCallback(palette, containment)); 
		contextMenu.add(initInsertContextMenuCallback(palette, containment));
		contextMenu.add(initLayoutContextMenuCallback(layout));
		contextMenu.add(initChildContextMenuCallback(command));

	}
	
}


once(() => initContainers());
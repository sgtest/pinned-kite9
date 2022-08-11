import { hasLastSelected } from '/public/bundles/api.js'
import { textarea, form, ok, cancel, inlineButtons, formValues } from '/public/bundles/form.js'
import { getMainSvg } from '/public/bundles/screen.js';

export function initEditContextMenuCallback(command, selector, textCollector) {
	
	function createEditStep(e, newText, oldText) {
		return {
			"type": 'ReplaceText',
			"fragmentId": e.getAttribute('id'),
			"to": newText,
			"from": oldText
			
		}
	}
	
	if (selector == undefined) {
		selector = function() {
			return getMainSvg().querySelectorAll("[id][k9-ui~='edit'].selected");
		}
	}
	
	if (textCollector == undefined) {
		textCollector = function(e) {
			var text = e.querySelector("[k9-ui~='text']");
			text = (text != null) ? text : e;
			var lines = text.querySelectorAll('text');
			return Array.from(lines).map(l => l.textContent.trim()).reduce((a, b) => a +"\n" + b); 
		}
	}

	/**
	 * Provides a text-edit option for the context menu
	 */
	return function(event, cm) {
		
		const selectedElements = hasLastSelected(selector());
		
		if (selectedElements.length > 0) {
			
			cm.addControl(event, "/public/behaviours/editable/text/edit.svg", 'Edit Text', () => {
				const defaultText = textCollector(hasLastSelected(selectedElements, true));
				cm.clear();
				var htmlElement = cm.get(event);
				htmlElement.appendChild(form([
					textarea('Enter Text', defaultText, { rows: 10 }),
					inlineButtons([
						ok('ok', {}, (e) => {
							const values = formValues('editText');
							const steps = Array.from(selectedElements).map(e => createEditStep(e, values.enterText, defaultText));
							command.pushAllAndPerform(steps);
							cm.destroy();
						}),
						cancel('cancel', [], () => cm.destroy())
					])
				], 'editText'));
				
			});
		}
	}
}

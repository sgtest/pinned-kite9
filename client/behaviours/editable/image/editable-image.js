import { hasLastSelected} from '/github/kite9-org/kite9/client/bundles/api.js?v=v0.8'
import { getMainSvg } from '/github/kite9-org/kite9/client/bundles/screen.js?v=v0.8'
import { icon, form, text, p, inlineButtons, ok, cancel, formValues, img, fieldset, div } from '/github/kite9-org/kite9/client/bundles/form.js?v=v0.8'

const LOADING = '/public/behaviours/editable/image/loading.svg';
const SUCCESS = '/public/behaviours/editable/image/success.svg';
const FAIL = '/public/behaviours/editable/image/fail.svg';

export function initEditableImageContextMenuCallback(command, metadata, selector) {
	
	if (selector == undefined) {
		selector = function () {
			return getMainSvg().querySelectorAll("[id][k9-ui~=image].selected");
		}
	}
	
	function setAttribute(element, key, value, newValue) {
		if ((value) && (newValue) && (value != newValue)) {
			command.push({
				type: 'ReplaceAttr',
				fragmentId: element.getAttribute("id"),
				name: key,
				from: value,
				to: newValue
			});
		}
	}
	
	function uploadImage(blob, path, spinner) {
		const fd = new FormData();
		fd.append('file', blob);
		
		fetch(path, {
			method: 'POST',
			body: fd
		}).catch(e => {
			spinner.setAttribute("src", FAIL);
		}).then(r => {
			spinner.setAttribute("src", SUCCESS);
		})
	}
	
  function canUpload() {
    return metadata.get('uploads'); 
  }
  
	function loadImages(into, onClick) {
		const spinner = img('pasteStatus', LOADING, { width: '80px'});
		into.appendChild(spinner);
		const path = metadata.get('uploads') + '?format=json';
		fetch(path).catch(e => {
			spinner.setAttribute("src", FAIL);
		}).then(r => {
			into.removeChild(spinner);
			return r.json();
		}).then(json => {
			json.documents.forEach(d => {
				const img = icon('x', d.title, d.icon, onClick);
				into.appendChild(img);
			});
			console.log(json);
		});
	}
	
	function createP(id) {
		return p('', {
			'contentEditable' : 'true', 
			'id': id,
			'style' : 'height: 100px; padding: 12px; margin: 5px; user-select: auto; -webkit-user-select: auto; display: block; ',
			'class' : 'hint--bottom hint--bounce',
			'aria-label' : 'Paste Image Here To Upload'
		});
	}
	
	function createDiv(id) {
		return div({
			'style': 'overflow: scroll; display: block; height: 140px; '
		}, []);
	}
		
	/**
	 * Provides a layout option for the context menu
	 */
	return function (event, contextMenu) {

		const elements = hasLastSelected(selector());

		if (elements.length> 0) {
			const last = hasLastSelected(elements, true);
			contextMenu.addControl(event, "/public/behaviours/editable/image/edit.svg", 'Edit Image', () => {
				const href =  last.getAttribute("href");
								
				contextMenu.clear();
				var htmlElement = contextMenu.get(event);
				
				var hrefField = text('Image URI', href);
				var pasteField = createP('pasteField');
        var existingField = null;
        
        if (canUpload()) {				
          var pasteImage = null;
          var pasteData = null;
          var pastePath = null;
          var pasteStatus = null;
  		
      		pasteField.addEventListener('paste', function(event) {
  				  // use event.originalEvent.clipboard for newer chrome versions
  				  var items = (event.clipboardData  || event.originalEvent.clipboardData).items;
  				  console.log(JSON.stringify(items)); // will give you the mime types
  				  // find pasted image among pasted items
  				  var blob = null;
  				  for (var i = 0; i < items.length; i++) {
  				    if (items[i].type.indexOf("image") === 0) {
  				      blob = items[i].getAsFile();
  				    }
  				  }
  				  
  				  // load image if there is a pasted image
  				  if (blob !== null) {
  				    var reader = new FileReader();
  				    reader.onload = function(event) {
  				      console.log(event.target.result); // data url!
  				      
  				      if (pasteImage != null) {
  				    	  pasteField.removeChild(pasteImage);
  				    	  pasteField.removeChild(pasteStatus);
  				      }
  				      pasteData = event.target.result;
  				      pasteImage = img('pastedImage', pasteData, { width: '80px'});
  				      pasteField.appendChild(pasteImage);
  				      pasteField.appendChild(pasteStatus);
  				    };
  
  				    pasteStatus = img('pasteStatus', LOADING, { width: '80px'});
  				    pastePath = metadata.get('uploads')+"/"+blob.name;
  				    reader.readAsDataURL(blob);
  				    hrefField.children[1].value = pastePath;
  				    
  				    uploadImage(blob, pastePath, pasteStatus);
  				  }
  				  
  				  event.stopPropagation();
  				  event.preventDefault();
  				});
  				
  				existingField = createDiv('existingField');
  				loadImages(existingField, function(event) {
  					const selected = event.currentTarget.children[0];
  					if (pasteImage != null) {
  				    	  pasteField.removeChild(pasteImage);
  				    	  pasteField.removeChild(pasteStatus);
  				    }
  					pastePath = selected.getAttribute("src");
  					pasteImage = img('pastedImage', pastePath, { width: '80px'});
  				    pasteField.appendChild(pasteImage);
  				    hrefField.children[1].value = pastePath;
  				});
        
        } else {
          pasteField = p("You can't upload here");
          existingField = p('No existing images');
        }

				
				htmlElement.appendChild(form([
					hrefField,
					fieldset('New Image', [ pasteField ],  { style: 'padding: 2px;' }),
					fieldset('Existing Images', [ existingField ], { style: 'padding: 2px; '}),
					inlineButtons([
						ok('ok', {}, (ev) => {
							const newValues = formValues('imageProperties');
							Array.from(elements).forEach(e => setAttribute(e, 'href', href, newValues.imageURI));
							command.perform();
							contextMenu.destroy();
						}),
						cancel('cancel', [], () => contextMenu.destroy())
					])
				], 'imageProperties'));
			});
		}
	};	
}
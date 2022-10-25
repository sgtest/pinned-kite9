export const WILDCARD = "*";

type WcElement = string | Element

type callback = (elements: WcElement[], parents?: WcElement[], children?: WcElement[]) => Element[]

/** 
 * Handles drag and drop rules, as well as surround/contain and insert.  Replace is handled elsewhere.
 */
export class Containment {

	callbacks : callback[] = []
	
	add(cb: callback) {
		this.callbacks.push(cb);
	}
	
	
	/**
	 * This is the main function.  elements is the set of elements that will be reduced and returned.  
	 * Generally, these are SVG (kite9) elements, but you can also supply "*" ( a string-star), which acts as a wildcard.
	 * Parents and children are optional, and will "limit" the elements returned in the reduction.
	 */
	allowed(elements : WcElement[], parents? :Element[], children?: Element[]) : Element[] {
		// run each callback.  return the union of allowed elements from all callbacks.
		
		return this.callbacks
			.map(cb => cb(elements, parents, children))
			.reduce((a, b) => [...new Set([...a, ...b])]);
	}

	/**
	 * Helper function
	 */
	canContain(element: Element[], parent: Element) {
		return this.allowed(
			Array.isArray(element) ? element : [element], 
			Array.isArray(parent) ? parent : [parent]).length == 1;
	}
	
	/**
	 * Helper function
	 */
	canContainAll(elements: Element[], parent: Element) : boolean {
		return this.allowed(elements, 
			Array.isArray(parent) ? parent : [parent]
			).length == elements.length;
	}
	
	canSurroundAll(elements: Element[], parents: Element[], children: Element[]) : boolean {
		return this.allowed(elements, parents, children).length == elements.length;
	}
	
	/**
	 * Can insert into this container
	 */
	canInsert(containers: Element[], children : Element[]) : boolean {
		return this.allowed([ WILDCARD ], 
			Array.isArray(containers) ? containers : [ containers], 
			Array.isArray(children) ? children : [ children ]).length == 1;
	}
}
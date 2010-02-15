package domain;


public class DoublyLinkedList<T> {
	private Link<T> first;
	private Link<T> last;
	private Link<T> current = null;
	
	public DoublyLinkedList(){
		first = null;
		last = null;
	}
	
	public void addElement(T val){
		Link<T> newLink = new Link<T>(val);
		if(first == null){
			first = newLink;
			last = newLink;
		}else{
			last.setNext(newLink);
			newLink.setPrev(last);
			last = newLink;
		}
	}
	
	public void toFirst(){
		current = first;
	}
	
	public void toLast(){
		current = last;
	}
	
	public boolean next(){
		current = current.getNext();
		return current != null;
	}
	
	public boolean prev(){
		current = current.getPrev();
		return current != null;
	}
	
	
	public T getCurrentValue(){
		return current.getValue();
	}
	
	public boolean currentIsNull(){
		return current == null;
	}
	
	// removes the current element and moves current to the
	// previous link, or the first if there is no previous.
	public void removeCurrent(){
		if(current.getNext() != null)
			current.getNext().setPrev(current.getPrev());
		else
			last = current.getPrev();
		if(current.getPrev() != null){
			current.getPrev().setNext(current.getNext());
			current = current.getPrev();
		} else {
			first = current.getNext();
			current = first;
		}
	}
	
	// inserts a new value after current.
	public void insertAtCurrent(T value){
		Link<T> addMe = new Link<T>(value);
		if(current == null){
			addElement(value);
		}else{
			addMe.setNext(current.getNext());
			if(current.getNext()!=null)
				current.getNext().setPrev(addMe);
			current.setNext(addMe);
			addMe.setPrev(current);
		}
	}
	
	// inserts a new value before current.
	public void insertBeforeCurrent(T value){
		Link<T> addMe = new Link<T>(value);
		// If current is null, add to the beginning
		if(current == null){
			Link<T> second = first;
			first = addMe;
			first.setNext(second);
			if(second != null){
				second.setPrev(first);
			}		
		}else{
			// Current is not null
			addMe.setNext(current);
			addMe.setPrev(current.getPrev());			
			if(current.getPrev()!=null){
				// current is not first
				current.getPrev().setNext(addMe);				
			}else{
				// current was first
				first = addMe;				
			}
			current.setPrev(addMe);
		}
	}
	
	public void updateCurrent(T value){
		current.setValue(value);
	}
	
	public String toString(){
		Link<T> oldCurrent = current;
		String output = "";
		toFirst();
		while(!this.currentIsNull()){
			output += this.getCurrentValue().toString()+"\n";
			this.next();
		}
		current = oldCurrent;
		return output;
	}
}

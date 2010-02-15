package domain;

public class Link<T> {
	private Link<T> prev;
	private Link<T> next;
	private T value;
	
	public Link(T contents){
		value = contents;
	}
	
	public void setNext(Link<T> n){
		next = n;
	}
	
	public void setPrev(Link<T> n){
		prev = n;
	}
	public Link<T> getNext(){
		return next;
	}
	
	public Link<T> getPrev(){
		return prev;
	}
	public void setValue(T val){
		value = val;
	}
	public T getValue(){
		return value;
	}
	public String toString(){
		return (prev==null?"null":prev.getValue().toString()) + "->("+value.toString()+")->" +(next == null?"null":next.getValue().toString());
	}
}

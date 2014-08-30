package org.rnd.util;

/**
 * This class deliberately doesn't implement {@link java.util.List}, because the
 * whole purpose is to generate compiler errors when calling remove, contains,
 * etc with a parameter of the wrong type.
 * 
 * Don't leave instances of this hanging around in the code -- just change the
 * type of the object you're worried about, fix the errors, and change it back.
 */
public class SaneList<E> implements Iterable<E>
{
	private java.util.List<E> wrap;
	
	public SaneList(java.util.List<E> wrap)
	{
		this.wrap = wrap;
	}
	
	public SaneList(SaneList<E> copy)
	{
		this.wrap = new java.util.LinkedList<E>();
		for(E e: copy)
			this.wrap.add(e);
	}

	public boolean add(E arg0)
	{
		return this.wrap.add(arg0);
	}

	public void add(int arg0, E arg1)
	{
		this.wrap.add(arg0, arg1);
	}

	public boolean addAll(java.util.Collection<? extends E> arg0)
	{
		return this.wrap.addAll(arg0);
	}

	public boolean addAll(SaneList<? extends E> c)
	{
		boolean ret = true;
		for(E e: c)
			ret = this.add(e) && ret;
		return ret;
	}

	public boolean addAll(int index, java.util.Collection<? extends E> c)
	{
		return this.wrap.addAll(index, c);
	}

	public void clear()
	{
		this.wrap.clear();
	}

	public boolean contains(E o)
	{
		return this.wrap.contains(o);
	}

	public boolean containsAll(java.util.Collection<? extends E> c)
	{
		return this.wrap.containsAll(c);
	}

	public E get(int index)
	{
		return this.wrap.get(index);
	}

	public int indexOf(E o)
	{
		return this.wrap.indexOf(o);
	}

	public boolean isEmpty()
	{
		return this.wrap.isEmpty();
	}

	public java.util.Iterator<E> iterator()
	{
		return this.wrap.iterator();
	}

	public int lastIndexOf(E o)
	{
		return this.wrap.lastIndexOf(o);
	}

	public java.util.ListIterator<E> listIterator()
	{
		return this.wrap.listIterator();
	}

	public java.util.ListIterator<E> listIterator(int index)
	{
		return this.wrap.listIterator(index);
	}

	public boolean remove(E o)
	{
		return this.wrap.remove(o);
	}

	public E remove(int index)
	{
		return this.wrap.remove(index);
	}

	public boolean removeAll(java.util.Collection<?> c)
	{
		return this.wrap.removeAll(c);
	}

	public boolean retainAll(java.util.Collection<?> c)
	{
		return this.wrap.retainAll(c);
	}

	public E set(int index, E element)
	{
		return this.wrap.set(index, element);
	}

	public int size()
	{
		return this.wrap.size();
	}

	public java.util.List<E> subList(int fromIndex, int toIndex)
	{
		return this.wrap.subList(fromIndex, toIndex);
	}

	public Object[] toArray()
	{
		return this.wrap.toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		return this.wrap.toArray(a);
	}

}

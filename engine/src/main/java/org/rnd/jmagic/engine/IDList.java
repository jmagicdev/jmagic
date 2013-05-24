package org.rnd.jmagic.engine;

/**
 * IDList is a special kind of List that allows one to hold Identified instances
 * that belong to a GameState where those Identified might not exist yet.
 * Essentially, it holds a list of IDs and transparently maps them to Identifed
 * whenever they're needed.
 * <p>
 * It isn't important that collections initialized to IDList stay an IDList. As
 * long as every Identified in the IDList is in this GameState, any other
 * collection copying this list will work correctly.
 */
public class IDList<T extends Identified> extends java.util.AbstractCollection<T> implements java.util.List<T>
{
	/**
	 * This is where the magic happens. For the most part, this iterator acts
	 * just like an iterator on the backing store (list of IDs) but it returns
	 * an Identified whenever you're expecting one (next() and previous()) and
	 * takes an Identified whenever you're expecting it to (add() and set())
	 */
	private class IDListIterator implements java.util.ListIterator<T>
	{
		private java.util.ListIterator<Integer> IDiterator;

		IDListIterator(int index)
		{
			this.IDiterator = IDList.this.IDs.listIterator(index);
		}

		@Override
		public void add(T e)
		{
			this.IDiterator.add(e.ID);
		}

		@Override
		public boolean hasNext()
		{
			return this.IDiterator.hasNext();
		}

		@Override
		public boolean hasPrevious()
		{
			return this.IDiterator.hasPrevious();
		}

		@Override
		public T next()
		{
			return IDList.this.state.<T>get(this.IDiterator.next());
		}

		@Override
		public int nextIndex()
		{
			return this.IDiterator.nextIndex();
		}

		@Override
		public T previous()
		{
			return IDList.this.state.<T>get(this.IDiterator.previous());
		}

		@Override
		public int previousIndex()
		{
			return this.IDiterator.previousIndex();
		}

		@Override
		public void remove()
		{
			this.IDiterator.remove();
		}

		@Override
		public void set(T e)
		{
			this.IDiterator.set(e.ID);
		}
	}

	/**
	 * The state that the objects will be retrieved from
	 */
	private final GameState state;

	private java.util.List<Integer> IDs;

	/**
	 * Create an empty IDList.
	 * 
	 * @param state The state that the objects will be retrieved from
	 */
	IDList(GameState state)
	{
		this.state = state;
		this.IDs = new java.util.LinkedList<Integer>();
	}

	/**
	 * This is a private copy constructor so that it isn't necessary to iterate
	 * over the elements, just over the backing store
	 * 
	 * @param state The state that the objects will be retrieved from
	 * @param ids The collection of ids to put into the backing store
	 * @param dummy Unused parameter to avoid type erasure collision
	 */
	IDList(GameState state, java.util.Collection<Integer> ids, boolean dummy)
	{
		this(state);
		this.IDs.addAll(ids);
	}

	/**
	 * Create an IDList copy of another collection.
	 * 
	 * @param state The state that the objects will be retrieved from
	 * @param copy The collection to copy.
	 */
	IDList(GameState state, java.util.Collection<T> copy)
	{
		this(state);
		if(copy instanceof IDList<?>)
			this.IDs.addAll(((IDList<T>)copy).IDs);
		else
			for(T t: copy)
				this.IDs.add(t.ID);
	}

	@Override
	public void add(int index, T t)
	{
		this.IDs.add(index, t.ID);
	}

	@Override
	public boolean add(T t)
	{
		return this.IDs.add(t.ID);
	}

	@Override
	public boolean addAll(int index, java.util.Collection<? extends T> c)
	{
		java.util.Collection<Integer> newIDs = new java.util.LinkedList<Integer>();
		for(T t: c)
			newIDs.add(t.ID);

		return this.IDs.addAll(index, newIDs);
	}

	@Override
	public boolean addAll(java.util.Collection<? extends T> c)
	{
		boolean changed = false;
		for(T t: c)
			if(this.add(t))
				changed = true;
		return changed;
	}

	// AbstractSequentialList.clear() is defined as iterating over the
	// collection and removing each element individually. This is a much
	// simpler solution and allows for clearing a list when those
	// Identifieds aren't available.
	@Override
	public void clear()
	{
		this.IDs.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		if(!(o instanceof Identified))
			return false;

		return this.IDs.contains(((Identified)o).ID);
	}

	@Override
	public boolean containsAll(java.util.Collection<?> c)
	{
		for(Object o: c)
			if(!this.contains(o))
				return false;

		return true;
	}

	@Override
	public T get(int index)
	{
		return this.state.<T>get(this.IDs.get(index));
	}

	@Override
	public int indexOf(Object o)
	{
		if(!(o instanceof Identified))
			return -1;

		return this.IDs.indexOf(((Identified)o).ID);
	}

	@Override
	public boolean isEmpty()
	{
		return this.IDs.isEmpty();
	}

	@Override
	public java.util.Iterator<T> iterator()
	{
		return this.listIterator();
	}

	@Override
	public int lastIndexOf(Object o)
	{
		if(!(o instanceof Identified))
			return -1;

		return this.IDs.lastIndexOf(((Identified)o).ID);
	}

	@Override
	public java.util.ListIterator<T> listIterator()
	{
		return this.listIterator(0);
	}

	@Override
	public java.util.ListIterator<T> listIterator(int index)
	{
		return new IDListIterator(index);
	}

	@Override
	public T remove(int index)
	{
		return this.state.<T>get(this.IDs.remove(index));
	}

	@Override
	public boolean remove(Object o)
	{
		if(!(o instanceof Identified))
			return false;

		// The cast to Integer is required to make sure we're not calling
		// remove(index)
		return this.IDs.remove((Integer)((Identified)o).ID);
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c)
	{
		boolean changed = false;
		for(Object o: c)
			if(this.remove(o))
				changed = true;

		return changed;
	}

	@Override
	public boolean retainAll(java.util.Collection<?> c)
	{
		java.util.Collection<Integer> newIDs = new java.util.LinkedList<Integer>();
		for(Object o: c)
			if(o instanceof Identified)
				newIDs.add(((Identified)o).ID);

		return this.IDs.retainAll(c);
	}

	@Override
	public T set(int index, T element)
	{
		return this.state.<T>get(this.IDs.set(index, element.ID));
	}

	@Override
	public int size()
	{
		return this.IDs.size();
	}

	@Override
	public java.util.List<T> subList(int fromIndex, int toIndex)
	{
		return new IDList<T>(this.state, this.IDs.subList(fromIndex, toIndex), false);
	}

	@Override
	public Object[] toArray()
	{
		Object[] a = new Object[this.IDs.size()];
		int index = 0;
		java.util.Iterator<T> iter = this.iterator();
		while(iter.hasNext())
			a[index++] = iter.next();
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> S[] toArray(S[] a)
	{
		if(a.length < this.size())
			a = (S[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), this.size());
		int i = 0;
		Object[] result = a;
		java.util.Iterator<T> iter = this.iterator();
		while(iter.hasNext())
		{
			result[i++] = iter.next();
		}

		if(a.length > this.size())
			a[this.size()] = null;

		return a;
	}
}

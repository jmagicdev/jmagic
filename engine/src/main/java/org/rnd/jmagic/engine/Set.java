package org.rnd.jmagic.engine;

import java.util.Collection;

/**
 * Represents a Magic-specific version of a mathematical set.
 *
 * A mathematical set is a collection of things where duplicates are not
 * allowed. Java provides an interface at java.util.Set which represents a
 * contract for this functionality.
 *
 * There are two main differences between this class and the mathematical set.
 * The first is that, while most things (GameObjects, Zones, etc.) may not
 * appear more than once, integers may. So a Set could (for example) contain [2,
 * 2, Battlefield], but not [2, Battlefield, Battlefield].
 *
 * The other main difference is that only one NumberRange may appear in a Set.
 */
public class Set implements java.util.Set<Object>, java.io.Serializable
{
	/**
	 * A special java.util.Set is required to hold multiple instances of Integer
	 * without the uniqueness condition kicking in.
	 */
	private static class IntSet implements java.util.Set<Integer>
	{
		private java.util.List<Integer> wrappers;

		public IntSet()
		{
			this.wrappers = new java.util.LinkedList<Integer>();
		}

		@Override
		public boolean add(Integer e)
		{
			return this.wrappers.add(e);
		}

		@Override
		public boolean addAll(java.util.Collection<? extends Integer> c)
		{
			return this.wrappers.addAll(c);
		}

		@Override
		public void clear()
		{
			this.wrappers.clear();
		}

		@Override
		public boolean contains(Object o)
		{
			return this.wrappers.contains(o);
		}

		@Override
		public boolean containsAll(java.util.Collection<?> c)
		{
			return this.wrappers.containsAll(c);
		}

		@Override
		public boolean isEmpty()
		{
			return this.wrappers.isEmpty();
		}

		@Override
		public java.util.Iterator<Integer> iterator()
		{
			return this.wrappers.iterator();
		}

		@Override
		public boolean remove(Object o)
		{
			return this.wrappers.remove(o);
		}

		@Override
		public boolean removeAll(java.util.Collection<?> c)
		{
			return this.wrappers.removeAll(c);
		}

		@Override
		public boolean retainAll(java.util.Collection<?> c)
		{
			return this.wrappers.retainAll(c);
		}

		@Override
		public int size()
		{
			return this.wrappers.size();
		}

		@Override
		public Object[] toArray()
		{
			return this.wrappers.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a)
		{
			return this.wrappers.toArray(a);
		}
	}

	private static class IntWrapper implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public final int value;

		public IntWrapper(int value)
		{
			this.value = value;
		}

		@Override
		public String toString()
		{
			return Integer.toString(this.value);
		}
	}

	/**
	 * Unmodifiable is a special Set that can't be modified, but otherwise acts
	 * like a Set.
	 */
	public static class Unmodifiable extends Set
	{
		private static final long serialVersionUID = 1L;

		public Unmodifiable()
		{
			this.store = java.util.Collections.emptySet();
		}

		public Unmodifiable(int i)
		{
			this.store = java.util.Collections.<Object>singleton(new IntWrapper(i));
		}

		public Unmodifiable(Object o)
		{
			this.store = java.util.Collections.singleton(o);
		}

		public Unmodifiable(Set s)
		{
			this.store = java.util.Collections.unmodifiableSet(s.store);
		}
	}

	/**
	 * Constructs a Set containing all the elements in a given collection.
	 *
	 * @param c The collection.
	 */
	public static Set fromCollection(Collection<?> c)
	{
		if(null == c)
			throw new UnsupportedOperationException("Attempt to initialize a Set with a null collection");

		Set ret = new Set();
		boolean hasNumberRange = false;
		for(Object e: c)
			if(null != e)
				if(e instanceof Integer)
					ret.store.add(new IntWrapper((Integer)e));
				else
				{
					if(e instanceof org.rnd.util.NumberRange)
					{
						if(hasNumberRange)
							throw new UnsupportedOperationException("Attempt to initialize a Set with a collection containing two NumberRange instances");
						hasNumberRange = true;
					}
					ret.store.add(e);
				}
		return ret;
	}

	protected java.util.Set<Object> store;

	private static final long serialVersionUID = 1L;

	/** Constructs an empty Set. */
	public Set()
	{
		this.store = new java.util.HashSet<Object>();
	}

	/**
	 * Constructs a Set containing all the elements in a given array.
	 *
	 * @param c The array.
	 */
	public Set(Object... c)
	{
		this();
		if(null == c)
			throw new UnsupportedOperationException("Attempt to initialize a Set with a null collection");

		boolean hasNumberRange = false;
		for(Object e: c)
		{
			if(null == e)
				throw new UnsupportedOperationException("Attempt to initialize a Set with a null element");
			if(e instanceof Integer)
				this.store.add(new IntWrapper((Integer)e));
			else
			{
				if(e instanceof org.rnd.util.NumberRange)
				{
					if(hasNumberRange)
						throw new UnsupportedOperationException("Attempt to initialize a Set with a collection containing two NumberRange instances");
					hasNumberRange = true;
				}
				this.store.add(e);
			}
		}
	}

	public Set(int... c)
	{
		this();

		if(null == c)
			throw new UnsupportedOperationException("Attempt to add to a Set with a null collection");

		for(int i: c)
			this.store.add(new IntWrapper((Integer)i));
	}

	public Set(int c)
	{
		this();
		this.store.add(new IntWrapper((Integer)c));
	}

	/**
	 * Adds an element to this Set.
	 *
	 * @param e The element
	 */
	@Override
	public boolean add(Object e)
	{
		if(null == e)
			throw new UnsupportedOperationException("Attempt to add null to a Set");

		if(e instanceof Integer)
			return this.store.add(new IntWrapper((Integer)e));

		if(e instanceof org.rnd.util.NumberRange)
		{
			boolean hasNumberRange = false;
			for(Object o: this)
			{
				if(o instanceof org.rnd.util.NumberRange)
				{
					hasNumberRange = true;
					break;
				}
			}
			if(hasNumberRange)
				throw new UnsupportedOperationException("Attempt to add a second NumberRange to a Set");
		}
		return this.store.add(e);
	}

	/**
	 * Adds all the elements of a collection to this set.
	 *
	 * @param c The collection.
	 * @return True if this Set changed as a result of this call; false
	 * otherwise.
	 */
	@Override
	public boolean addAll(java.util.Collection<?> c)
	{
		if(null == c)
			throw new UnsupportedOperationException("Attempt to add to a Set with a null collection");

		boolean hasNumberRange = false;
		for(Object o: this)
		{
			if(o instanceof org.rnd.util.NumberRange)
			{
				hasNumberRange = true;
				break;
			}
		}

		boolean changed = false;
		for(Object e: c)
		{
			if(null == e)
				throw new UnsupportedOperationException("Attempt to add null to a Set");

			if(e instanceof org.rnd.util.NumberRange)
			{
				if(hasNumberRange)
					throw new UnsupportedOperationException("Attempt to add a second NumberRange to a Set");
				hasNumberRange = true;
			}
			boolean isInt = e instanceof Integer;
			if((isInt && this.store.add(new IntWrapper((Integer)e))) || (!isInt && this.store.add(e)))
				changed = true;
		}
		return changed;
	}

	public boolean addAll(int... c)
	{
		if(null == c)
			throw new UnsupportedOperationException("Attempt to add to a Set with a null collection");

		boolean changed = false;
		for(int i: c)
			changed = this.store.add(new IntWrapper((Integer)i)) || changed;

		return changed;
	}

	/** Removes all elements from this Set. */
	@Override
	public void clear()
	{
		this.store.clear();
	}

	/** @return True if this set contains the specified element; false otherwise. */
	@Override
	public boolean contains(Object arg0)
	{
		return this.store.contains(arg0);
	}

	/**
	 * @return True if this set contains all elements of the specified
	 * collection; false otherwise.
	 */
	@Override
	public boolean containsAll(java.util.Collection<?> arg0)
	{
		return this.store.containsAll(arg0);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof Set))
			return false;
		Set other = (Set)obj;
		if(this.store == null)
		{
			if(other.store != null)
				return false;
		}
		else if(!this.store.equals(other.store))
			return false;
		return true;
	}

	/**
	 * Gets all elements of a specific type from this Set.
	 *
	 * @param <T> The type.
	 * @param c The type.
	 * @return The elements.
	 */
	@SuppressWarnings("unchecked")
	public <T> java.util.Set<T> getAll(Class<T> c)
	{
		if(c.equals(Integer.class))
		{
			IntSet ret = new IntSet();
			for(Object o: this)
				if(o instanceof IntWrapper)
					ret.add(((IntWrapper)o).value);
			return (java.util.Set<T>)ret;
		}
		java.util.Set<T> ret = new java.util.HashSet<T>();
		for(Object o: this)
		{
			if(c.isAssignableFrom(o.getClass()))
				ret.add((T)o);
		}
		return ret;
	}

	/**
	 * Gets all classes from this Set which are assignable to a specific class.
	 *
	 * @param c The specific class to check against.
	 * @return The elements.
	 */
	@SuppressWarnings("unchecked")
	public <T> java.util.Set<Class<? extends T>> getAllClasses(Class<T> c)
	{
		java.util.Set<Class<? extends T>> ret = new java.util.HashSet<Class<? extends T>>();
		for(Class<?> in: this.getAll(Class.class))
			if(c.isAssignableFrom(in))
				ret.add((Class<T>)in);
		return ret;
	}

	/**
	 * Gets a single element out of this Set.
	 *
	 * @param <T> The kind of item to get.
	 * @param c The kind of item to get.
	 * @return An arbitrary element of type <T> from this Set if it contains
	 * one; null otherwise.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOne(Class<T> c)
	{
		if(c.equals(Integer.class))
		{
			for(Object o: this)
				if(o instanceof IntWrapper)
					return (T)(Integer)(((IntWrapper)o).value);
		}
		else
		{
			for(Object o: this)
				if(c.isAssignableFrom(o.getClass()))
					return (T)o;
		}
		return null;
	}

	@Override
	public int hashCode()
	{
		return this.store.hashCode();
	}

	/**
	 * @return True if this Set contains no elements; false if it contains at
	 * least one.
	 */
	@Override
	public boolean isEmpty()
	{
		return this.store.isEmpty();
	}

	/** @return An iterator over the elements of this Set. */
	@Override
	public java.util.Iterator<Object> iterator()
	{
		return this.store.iterator();
	}

	/**
	 * Removes the specified element from this Set (assuming this set contains
	 * the specified element).
	 *
	 * @param arg0 The element to remove.
	 * @return Whether this Set changed as a result of this call.
	 */
	@Override
	public boolean remove(Object arg0)
	{
		return this.store.remove(arg0);
	}

	/**
	 * Removes all elements in the specified collection from this set.
	 *
	 * @param arg0 A collection containing the elements to remove.
	 * @return Whether this Set changed as a result of this call.
	 */
	@Override
	public boolean removeAll(java.util.Collection<?> arg0)
	{
		return this.store.removeAll(arg0);
	}

	/**
	 * Removes all elements in this Set that are not in the specified
	 * collection.
	 *
	 * @param arg0 The elements to retain in this Set.
	 * @return Whether this Set changed as a result of this call.
	 */
	@Override
	public boolean retainAll(java.util.Collection<?> arg0)
	{
		return this.store.retainAll(arg0);
	}

	/** @return The number of elements in this Set. */
	@Override
	public int size()
	{
		return this.store.size();
	}

	/**
	 * @return An array containing all of the elements in this Set. No
	 * references to the returned array are maintained by this Set.
	 */
	@Override
	public Object[] toArray()
	{
		return this.store.toArray();
	}

	/**
	 * Returns an array containing all of the elements in this Set; the runtime
	 * type of the returned array is that of the specified array. If the set
	 * fits in the specified array, it is returned therein. Otherwise, a new
	 * array is allocated with the runtime type of the specified array and the
	 * size of this set.
	 *
	 * If this Set fits in the specified array with room to spare, the element
	 * in the array immediately following the end of the Set is set to
	 * <code>null</code>.
	 *
	 * @param arg0 The array into which the elements of this Set are to be
	 * stored, if it is big enough; otherwise, a new array of the same runtime
	 * type is allocated for this purpose.
	 * @return An array containing all the elements in this Set.
	 */
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return this.store.toArray(arg0);
	}

	/** @return A string representation of this Set. */
	@Override
	public String toString()
	{
		return org.rnd.util.SeparatedList.get("and", this).toString();
	}
}

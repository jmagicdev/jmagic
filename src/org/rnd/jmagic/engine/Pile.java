package org.rnd.jmagic.engine;

public class Pile implements java.util.Set<GameObject>, Sanitizable
{
	private java.util.Set<GameObject> delegate;

	public Pile()
	{
		this.delegate = new java.util.HashSet<GameObject>();
	}

	@Override
	public boolean add(GameObject e)
	{
		return this.delegate.add(e);
	}

	@Override
	public boolean addAll(java.util.Collection<? extends GameObject> c)
	{
		return this.delegate.addAll(c);
	}

	@Override
	public void clear()
	{
		this.delegate.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		return this.delegate.contains(o);
	}

	@Override
	public boolean containsAll(java.util.Collection<?> c)
	{
		return this.delegate.containsAll(c);
	}

	@Override
	public boolean isEmpty()
	{
		return this.delegate.isEmpty();
	}

	@Override
	public java.util.Iterator<GameObject> iterator()
	{
		return this.delegate.iterator();
	}

	@Override
	public boolean remove(Object o)
	{
		return this.delegate.remove(o);
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c)
	{
		return this.delegate.removeAll(c);
	}

	@Override
	public boolean retainAll(java.util.Collection<?> c)
	{
		return this.delegate.retainAll(c);
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		java.util.HashSet<java.io.Serializable> ret = new java.util.HashSet<java.io.Serializable>();
		for(GameObject o: this.delegate)
			ret.add(o.sanitize(state, whoFor));
		return ret;
	}

	@Override
	public int size()
	{
		return this.delegate.size();
	}

	@Override
	public Object[] toArray()
	{
		return this.delegate.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return this.delegate.toArray(a);
	}
}

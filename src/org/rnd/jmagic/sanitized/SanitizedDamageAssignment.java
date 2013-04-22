package org.rnd.jmagic.sanitized;

import org.rnd.jmagic.engine.*;

public class SanitizedDamageAssignment implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	public final int source;
	public final int taker;

	public SanitizedDamageAssignment(DamageAssignment da)
	{
		this.source = da.sourceID;
		this.taker = da.takerID;
	}

	public static class SanitizedBatch implements java.util.Collection<SanitizedDamageAssignment>, java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public final java.util.Collection<SanitizedDamageAssignment> store;

		public SanitizedBatch(DamageAssignment.Batch b)
		{
			this.store = new java.util.LinkedList<SanitizedDamageAssignment>();
			for(DamageAssignment da: b)
				this.add(new SanitizedDamageAssignment(da));
		}

		@Override
		public boolean add(SanitizedDamageAssignment e)
		{
			return this.store.add(e);
		}

		@Override
		public boolean addAll(java.util.Collection<? extends SanitizedDamageAssignment> c)
		{
			return this.store.addAll(c);
		}

		@Override
		public void clear()
		{
			this.store.clear();
		}

		@Override
		public boolean contains(Object o)
		{
			return this.store.contains(o);
		}

		@Override
		public boolean containsAll(java.util.Collection<?> c)
		{
			return this.store.containsAll(c);
		}

		@Override
		public boolean isEmpty()
		{
			return this.store.isEmpty();
		}

		@Override
		public java.util.Iterator<SanitizedDamageAssignment> iterator()
		{
			return this.store.iterator();
		}

		@Override
		public boolean remove(Object o)
		{
			return this.store.remove(o);
		}

		@Override
		public boolean removeAll(java.util.Collection<?> c)
		{
			return this.store.removeAll(c);
		}

		@Override
		public boolean retainAll(java.util.Collection<?> c)
		{
			return this.store.retainAll(c);
		}

		@Override
		public int size()
		{
			return this.store.size();
		}

		@Override
		public Object[] toArray()
		{
			return this.store.toArray();
		}

		@Override
		public <T> T[] toArray(T[] a)
		{
			return this.store.toArray(a);
		}

		@Override
		public String toString()
		{
			return this.store.toString();
		}
	}

	public static class Replacement implements SanitizedReplacement
	{
		private static final long serialVersionUID = 1L;

		public final SanitizedReplacementEffect effect;
		public final SanitizedBatch batch;

		public Replacement(GameState state, Player whoFor, DamageAssignment.Replacement r)
		{
			this.effect = new SanitizedReplacementEffect(state, whoFor, r.effect);
			this.batch = new SanitizedBatch(r.batch);
		}

		@Override
		public boolean isOptionalForMe()
		{
			return this.effect.isOptionalForMe();
		}

		@Override
		public String toString()
		{
			return "Apply '" + this.effect + "'";
		}
	}
}

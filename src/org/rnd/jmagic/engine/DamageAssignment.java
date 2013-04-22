package org.rnd.jmagic.engine;

public class DamageAssignment implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * A convenience class to represent a collection of DamageAssignment
	 * instances.
	 */
	public static class Batch implements java.util.Collection<DamageAssignment>
	{
		public final java.util.Collection<DamageAssignment> store;

		public Batch()
		{
			this.store = new java.util.LinkedList<DamageAssignment>();
		}

		public Batch(java.util.Collection<DamageAssignment> copy)
		{
			this.store = new java.util.LinkedList<DamageAssignment>(copy);
		}

		@Override
		public boolean add(DamageAssignment e)
		{
			return this.store.add(e);
		}

		@Override
		public boolean addAll(java.util.Collection<? extends DamageAssignment> c)
		{
			return this.store.addAll(c);
		}

		/**
		 * @return Whether all the damage in this batch is identical. More
		 * formally, if for every two {@link DamageAssignment}s <code>a</code>
		 * and <code>b</code>, a.isTheSameAs(b), returns true; otherwise returns
		 * false.
		 */
		public boolean allTheSame()
		{
			if(this.size() < 2)
				return true;

			java.util.Iterator<DamageAssignment> iter = this.iterator();
			java.util.Iterator<DamageAssignment> other = this.iterator();
			other.next();
			while(other.hasNext())
			{
				DamageAssignment first = iter.next();
				DamageAssignment second = other.next();
				if(!first.isTheSameAs(second))
					return false;
			}
			return true;
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
		public java.util.Iterator<DamageAssignment> iterator()
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

	/**
	 * Represents a DamageReplacementEffect as it would apply to a batch of
	 * damage.
	 */
	public static class Replacement implements Sanitizable
	{
		public DamageReplacementEffect effect;
		public Batch batch;

		public Replacement(DamageReplacementEffect effect, Batch batch)
		{
			this.effect = effect;
			this.batch = batch;
		}

		@Override
		public java.io.Serializable sanitize(GameState state, Player whoFor)
		{
			return new org.rnd.jmagic.sanitized.SanitizedDamageAssignment.Replacement(state, whoFor, this);
		}

		@Override
		public String toString()
		{
			return "Apply '" + this.effect + "'";
		}
	}

	public boolean isCombatDamage;

	/** True if this damage "can't be prevented". */
	public boolean isUnpreventable;

	private String name;

	// TODO : make this field nontransient (i.e., make this class Sanitizable
	// rather than Serializable)
	/** Replacement effects that have replaced this ONE damage. */
	public transient final java.util.Set<ReplacementEffect> replacedBy;

	/** The source of this damage. */
	public final int sourceID;

	/** What is taking this damage. */
	public final int takerID;

	/**
	 * Constructs a new DamageAssignment object based on the copy.
	 */
	public DamageAssignment(DamageAssignment copy)
	{
		this.isCombatDamage = copy.isCombatDamage;
		this.isUnpreventable = copy.isUnpreventable;
		this.name = copy.name;
		this.sourceID = copy.sourceID;
		this.takerID = copy.takerID;
		this.replacedBy = new java.util.LinkedHashSet<ReplacementEffect>(copy.replacedBy);
	}

	/**
	 * Construct a class that represents ONE damage.
	 * 
	 * @param source The thing dealing ONE damage.
	 * @param taker The thing taking ONE damage.
	 */
	public DamageAssignment(GameObject source, Identified taker)
	{
		this.isCombatDamage = false;
		this.isUnpreventable = false;
		this.name = source + " deals one " + (this.isCombatDamage ? "combat " : "") + "damage to " + taker;
		this.sourceID = source.ID;
		this.takerID = taker.ID;
		this.replacedBy = new java.util.LinkedHashSet<ReplacementEffect>();
	}

	// DO NOT OVERRIDE EQUALS. Reference semantics for equals are required;
	// damage events pass them in Sets.
	/**
	 * @return Whether <code>obj</code> is a {@link DamageAssignment} identical
	 * to this one.
	 */
	public boolean isTheSameAs(DamageAssignment other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.isCombatDamage != other.isCombatDamage)
			return false;
		if(this.isUnpreventable != other.isUnpreventable)
			return false;
		if(!this.name.equals(other.name))
			return false;
		if(!this.replacedBy.equals(other.replacedBy))
			return false;
		if(this.sourceID != other.sourceID)
			return false;
		if(this.takerID != other.takerID)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}

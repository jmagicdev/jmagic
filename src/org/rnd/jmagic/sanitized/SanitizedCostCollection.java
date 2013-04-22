package org.rnd.jmagic.sanitized;

public class SanitizedCostCollection implements java.io.Serializable
{

	public static class SanitizedEventFactory implements java.io.Serializable
	{
		private static final long serialVersionUID = 1L;

		public final String name;
		// public final org.rnd.jmagic.engine.EventType type;
		public final String type;

		public SanitizedEventFactory(org.rnd.jmagic.engine.EventFactory ef)
		{
			this.name = ef.name;
			this.type = ef.type.toString();
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
			result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			SanitizedEventFactory other = (SanitizedEventFactory)obj;
			if(this.name == null)
			{
				if(other.name != null)
					return false;
			}
			else if(!this.name.equals(other.name))
				return false;
			if(this.type == null)
			{
				if(other.type != null)
					return false;
			}
			else if(!this.type.equals(other.type))
				return false;
			return true;
		}
	}

	private static final long serialVersionUID = 2L;

	public final String type;
	public final boolean allowMultiples;
	public final java.util.Set<SanitizedEventFactory> events;
	public final org.rnd.jmagic.engine.ManaPool manaCost;

	private final String toString;

	public SanitizedCostCollection(org.rnd.jmagic.engine.CostCollection c)
	{
		java.util.Set<SanitizedEventFactory> events = new java.util.HashSet<SanitizedEventFactory>();

		for(org.rnd.jmagic.engine.EventFactory ef: c.events)
			events.add(new SanitizedEventFactory(ef));

		this.type = c.type.toString();

		this.allowMultiples = c.allowMultiples;

		this.events = java.util.Collections.unmodifiableSet(events);

		this.manaCost = new org.rnd.jmagic.engine.ManaPool(c.manaCost);

		this.toString = this.type + ": " + c.toString();
	}

	public SanitizedCostCollection(String type, org.rnd.jmagic.engine.ManaPool mana)
	{
		this.type = type;
		this.allowMultiples = false;
		this.events = java.util.Collections.emptySet();
		this.manaCost = mana;
		this.toString = mana.toString();
	}

	@Override
	public int hashCode()
	{
		int result = 31 + ((this.events == null) ? 0 : this.events.hashCode());
		result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
		result = 31 * result + ((this.manaCost == null) ? 0 : this.manaCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		SanitizedCostCollection other = (SanitizedCostCollection)obj;
		if(this.type == null)
		{
			if(other.type != null)
				return false;
		}
		else if(!this.type.equals(other.type))
			return false;
		if(this.events == null)
		{
			if(other.events != null)
				return false;
		}
		else if(!this.events.equals(other.events))
			return false;
		if(this.manaCost == null)
		{
			if(other.manaCost != null)
				return false;
		}
		else if(!this.manaCost.equals(other.manaCost))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return this.toString;
	}
}

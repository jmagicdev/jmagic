package org.rnd.jmagic.gui;

public class Arrow
{
	public enum ArrowType
	{
		ATTACHMENT(java.awt.Color.BLUE, "Attachments"), //
		ATTACKING(java.awt.Color.ORANGE, "Attacking"), //
		BLOCKING(java.awt.Color.GREEN, "Blocking"), //
		CAUSE(java.awt.Color.MAGENTA, "Cause", true), //
		CONTROLLER(java.awt.Color.DARK_GRAY, "Controller", true), //
		LINK(java.awt.Color.MAGENTA, "Linked Objects"), //
		PAIR(java.awt.Color.MAGENTA, "Paired Creatures"), //
		SOURCE(new java.awt.Color(0, 51, 102), "Source of Ability", true), //
		TARGET(java.awt.Color.RED, "Targetting"), //
		;

		private final java.awt.Color defaultColor;
		public final String description;
		private final boolean hollow;

		ArrowType(java.awt.Color color, String description)
		{
			this(color, description, false);
		}

		ArrowType(java.awt.Color defaultColor, String description, boolean hollow)
		{
			this.defaultColor = defaultColor;
			this.description = description;
			this.hollow = hollow;
		}

		public boolean isHollow()
		{
			return this.hollow;
		}

		public java.awt.Color getDefaultColor()
		{
			return this.defaultColor;
		}
	}

	public final int sourceID;
	public final int targetID;
	public final ArrowType type;

	public Arrow(int source, int target, ArrowType type)
	{
		this.sourceID = source;
		this.targetID = target;
		this.type = type;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.sourceID;
		result = prime * result + this.targetID;
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
		Arrow other = (Arrow)obj;
		if(this.sourceID != other.sourceID)
			return false;
		if(this.targetID != other.targetID)
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

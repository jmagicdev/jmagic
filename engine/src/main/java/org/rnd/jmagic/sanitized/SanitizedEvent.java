package org.rnd.jmagic.sanitized;

public class SanitizedEvent extends SanitizedIdentified
{
	private static final long serialVersionUID = 2L;

	public final int sourceID;
	public final String type;

	public SanitizedEvent(org.rnd.jmagic.engine.Event e)
	{
		super(e);

		org.rnd.jmagic.engine.GameObject source = e.getSource();
		if(source == null)
			this.sourceID = -1;
		else
			this.sourceID = source.ID;

		this.type = e.type.toString();
	}

	public SanitizedEvent(org.rnd.jmagic.engine.Event e, String nameOverride)
	{
		super(e, nameOverride);

		org.rnd.jmagic.engine.GameObject source = e.getSource();
		if(source == null)
			this.sourceID = -1;
		else
			this.sourceID = source.ID;

		this.type = e.type.toString();
	}

	/**
	 * @param state State to pull information from, if needed.
	 */
	public String getDescription(SanitizedGameState state)
	{
		return this.toString();
	}

	public static class Reveal extends SanitizedEvent
	{
		private static final long serialVersionUID = 1L;

		public java.util.Collection<SanitizedGameObject> revealed;

		public Reveal(org.rnd.jmagic.engine.Event e, java.util.Collection<org.rnd.jmagic.engine.GameObject> toReveal, org.rnd.jmagic.engine.Player whoFor)
		{
			super(e);

			this.revealed = new java.util.LinkedList<SanitizedGameObject>();
			for(org.rnd.jmagic.engine.GameObject o: toReveal)
				this.revealed.add(new SanitizedGameObject(o, whoFor));
		}

		@Override
		public String getDescription(SanitizedGameState state)
		{
			return "Reveal " + this.revealed + ".";
		}
	}

	public static class Move extends SanitizedEvent
	{
		private static final long serialVersionUID = 1L;

		public java.util.Collection<SanitizedZoneChange> zoneChanges;

		public Move(org.rnd.jmagic.engine.Event e, java.util.Collection<org.rnd.jmagic.engine.ZoneChange> zoneChanges, org.rnd.jmagic.engine.Player whoFor)
		{
			super(e);

			this.zoneChanges = new java.util.LinkedList<SanitizedZoneChange>();
			for(org.rnd.jmagic.engine.ZoneChange zc: zoneChanges)
				this.zoneChanges.add(zc.sanitize(e.state, whoFor));
		}

		@Override
		public String getDescription(SanitizedGameState state)
		{
			StringBuilder ret = new StringBuilder();

			for(SanitizedZoneChange zc: this.zoneChanges)
			{
				String oldObjectName = null;
				String newObjectName = null;
				String destination = state.get(zc.destinationID).name;
				String source = state.get(zc.sourceID).name;

				if(state.containsKey(zc.oldID))
				{
					oldObjectName = state.get(zc.oldID).name;

					if(state.containsKey(zc.newID))
						newObjectName = state.get(zc.newID).name;
					else
						newObjectName = oldObjectName;
				}
				else if(state.containsKey(zc.newID))
					oldObjectName = newObjectName = state.get(zc.newID).name;
				else
					oldObjectName = newObjectName = "An object";

				ret.append(oldObjectName + " moved from " + source + " to " + destination + (oldObjectName.equals(newObjectName) ? "" : " and became " + newObjectName) + ". ");
			}
			return ret.toString();
		}
	}

	public static class Look extends SanitizedEvent
	{
		private static final long serialVersionUID = 1L;

		public java.util.Collection<SanitizedGameObject> lookedAt;
		public SanitizedPlayer player;

		public Look(org.rnd.jmagic.engine.Event e, org.rnd.jmagic.engine.Player p, java.util.Collection<org.rnd.jmagic.engine.GameObject> toLookAt)
		{
			super(e);

			this.player = new SanitizedPlayer(p);

			this.lookedAt = new java.util.LinkedList<SanitizedGameObject>();
			for(org.rnd.jmagic.engine.GameObject o: toLookAt)
				this.lookedAt.add(new SanitizedGameObject(o, p));
		}

		@Override
		public String getDescription(SanitizedGameState state)
		{
			return this.player + " looked at " + this.lookedAt + ".";
		}
	}
}

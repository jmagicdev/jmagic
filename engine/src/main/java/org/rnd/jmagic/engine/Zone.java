package org.rnd.jmagic.engine;

/** Represents a zone in the game. */
public class Zone extends Identified implements Iterable<GameObject>, Ownable, Targetable, Sanitizable
{
	/** Objects that are currently in this zone. */
	public java.util.List<GameObject> objects;

	/**
	 * Who can see this zone. Players with IDs in this collection can see the
	 * faces of all objects in this zone.
	 */
	public java.util.Set<Integer> visibleTo;

	/**
	 * @param state The game state in which this zone will exist.
	 * @param name The name of this zone.
	 */
	public Zone(GameState state, String name)
	{
		super(state);

		this.setName(name);

		this.objects = new IDList<GameObject>(state);
		this.visibleTo = new java.util.HashSet<Integer>();
	}

	/**
	 * Adds an object at a specific position in this zone.
	 * 
	 * @param object The object to add.
	 * @param position The position at which to add the object. 1 = top, 2 =
	 * second from top, 3 = long term plans... -1 = bottom, -2 = second from
	 * bottom...
	 */
	public void addAtPosition(GameObject object, int position)
	{
		if(position == 0)
			throw new UnsupportedOperationException("Zone: tried to add a card at index 0");

		if(position > 0)
			position--;
		else
			position += (this.objects.size() + 1);

		if(position > this.objects.size())
			position = this.objects.size();
		this.objects.add(position, object);
		object.setZone(this);
	}

	/**
	 * Adds an object to the top of this zone. This is the function that should
	 * be used most often when adding an object to a zone.
	 * 
	 * @param object The object to add.
	 */
	public void addToTop(GameObject object)
	{
		this.objects.add(0, object);
		object.setZone(this);
	}

	/** CIRCU CAN FUCK RIGHT OFF. */
	@Override
	public SetPattern cantBeTheTargetOf()
	{
		return SetPattern.NEVER_MATCH;
	}

	/** CIRCU CAN FUCK RIGHT OFF. */
	@Override
	public void cantBeTheTargetOf(SetPattern what)
	{
		throw new UnsupportedOperationException("Targeting restriction on a Zone?!?!");
	}

	@Override
	public Zone clone(GameState state)
	{
		Zone ret = (Zone)super.clone(state);
		ret.objects = new IDList<GameObject>(state, this.objects);
		ret.visibleTo = new java.util.HashSet<Integer>(this.visibleTo);
		return ret;
	}

	@Override
	public Zone getActual()
	{
		return (Zone)super.getActual();
	}

	/** @return The owner of the objects in this zone. */
	@Override
	public Player getOwner(GameState state)
	{
		for(Player player: state.players)
		{
			if(player.getHandID() == this.ID || player.getGraveyardID() == this.ID || player.getLibraryID() == this.ID)
				return player;
		}
		return null;
	}

	/** @return This zone in the physical game state. */
	@Override
	public Zone getPhysical()
	{
		return (Zone)super.getPhysical();
	}

	/** @return True if this is a graveyard; false otherwise. */
	public boolean isGraveyard()
	{
		for(Player player: this.game.actualState.players)
			if(player.getGraveyardID() == this.ID)
				return true;
		return false;
	}

	/** @return True if this is a hand; false otherwise. */
	public boolean isHand()
	{
		for(Player player: this.game.actualState.players)
			if(player.getHandID() == this.ID)
				return true;
		return false;
	}

	/** @return True if this is a library; false otherwise. */
	public boolean isLibrary()
	{
		for(Player player: this.game.actualState.players)
			if(player.getLibraryID() == this.ID)
				return true;
		return false;
	}

	/** @return An iterator over the objects in this zone. */
	@Override
	public java.util.Iterator<GameObject> iterator()
	{
		return this.objects.iterator();
	}

	public GameObject peekAtPosition(int position)
	{
		if(position == 0)
			throw new UnsupportedOperationException("Zone: tried to get a card at index 0");

		if(position > 0)
			position--;
		else
			position += (this.objects.size() + 1);

		if(position > this.objects.size())
			position = this.objects.size();

		try
		{
			return this.objects.get(position);
		}
		catch(IndexOutOfBoundsException e)
		{
			return null;
		}
	}

	public GameObject peekAtTop()
	{
		return peekAtPosition(1);
	}

	/**
	 * Removes the specified object from this zone and replaces it with an
	 * LKI-ghost of that object.
	 * 
	 * TODO : Should this throw an exception rather than returning false on
	 * failure? -RulesGuru
	 * 
	 * @param object The object to remove.
	 * @return Whether the object was successfully removed.
	 */
	public boolean remove(GameObject object)
	{
		if(this.objects.remove(object))
		{
			// Tokens that are being created in the RFG zone don't exist long
			// enough physically to exist actually, so test first.
			if(this.game.actualState.containsIdentified(object.ID))
				this.game.actualState.<GameObject>get(object.ID).ghost();

			return true;
		}

		return false;
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedZone(state.<Zone>get(this.ID));
	}
}

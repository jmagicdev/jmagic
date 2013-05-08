package org.rnd.jmagic.engine;

/**
 * A class containing all the necessary parts to construct an event except for a
 * gamestate. This provides a non-stateful way to store event information. Flags
 * can be set on this class to affect the type of event it creates, however,
 * after the first event is created, this class should be considered immutable.
 * If any flags are changed after EventFactory is finalized, the class will
 * throw a runtime exception.
 */
public class EventFactory
{
	public static final class Constant extends EventFactory
	{
		public Constant(String name)
		{
			super(null, name);
			this.finalized = true;
		}

		@Override
		public Event createEvent(Game game, GameObject object)
		{
			throw new RuntimeException("Calling createEvent() on an EventFactory.Constant object.");
		}
	}

	public final String name;
	public final EventType type;
	public final java.util.Map<EventType.Parameter, SetGenerator> parameters;

	private java.util.Collection<Integer> cantMoveIDs;
	private boolean preserve;
	private boolean usesX;
	private int linkID;

	/** True when the effect is added by a keyword. */
	public boolean hidden;

	/**
	 * ID of an event, signifying that after an event created from this is
	 * performed, the result of that event should be passed to this one.
	 */
	private int passResultID;

	protected boolean finalized;

	public EventFactory(EventType type, java.util.Map<EventType.Parameter, SetGenerator> parameters, String name)
	{
		this.type = type;
		this.parameters = parameters;
		this.preserve = false;
		this.name = name;
		this.cantMoveIDs = new java.util.LinkedList<Integer>();

		this.usesX = false;
		this.linkID = -1;
		this.passResultID = -1;

		this.finalized = false;
	}

	public EventFactory(EventType type, String name)
	{
		this(type, new java.util.HashMap<EventType.Parameter, SetGenerator>(), name);
	}

	/**
	 * An effect that modifies how a permanent enters the battlefield may cause
	 * other objects to change zones. Such an effect can't cause the permanent
	 * itself to not enter the battlefield.
	 * 
	 * Tells this EventFactory that it can't move the specified object in
	 * accordance with this rule.
	 */
	public void cantMove(int objectID)
	{
		this.cantMoveIDs.add(objectID);
	}

	private void checkFinalized()
	{
		if(this.finalized)
			throw new RuntimeException("Attempting to modify a finalized EventFactory");
	}

	private Event createEvent(GameState state)
	{
		this.finalized = true;

		Event event = new Event(state, this.name, this.type);
		event.parameters.putAll(this.parameters);
		event.cantMoveIDs = java.util.Collections.unmodifiableCollection(this.cantMoveIDs);
		event.passResultID = this.passResultID;
		event.preserve = this.preserve;
		event.usesX = this.usesX;
		if(this.linkID != -1)
			event.setStoreInObject((Linkable)state.get(this.linkID));

		return event;
	}

	/**
	 * Creates an event in the physical state of a game from this factory whose
	 * source is an object.
	 * 
	 * @param game The game to create the new Event in
	 * @param source The object that is the source of the event.
	 * @return A newly created physical event in the given game with the given
	 * source.
	 */
	public Event createEvent(Game game, GameObject source)
	{
		Event event = this.createEvent(game.physicalState);
		event.setSource(source);
		if(source != null)
		{
			GameObject physical = event.getSource().getPhysical();
			if(null == physical.effectsGenerated)
				physical.effectsGenerated = new java.util.HashMap<EventFactory, Integer>();
			physical.effectsGenerated.put(this, event.ID);
		}
		return event;
	}

	/**
	 * Tells this factory that after an event created from this factory is
	 * performed, it should pass its result to the given event.
	 */
	public void passResultTo(Event toPassTo)
	{
		checkFinalized();
		this.passResultID = toPassTo.ID;
	}

	public void preserveCreatedEvents()
	{
		this.preserve = true;
	}

	/**
	 * Determines which objects this event refers to; used by
	 * {@link org.rnd.jmagic.engine.generators.AllSourcesOfDamage}.
	 */
	public java.util.Collection<GameObject> refersTo(GameState state, Identified thisObject)
	{
		java.util.Collection<GameObject> ret = new java.util.HashSet<GameObject>();

		for(SetGenerator parameter: this.parameters.values())
			for(GameObject referencedObject: parameter.evaluate(state, thisObject).getAll(GameObject.class))
				ret.add(referencedObject);

		return ret;
	}

	/**
	 * Sets the objects that events this factory produces will store their
	 * results in when performed.
	 */
	public void setLink(Linkable link)
	{
		checkFinalized();
		this.linkID = ((Identified)link).ID;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	public void usesX()
	{
		checkFinalized();
		this.usesX = true;
	}

	public boolean willUseX()
	{
		return this.usesX;
	}
}

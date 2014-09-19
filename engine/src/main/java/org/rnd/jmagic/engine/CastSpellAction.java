package org.rnd.jmagic.engine;

/** Represents the act of casting a spell. */
public class CastSpellAction extends CastSpellOrActivateAbilityAction
{
	private static class EventFactoryWithSource
	{
		public EventFactory event;
		public int sourceID;
	}

	private java.util.List<EventFactoryWithSource> attachedEvents;

	private static String actionName(GameObject spell, java.util.Collection<Integer> castableSides)
	{
		Characteristics[] characteristics = spell.getCharacteristics();
		return castableSides.stream().map(i -> characteristics[i].name).reduce((left, right) -> left + " // " + right).orElse("");
	}

	/**
	 * Creates a cast spell action for a specific spell.
	 * 
	 * @param game The game in which the action will be performed.
	 * @param spell The spell to cast.
	 * @param castableSides Which sides of the spell are castable. For most
	 * cards, this will just be [0]. For split cards not under a prohibition of
	 * some sort, this will be [0, 1].
	 * @param casting Who will be casting the spell.
	 * @param source The thing generating this cast action -- usually the card
	 * itself, but not always.
	 */
	public CastSpellAction(Game game, GameObject spell, java.util.Collection<Integer> castableSides, Player casting, int source)
	{
		super(game, "Cast " + actionName(spell, castableSides), castableSides, casting, source);
		this.attachedEvents = new java.util.LinkedList<EventFactoryWithSource>();
		this.toBePlayedID = spell.ID;
	}

	void attachEvent(EventFactory event, int sourceID)
	{
		EventFactoryWithSource add = new EventFactoryWithSource();
		add.event = event;
		add.sourceID = sourceID;
		this.attachedEvents.add(add);

		this.name += (". " + event.name);
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		GameObject ability = this.game.physicalState.get(this.toBePlayedID);
		return new PlayerInterface.ReversionParameters("CastSpellAction", player.getName() + " failed to cast " + ability.getName() + ".");
	}

	@Override
	public GameObject play()
	{
		GameObject played = super.play();
		if(null != played)
			for(EventFactoryWithSource e: this.attachedEvents)
				if(!(e.event.createEvent(this.game, this.game.actualState.<GameObject>get(e.sourceID)).perform(null, true)))
					return null;
		return played;
	}
}

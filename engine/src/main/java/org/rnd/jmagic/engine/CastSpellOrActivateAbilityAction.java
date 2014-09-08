package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/** Represents the act of playing a spell or activated ability. */
public abstract class CastSpellOrActivateAbilityAction extends PlayerAction
{
	/**
	 * The spell or ability to be played when this action is performed. Classes
	 * extending this class should set this member on construction, most likely
	 * by taking it as a parameter to the constructor.
	 */
	public int toBePlayedID;

	private int playedID;
	private SetGenerator forcedAlternateCost;

	protected final int[] characteristicsIndices;

	/**
	 * @param game The game in which this action will be performed.
	 * @param name This should usually be Magic-ese that describes this action;
	 * for example, "Cast Giant Growth", or the text of an ability.
	 */
	public CastSpellOrActivateAbilityAction(Game game, String name, int[] characteristicsIndices, Player caster, int source)
	{
		super(game, name, caster, source);
		this.playedID = -1;
		this.forcedAlternateCost = null;
		this.characteristicsIndices = characteristicsIndices;
	}

	/**
	 * If this action is coming from an effect that specifies an alternate cost,
	 * call this method to specify that cost. If the effect specifies "without
	 * paying its mana cost", pass an
	 * {@link org.rnd.jmagic.engine.generators.Empty} here.
	 */
	public void forceAlternateCost(SetGenerator alternateCost)
	{
		this.forcedAlternateCost = alternateCost;
	}

	@Override
	public int getSourceObjectID()
	{
		return this.toBePlayedID;
	}

	/**
	 * 601.5. A player can't begin to cast a spell that's prohibited from being
	 * cast.
	 * 
	 * 602.5. A player can't begin to activate an ability that's prohibited from
	 * being activated.
	 * 
	 * @return Whether the appropriate player can begin to play the appropriate
	 * spell or ability, in accordance with rules 601.5 and 602.5.
	 */
	public boolean attempt()
	{
		GameObject toBePlayed = this.game.actualState.get(this.toBePlayedID);
		Player caster = this.game.actualState.get(this.actorID);

		Event event = new Event(this.game.physicalState, caster + " plays " + toBePlayed + ".", EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
		event.parameters.put(EventType.Parameter.PLAYER, Identity.instance(caster));
		event.parameters.put(EventType.Parameter.OBJECT, Identity.instance(toBePlayed));
		if(this.forcedAlternateCost != null)
			event.parameters.put(EventType.Parameter.ALTERNATE_COST, this.forcedAlternateCost);
		event.parameters.put(EventType.Parameter.EFFECT, Identity.fromCollection(new Set(this.characteristicsIndices)));

		event.setSource(toBePlayed);
		return !event.isProhibited(this.game.actualState);
	}

	/**
	 * <p>
	 * Causes the acting player to carry out this action and play the spell or
	 * ability specified on construction.
	 * </p>
	 * <p>
	 * This method is final. Don't change this. If you want to make a
	 * {@link CastSpellAction} that has a different behavior, override
	 * {@link #play()}.
	 * </p>
	 * 
	 * @return Whether the spell or ability was successfully played.
	 */
	@Override
	public final boolean perform()
	{
		GameObject played = this.play();
		if(played == null)
			return false;

		this.playedID = played.ID;
		return true;
	}

	/**
	 * Plays the appropriate object, and returns that object as it exists on the
	 * stack (returns null if the play failed). This
	 */
	public GameObject play()
	{
		GameObject toBePlayed = this.game.actualState.get(this.toBePlayedID);
		Player caster = this.game.actualState.get(this.actorID);

		Event event = new Event(this.game.physicalState, caster + " plays " + toBePlayed + ".", EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
		event.parameters.put(EventType.Parameter.PLAYER, Identity.instance(caster));
		event.parameters.put(EventType.Parameter.ACTION, Identity.instance(this));
		event.parameters.put(EventType.Parameter.OBJECT, Identity.instance(toBePlayed));
		// this Identity looks inefficient, but if you try to replace it, please
		// be very careful that the ints end up in the identity. it's easy to
		// make this evaluate to [[1]] instead of [1].
		event.parameters.put(EventType.Parameter.EFFECT, Identity.fromCollection(new Set(this.characteristicsIndices)));
		if(this.forcedAlternateCost != null)
			event.parameters.put(EventType.Parameter.ALTERNATE_COST, this.forcedAlternateCost);

		if(event.perform(null, true))
			return event.getResult().getOne(GameObject.class);
		return null;
	}

	/**
	 * @return If this action has been successfully performed, the physical card
	 * that was played as a result of the action; otherwise, null.
	 */
	public GameObject played()
	{
		if(this.playedID == -1)
			return null;
		return this.game.physicalState.<GameObject>get(this.playedID);
	}

	@Override
	public java.io.Serializable sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedCastSpellOrActivateAbilityAction(this);
	}
}

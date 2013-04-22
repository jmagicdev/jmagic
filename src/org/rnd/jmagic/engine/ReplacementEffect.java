package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.generators.*;

/** Encapsulates common functionality between both kinds of replacement effects. */
public abstract class ReplacementEffect implements Sanitizable
{
	private int effectID;

	public Game game;
	public String name;
	public SetGenerator optional;

	/**
	 * Constructs a new replacement effect.
	 * 
	 * @param game The game to compare things in.
	 * @param name The text of this effect.
	 */
	public ReplacementEffect(Game game, String name)
	{
		this.game = game;
		this.name = name;
		this.effectID = -1;
		this.optional = org.rnd.jmagic.engine.generators.Empty.instance();
	}

	/**
	 * Determines whether to apply this effect. If the effect is not optional,
	 * this method always returns true. For optional effects, it asks the player
	 * whether to apply the effect.
	 * 
	 * @param state The state in which to make this determination.
	 * @return Whether to apply this effect.
	 */
	protected boolean apply(GameState state)
	{
		Player chooser = this.optional.evaluate(state, this.getSourceObject(state)).getOne(Player.class);
		Answer apply = Answer.YES;
		if(chooser != null)
		{
			PlayerInterface.ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.OPTIONAL_REPLACEMENT);
			chooseParameters.replacement = this.name;
			apply = chooser.choose(chooseParameters).get(0);
		}

		return apply == Answer.YES;
	}

	/**
	 * Gets the floating continuous effect that generated this replacement
	 * effect.
	 * 
	 * @param state The state in which the effect that generated this effect
	 * exists.
	 * @return Null if this effect came from a static ability; otherwise, the
	 * floating continuous effect that generated this replacement effect.
	 */
	public FloatingContinuousEffect getFloatingContinuousEffect(GameState state)
	{
		if(state.containsIdentified(this.effectID))
		{
			Identified i = state.get(this.effectID);
			if(i instanceof FloatingContinuousEffect)
				return (FloatingContinuousEffect)i;
		}
		return null;
	}

	/**
	 * Gets the object that generated the floating continuous effect or has the
	 * static ability that generated this replacement effect.
	 * 
	 * @param state The state in which the object that generated this effect
	 * exists.
	 * @return The object that ultimately generated this replacement effect.
	 */
	public Identified getSourceObject(GameState state)
	{
		return state.<ContinuousEffect>get(this.effectID).getSourceObject();
	}

	/**
	 * Gets the object whose static ability generated this replacement effect.
	 * 
	 * @param state The state in which the object that generated this effect
	 * exists.
	 * @return Null if this effect came from a floating continuous effect;
	 * otherwise, the object whose static ability generated this replacement
	 * effect.
	 */
	public Identified getStaticSourceObject(GameState state)
	{
		StaticAbility staticSource = state.<ContinuousEffect>get(this.effectID).getStaticSource();
		if(null == staticSource)
			return null;

		return staticSource.getSource(state);
	}

	/**
	 * Makes this effect optional.
	 * 
	 * @param player When this effect would apply, the player who is to choose
	 * whether it does.
	 */
	public void makeOptional(SetGenerator player)
	{
		this.optional = player;
	}

	/**
	 * 609.7a If an effect requires a player to choose a source of damage, he or
	 * she may choose a permanent; a spell on the stack (including a permanent
	 * spell); any object referred to ... by a replacement or prevention effect
	 * that's waiting to apply; or ... .
	 * 
	 * @param state The state to evaluate generators in
	 * @return Objects this effect refers to. When overriding this method, if
	 * this effect refers to a permanent or spell, this method need not return
	 * that permanent or spell (though it may), since 609.7a explicitly covers
	 * those objects.
	 */
	public abstract java.util.Collection<GameObject> refersTo(GameState state);

	@Override
	public org.rnd.jmagic.sanitized.SanitizedReplacementEffect sanitize(GameState state, Player whoFor)
	{
		return new org.rnd.jmagic.sanitized.SanitizedReplacementEffect(state, whoFor, this);
	}

	/**
	 * Tells this replacement effect which continuous effect it came from.
	 * 
	 * @param effect The effect this effect came from.
	 */
	public void setContinuousEffect(ContinuousEffect effect)
	{
		this.effectID = effect.ID;
	}

	@Override
	public String toString()
	{
		return this.name;
	}

	/**
	 * @return A SetGenerator that evaluates to the {@link Event},
	 * {@link DamageAssignment}(s), or {@link ZoneChange} replaced by this
	 * effect.
	 */
	public SetGenerator replacedByThis()
	{
		return ReplacedBy.instance(Identity.instance(this));
	}
}

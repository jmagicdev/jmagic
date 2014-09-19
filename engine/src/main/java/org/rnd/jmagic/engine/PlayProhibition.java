package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.patterns.*;

/**
 * Describes a "[players] can't play/cast [objects]" effect, like Meddling Mage.
 * 
 * A SetPattern that you give to a PlayProhibition will be matched against
 * Characteristics -- NOT GameObjects!! This is because prohibitions that apply
 * to one half of a split card might not apply to the other half. For example,
 * if spells named "Fire" are prohibited, or if red spells are prohibited, Ice
 * can still be cast.
 */
public class PlayProhibition
{
	// dear programmer,

	// please, if you're tempted to make any of these fields not-private,
	// consider extending this class instead.

	// love,
	// RulesGuru

	private int sourceID;

	private final SetPattern players;
	private final SetPattern things;

	/**
	 * Makes a prohibition saying that [players] can't play [things].
	 * 
	 * @param source The thing this prohibition is coming from (e.g. the
	 * Meddling Mage itself).
	 * @param players The players who are prohibited from playing the given
	 * objects.
	 * @param things A SetPattern matching Characteristics of things that can't
	 * be played.
	 */
	public PlayProhibition(SetGenerator players, CharacteristicsMatcherFunction matcher)
	{
		this.players = new SimpleSetPattern(players);
		this.things = new org.rnd.jmagic.engine.patterns.CharacteristicsPattern(matcher);
	}

	/**
	 * Makes a prohibition saying that [players] can't play [things].
	 * 
	 * @param source The thing this prohibition is coming from (e.g. the
	 * Meddling Mage itself).
	 * @param players The players who are prohibited from playing the given
	 * objects.
	 * @param things A SetPattern matching Characteristics of things that can't
	 * be played.
	 */
	public PlayProhibition(SetGenerator players, CharacteristicsEvaluatorFunction matcher, SetGenerator data)
	{
		this.players = new SimpleSetPattern(players);
		this.things = new org.rnd.jmagic.engine.patterns.CharacteristicsPattern(matcher, data);
	}

	/**
	 * Checks this prohibition against a specific player and GameObject, and
	 * tells you which characteristics of that GameObject can't be cast by that
	 * player.
	 * 
	 * @return A list of indices into [thing]'s characteristics; each index
	 * indicates a characteristics that can't be cast.
	 */
	public java.util.List<Integer> evaluate(GameState state, Player player, GameObject thing)
	{
		GameObject sourceObject = state.<GameObject>get(this.sourceID);
		if(!this.players.match(state, sourceObject, new Set(player)))
			return java.util.Collections.emptyList();

		java.util.List<Integer> ret = new java.util.LinkedList<>();
		Characteristics[] characteristics = thing.getCharacteristics();
		for(int i = 0; i < characteristics.length; i++)
			if(this.things.match(state, sourceObject, new Set(characteristics[i])))
				ret.add(i);

		return ret;
	}

	void setSource(Identified source)
	{
		this.sourceID = source.ID;
	}

	/**
	 * Given the play prohibitions in the given state, which sides of this
	 * object are still castable?
	 * 
	 * This doesn't take into account permissions! Or legal targets! This only
	 * returns the list of characteristics indices that the state's prohibitions
	 * don't keep the player from casting.
	 */
	public static java.util.List<Integer> getAllowed(GameState state, Player player, GameObject thing)
	{
		java.util.List<Integer> canCast = new java.util.ArrayList<>();
		Characteristics[] characteristics = thing.getCharacteristics();
		for(int i = 0; i < characteristics.length; i++)
			canCast.add(i);

		for(PlayProhibition prohibit: state.playProhibitions)
		{
			canCast.removeAll(prohibit.evaluate(state, player, thing));
			if(canCast.isEmpty())
				return java.util.Collections.emptyList();
		}

		return canCast;
	}
}

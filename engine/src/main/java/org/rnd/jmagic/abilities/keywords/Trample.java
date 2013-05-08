package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

@Name("Trample")
public final class Trample extends Keyword
{
	// 702.17. Trample
	//
	// 702.17a Trample is a static ability that modifies the rules for assigning
	// an attacking creature's combat damage. The ability has no effect when a
	// creature with trample is blocking or is dealing noncombat damage. (See
	// rule 510, "Combat Damage Step.")
	//
	// 702.17b The controller of an attacking creature with trample first
	// assigns damage to the creature(s) blocking it. Once all those blocking
	// creatures are assigned lethal damage, any remaining damage is assigned as
	// its controller chooses among those blocking creatures and the player or
	// planeswalker the creature is attacking. When checking for assigned lethal
	// damage, take into account damage already marked on the creature and
	// damage from other creatures that's being assigned during the same combat
	// damage step, but not any abilities or effects that might change the
	// amount of damage that's actually dealt. The attacking creature's
	// controller need not assign lethal damage to all those blocking creatures
	// but in that case can't assign any damage to the player or planeswalker
	// it's attacking.
	//
	// 702.17c If an attacking creature with trample is blocked, but there are
	// no creatures blocking it when damage is assigned, all its damage is
	// assigned to the player or planeswalker it's attacking.
	//
	// 702.17d If a creature with trample is attacking a planeswalker, none of
	// its combat damage can be assigned to the defending player, even if that
	// planeswalker has been removed from combat or the damage the attacking
	// creature could assign to that planeswalker exceeds its loyalty.
	//
	// 702.17e Multiple instances of trample on the same creature are redundant.

	public Trample(GameState state)
	{
		super(state, "Trample");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.LinkedList<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new TrampleStatic(this.state));
		return ret;
	}

	public static final class TrampleStatic extends StaticAbility
	{
		public TrampleStatic(GameState state)
		{
			super(state, "If this creature would assign enough damage to its blockers to destroy them, you may have it assign the rest of its damage to defending player or planeswalker.");
		}
	}
}

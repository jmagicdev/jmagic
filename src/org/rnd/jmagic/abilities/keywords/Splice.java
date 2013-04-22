package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;

/**
 * 702.44. Splice
 * 
 * 702.44a Splice is a static ability that functions while a card is in your
 * hand. "Splice onto [subtype] [cost]" means "You may reveal this card from
 * your hand as you cast a [subtype] spell. If you do, copy this card's text box
 * onto that spell and pay [cost] as an additional cost to cast that spell."
 * Paying a card's splice cost follows the rules for paying additional costs in
 * rules 601.2b and 601.2e-g.
 * 
 * The rest of the relevant rules are quoted in
 * {@link EventType#CAST_SPELL_OR_ACTIVATE_ABILITY}.
 */
public final class Splice extends Keyword
{
	public static final String COST_TYPE = "Splice";

	private final SubType ontoWhat;
	private final CostCollection cost;

	private Splice(GameState state, SubType ontoWhat, CostCollection cost, String name)
	{
		super(state, name);
		this.ontoWhat = ontoWhat;
		this.cost = cost;
	}

	public static Splice ontoArcane(GameState state, String manaCost)
	{
		return new Splice(state, SubType.ARCANE, new CostCollection(COST_TYPE, manaCost), "Splice onto Arcane " + manaCost);
	}

	public static Splice ontoArcane(GameState state, EventFactory cost)
	{
		return new Splice(state, SubType.ARCANE, new CostCollection(COST_TYPE, cost), "Splice onto Arcane\u2014" + cost);
	}

	@Override
	public Splice create(Game game)
	{
		return new Splice(game.physicalState, this.ontoWhat, this.cost, this.getName());
	}

	@Override
	public boolean isSplice()
	{
		return true;
	}

	public SubType getSubType()
	{
		return this.ontoWhat;
	}

	public CostCollection getCost()
	{
		return this.cost;
	}

	public static final class SpliceAbility extends StaticAbility
	{
		private final SubType ontoWhat;

		public SpliceAbility(GameState state, SubType ontoWhat)
		{
			super(state, "As you cast " + org.rnd.util.NumberNames.getAOrAn(1, ontoWhat.toString()) + " " + ontoWhat + " spell, you may reveal this card from your hand and pay its splice cost. If you do, add this card's effects to that spell.");
			this.ontoWhat = ontoWhat;
		}

		@Override
		public SpliceAbility create(Game game)
		{
			return new SpliceAbility(game.physicalState, this.ontoWhat);
		}
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new SpliceAbility(this.state, this.ontoWhat));
	}
}

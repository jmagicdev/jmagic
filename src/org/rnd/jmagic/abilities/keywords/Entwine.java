package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.39. Entwine
 * 
 * 702.39a Entwine is a static ability of modal spells (see rule 700.2) that
 * functions while the spell is on the stack. "Entwine [cost]" means "You may
 * choose all modes of this spell instead of just one. If you do, you pay an
 * additional [cost]." Using the entwine ability follows the rules for choosing
 * modes and paying additional costs in rules 601.2b and 601.2e-g.
 * 
 * 702.39b If the entwine cost was paid, follow the text of each of the modes in
 * the order written on the card when the spell resolves.
 */
public final class Entwine extends Keyword
{
	public static final String TYPE = "Entwine";

	private final CostCollection costCollection;
	private final String costName;

	public Entwine(GameState state, String manaCost)
	{
		this(state, new CostCollection(TYPE, manaCost), manaCost);
	}

	public Entwine(GameState state, CostCollection costs, String costName)
	{
		super(state, "Entwine" + (costs.events.isEmpty() ? " " : "\u2014") + costName);

		this.costCollection = costs;
		this.costName = costName;
	}

	@Override
	public Entwine create(Game game)
	{
		return new Entwine(game.physicalState, this.costCollection, this.costName);
	}

	@Override
	public java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new EntwineIncreaseModeChoices(this.state));
		ret.add(new EntwineAdditionalCost(this.state, this.costCollection, this.costName));
		return ret;
	}

	public static final class EntwineIncreaseModeChoices extends StaticAbility
	{
		public EntwineIncreaseModeChoices(GameState state)
		{
			super(state, "You may choose all modes of this spell instead of just one.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_CHOOSABLE_NUMBER_OF_MODES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Empty.instance());
			this.addEffectPart(part);

			// This always applies
			this.canApply = NonEmpty.instance();
		}
	}

	public static final class EntwineAdditionalCost extends StaticAbility
	{
		private final CostCollection costCollection;
		private final String costName;

		public EntwineAdditionalCost(GameState state, CostCollection costs, String costName)
		{
			super(state, "If you choose all the modes of this spell, you pay an additional " + costName + ".");

			this.costCollection = costs;
			this.costName = costName;

			Set newCosts = new Set(costs.manaCost);
			newCosts.addAll(costs.events);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(newCosts));
			this.addEffectPart(part);

			// This only applies if you've chosen all of its modes
			this.canApply = Not.instance(RelativeComplement.instance(ModesOf.instance(This.instance()), SelectedModesOf.instance(This.instance())));
		}

		@Override
		public EntwineAdditionalCost create(Game game)
		{
			return new EntwineAdditionalCost(game.physicalState, this.costCollection, this.costName);
		}
	}
}

package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Bestow extends Keyword
{
	public static final String BESTOW_COST = "Bestow cost";

	protected String bestowCost;

	public Bestow(GameState state, String manaCost)
	{
		super(state, "Bestow " + manaCost);
		this.bestowCost = manaCost;
	}

	@Override
	public Bestow create(Game game)
	{
		return new Bestow(game.physicalState, this.bestowCost);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<>();
		ret.add(new BestowCastAbility(this.state, this));
		ret.add(new BecomeAuraAbility(this.state));
		return ret;
	}

	public static final class BecomeAuraAbility extends StaticAbility
	{
		public BecomeAuraAbility(GameState state)
		{
			super(state, "If you chose to pay this spell's bestow cost, it becomes an Aura enchantment and gains enchant creature. These effects last until one of two things happens: this spell has an illegal target as it resolves or the permanent this spell becomes, becomes unattached.");

			ContinuousEffect.Part auraPart = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			auraPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			auraPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ENCHANTMENT, SubType.AURA));

			ContinuousEffect.Part enchantCreaturePart = addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Enchant.Creature.class);

			this.addEffectPart(auraPart, enchantCreaturePart);

			// These effects last until either (1) the target is illegal as it
			// resolves, or (2) the permanent isn't attached to anything. This
			// means that as long as it's on the stack up until the point it
			// resolves, the effect is active. Then, when it starts to resolve,
			// we need to check to make sure the targets are legal; if not, the
			// effect ends. Then, after it's on the battlefield, we need to
			// check to make sure it's attached to something (which, if it
			// resolved as a creature, will conveniently be never).

			// This makes our desired canApply generator as follows:
			// this was bestowed AND (
			// .. this is on the stack but not resolving OR
			// .. this is resolving with a legal target OR
			// .. this is on the battlefield attached)

			SetGenerator thisIsResolving = Intersect.instance(This.instance(), Resolving.instance());
			SetGenerator notResolvedYet = Both.instance(THIS_IS_ON_THE_STACK, Not.instance(thisIsResolving));

			SetGenerator hasLegalTarget = TargetsAreLegal.instance(AllChosenTargetsOf.instance(This.instance()));
			SetGenerator resolvingWithLegalTarget = Both.instance(thisIsResolving, hasLegalTarget);

			SetGenerator attachedToSomething = EnchantedBy.instance(This.instance());

			SetGenerator criteria = Union.instance(notResolvedYet, resolvingWithLegalTarget, attachedToSomething);
			this.canApply = Both.instance(WasBestowed.instance(This.instance()), criteria);
		}
	}

	public static final class BestowCastAbility extends StaticAbility
	{
		private Bestow parent;

		public BestowCastAbility(GameState state, Bestow parent)
		{
			super(state, "You may cast this spell by paying its bestow cost rather than its mana cost.");
			this.parent = parent;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(BESTOW_COST, parent.bestowCost)));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}

		@Override
		public BestowCastAbility create(Game game)
		{
			return new BestowCastAbility(game.physicalState, this.parent);
		}
	}
}

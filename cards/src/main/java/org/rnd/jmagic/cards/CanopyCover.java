package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Canopy Cover")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class CanopyCover extends Card
{
	public static final class FlyingButNotReally extends StaticAbility
	{
		public FlyingButNotReally(GameState state)
		{
			super(state, "Enchanted creature can't be blocked except by creatures with flying or reach.");

			SetGenerator hasFlyingOrReach = Union.instance(HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Reach.class));
			SetGenerator notBlockingWithFlyingOrReach = RelativeComplement.instance(Blocking.instance(EnchantedBy.instance(This.instance())), hasFlyingOrReach);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrReach));
			this.addEffectPart(part);
		}
	}

	public static final class GrantTrollShroud extends StaticAbility
	{
		public GrantTrollShroud(GameState state)
		{
			super(state, "Enchanted creature can't be the target of spells or abilities your opponents control.");
			SetGenerator stuffOpponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(new SimpleSetPattern(stuffOpponentsControl)));
			this.addEffectPart(part);
		}
	}

	public CanopyCover(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't be blocked except by creatures with flying
		// or reach.
		this.addAbility(new FlyingButNotReally(state));

		// Enchanted creature can't be the target of spells or abilities your
		// opponents control.
		this.addAbility(new GrantTrollShroud(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glaring Spotlight")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({})
public final class GlaringSpotlight extends Card
{
	public static final class GlaringSpotlightAbility0 extends StaticAbility
	{
		public GlaringSpotlightAbility0(GameState state)
		{
			super(state, "Creatures your opponents control with hexproof can be the targets of spells and abilities you control as though they didn't have hexproof.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.EXEMPT_FROM_ABILITY);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(OpponentsOf.instance(You.instance()))));
			part.parameters.put(ContinuousEffectType.Parameter.EXEMPT, ControlledBy.instance(You.instance(), Stack.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Hexproof.class));
			this.addEffectPart(part);
		}
	}

	public static final class GlaringSpotlightAbility1 extends ActivatedAbility
	{
		public GlaringSpotlightAbility1(GameState state)
		{
			super(state, "(3), Sacrifice Glaring Spotlight: Creatures you control gain hexproof until end of turn and are unblockable this turn.");
			this.setManaCost(new ManaPool("(3)"));
			this.addCost(sacrificeThis("Glaring Spotlight"));

			this.addEffect(createFloatingEffect("Creatures you control gain hexproof until end of turn and are unblockable this turn.", addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Hexproof.class), unblockable(CREATURES_YOU_CONTROL)));
		}
	}

	public GlaringSpotlight(GameState state)
	{
		super(state);

		// Creatures your opponents control with hexproof can be the targets of
		// spells and abilities you control as though they didn't have hexproof.
		this.addAbility(new GlaringSpotlightAbility0(state));

		// (3), Sacrifice Glaring Spotlight: Creatures you control gain hexproof
		// until end of turn and are unblockable this turn.
		this.addAbility(new GlaringSpotlightAbility1(state));
	}
}

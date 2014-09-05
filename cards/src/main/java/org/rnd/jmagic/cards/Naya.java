package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.gameTypes.*;

@Name("Naya")
@Types({Type.PLANE})
@SubTypes({SubType.ALARA})
@Printings({@Printings.Printed(ex = org.rnd.jmagic.expansions.Planechase.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Naya extends Card
{
	public static final class EverBloom extends org.rnd.jmagic.abilities.PlayExtraLands
	{
		public EverBloom(GameState state)
		{
			super(state, null, "You may play any number of lands on each of your turns.");

			setCanApply(PlanechaseGameRules.staticAbilityCanApply);
		}
	}

	public static final class TerraPump extends EventTriggeredAbility
	{
		public TerraPump(GameState state)
		{
			super(state, "Whenever you roll (C), target red, green, or white creature you control gets +1/+1 until end of turn for each land you control.");

			this.addPattern(PlanechaseGameRules.wheneverYouRollChaos());

			Target target = this.addTarget(Intersect.instance(HasColor.instance(Color.RED, Color.GREEN, Color.WHITE), CreaturePermanents.instance()), "target red, green, or white creature");

			SetGenerator pump = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), LandPermanents.instance()));

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), pump, pump, "Target red, green, or white creature you control gets +1/+1 until end of turn for each land you control."));

			this.canTrigger = PlanechaseGameRules.triggeredAbilityCanTrigger;
		}
	}

	public Naya(GameState state)
	{
		super(state);

		this.addAbility(new EverBloom(state));

		this.addAbility(new TerraPump(state));
	}
}

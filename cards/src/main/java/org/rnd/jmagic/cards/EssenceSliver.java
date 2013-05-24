package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Essence Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class EssenceSliver extends Card
{
	public static final class SliverLink extends EventTriggeredAbility
	{
		public SliverLink(GameState state)
		{
			super(state, "Whenever a Sliver deals damage, its controller gains that much life.");

			this.addPattern(whenDealsDamage(HasSubType.instance(SubType.SLIVER)));

			SetGenerator triggerDamage = TriggerDamage.instance(This.instance());
			SetGenerator itsController = ControllerOf.instance(SourceOfDamage.instance(triggerDamage));
			SetGenerator thatMuch = Count.instance(triggerDamage);

			this.addEffect(gainLife(itsController, thatMuch, "Its controller gains that much life."));
		}
	}

	public EssenceSliver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever a Sliver deals damage, its controller gains that much life.
		this.addAbility(new SliverLink(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Night Revelers")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class NightRevelers extends Card
{
	public static final class NightRevelersAbility0 extends StaticAbility
	{
		public NightRevelersAbility0(GameState state)
		{
			super(state, "Night Revelers has haste as long as an opponent controls a Human.");

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Haste.class));

			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), HasSubType.instance(SubType.HUMAN)));
		}
	}

	public NightRevelers(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Night Revelers has haste as long as an opponent controls a Human.
		this.addAbility(new NightRevelersAbility0(state));
	}
}

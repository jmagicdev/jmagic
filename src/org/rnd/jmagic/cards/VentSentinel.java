package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vent Sentinel")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VentSentinel extends Card
{
	public static final class VentSentinelAbility1 extends ActivatedAbility
	{
		public VentSentinelAbility1(GameState state)
		{
			super(state, "(1)(R), (T): Vent Sentinel deals damage to target player equal to the number of creatures with defender you control.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;

			SetGenerator amount = Count.instance(Intersect.instance(CREATURES_YOU_CONTROL, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class)));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(amount, target, "Vent Sentinel deals damage to target player equal to the number of creatures with defender you control."));
		}
	}

	public VentSentinel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (1)(R), (T): Vent Sentinel deals damage to target player equal to the
		// number of creatures with defender you control.
		this.addAbility(new VentSentinelAbility1(state));
	}
}

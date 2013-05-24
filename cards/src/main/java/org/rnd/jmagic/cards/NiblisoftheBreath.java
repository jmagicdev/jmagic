package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Niblis of the Breath")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class NiblisoftheBreath extends Card
{
	public static final class NiblisoftheBreathAbility1 extends ActivatedAbility
	{
		public NiblisoftheBreathAbility1(GameState state)
		{
			super(state, "(U), (T): You may tap or untap target creature.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(tapOrUntap(target, "Tap or untap target creature.")));
		}
	}

	public NiblisoftheBreath(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (U), (T): You may tap or untap target creature.
		this.addAbility(new NiblisoftheBreathAbility1(state));
	}
}

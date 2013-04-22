package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bottle Gnomes")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GNOME})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BottleGnomes extends Card
{
	public static final class SacForLife extends ActivatedAbility
	{
		public SacForLife(GameState state)
		{
			super(state, "Sacrifice Bottle Gnomes: You gain 3 life.");

			this.addCost(sacrificeThis("Bottle Gnomes"));

			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public BottleGnomes(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new SacForLife(state));
	}
}

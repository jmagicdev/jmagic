package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Intrepid Hero")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class IntrepidHero extends Card
{
	public static final class IntrepidHeroAbility0 extends ActivatedAbility
	{
		public IntrepidHeroAbility0(GameState state)
		{
			super(state, "(T): Destroy target creature with power 4 or greater.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(4, null))), "target creature with power 4 or greater"));
			this.addEffect(destroy(target, "Destroy target creature with power 4 or greater."));
		}
	}

	public IntrepidHero(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Destroy target creature with power 4 or greater.
		this.addAbility(new IntrepidHeroAbility0(state));
	}
}

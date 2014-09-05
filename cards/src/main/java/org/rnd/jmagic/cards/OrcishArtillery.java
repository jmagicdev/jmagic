package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Orcish Artillery")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class OrcishArtillery extends Card
{
	public static final class Strike extends ActivatedAbility
	{
		public Strike(GameState state)
		{
			super(state, "(T): Orcish Artillery deals 2 damage to target creature or player and 3 damage to you.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			// TODO : Simultaneous etc etc
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Orcish Artillery deals 2 damage to target creature or player"));
			this.addEffect(permanentDealDamage(3, You.instance(), "and 3 damage to you."));
		}
	}

	public OrcishArtillery(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new Strike(state));
	}
}

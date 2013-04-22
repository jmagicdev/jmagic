package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Shivan Hellkite")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ShivanHellkite extends Card
{
	public static final class ShivanPing extends ActivatedAbility
	{
		public ShivanPing(GameState state)
		{
			super(state, "(1)(R): Shivan Hellkite deals 1 damage to target creature or player.");

			this.setManaCost(new ManaPool("1R"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Shivan Hellkite deals 1 damage to target creature or player."));
		}
	}

	public ShivanHellkite(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new ShivanPing(state));
	}
}

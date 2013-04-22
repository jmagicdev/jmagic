package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Goblin Arsonist")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinArsonist extends Card
{
	public static final class Arson extends EventTriggeredAbility
	{
		public Arson(GameState state)
		{
			super(state, "When Goblin Arsonist dies, you may have it deal 1 damage to target creature or player.");

			this.addPattern(whenThisDies());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(youMay(permanentDealDamage(1, targetedBy(target), "Deal 1 damage to target creature or player."), "You may have it deal 1 damage to target creature or player."));
		}
	}

	public GoblinArsonist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Goblin Arsonist is put into a graveyard from the battlefield,
		// you may have it deal 1 damage to target creature or player.
		this.addAbility(new Arson(state));
	}
}

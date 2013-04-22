package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Neurok Commando")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class NeurokCommando extends Card
{
	public static final class NeurokCommandoAbility1 extends EventTriggeredAbility
	{
		public NeurokCommandoAbility1(GameState state)
		{
			super(state, "Whenever Neurok Commando deals combat damage to a player, you may draw a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(youMay(drawACard(), "You may draw a card."));
		}
	}

	public NeurokCommando(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Shroud
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		// Whenever Neurok Commando deals combat damage to a player, you may
		// draw a card.
		this.addAbility(new NeurokCommandoAbility1(state));
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Niv-Mizzet, Dracogenius")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.DRAGON})
@ManaCost("2UURR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class NivMizzetDracogenius extends Card
{
	public static final class NivMizzetDracogeniusAbility1 extends EventTriggeredAbility
	{
		public NivMizzetDracogeniusAbility1(GameState state)
		{
			super(state, "Whenever Niv-Mizzet, Dracogenius deals damage to a player, you may draw a card.");

			this.addPattern(whenDealsDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public static final class NivMizzetDracogeniusAbility2 extends ActivatedAbility
	{
		public NivMizzetDracogeniusAbility2(GameState state)
		{
			super(state, "(U)(R): Niv-Mizzet, Dracogenius deals 1 damage to target creature or player.");
			this.setManaCost(new ManaPool("(U)(R)"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Niv-Mizzet, Dracogenius deals 1 damage to target creature or player."));
		}
	}

	public NivMizzetDracogenius(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Niv-Mizzet, Dracogenius deals damage to a player, you may
		// draw a card.
		this.addAbility(new NivMizzetDracogeniusAbility1(state));

		// (U)(R): Niv-Mizzet, Dracogenius deals 1 damage to target creature or
		// player.
		this.addAbility(new NivMizzetDracogeniusAbility2(state));
	}
}

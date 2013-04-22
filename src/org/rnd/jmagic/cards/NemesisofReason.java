package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nemesis of Reason")
@Types({Type.CREATURE})
@SubTypes({SubType.LEVIATHAN, SubType.HORROR})
@ManaCost("3UB")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class NemesisofReason extends Card
{
	public static final class EatThatReason extends EventTriggeredAbility
	{
		public EatThatReason(GameState state)
		{
			super(state, "Whenever Nemesis of Reason attacks, defending player puts the top ten cards of his or her library into his or her graveyard.");

			this.addPattern(whenThisAttacks());

			this.addEffect(millCards(IsAttacked.instance(ABILITY_SOURCE_OF_THIS), 10, "Defending player puts the top ten cards of his or her library into his or her graveyard"));
		}
	}

	public NemesisofReason(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(7);

		this.addAbility(new EatThatReason(state));
	}
}

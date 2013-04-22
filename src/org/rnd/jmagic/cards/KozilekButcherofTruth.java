package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kozilek, Butcher of Truth")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(10)")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.MYTHIC)})
@ColorIdentity({})
public final class KozilekButcherofTruth extends Card
{
	public static final class WhenYouCastDrawFourCards extends EventTriggeredAbility
	{
		public WhenYouCastDrawFourCards(GameState state)
		{
			super(state, "When you cast Kozilek, Butcher of Truth, draw four cards.");
			this.triggersFromStack();

			this.addPattern(whenYouCastThisSpell());

			this.addEffect(drawCards(You.instance(), 4, "Draw four cards"));
		}
	}

	public KozilekButcherofTruth(GameState state)
	{
		super(state);

		this.setPower(12);
		this.setToughness(12);

		// When you cast Kozilek, Butcher of Truth, draw four cards.
		this.addAbility(new WhenYouCastDrawFourCards(state));

		// Annihilator 4
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 4));

		// When Kozilek is put into a graveyard from anywhere, its owner
		// shuffles his or her graveyard into his or her library.
		this.addAbility(new org.rnd.jmagic.abilities.EldraziReshuffle(state, this.getName()));
	}
}

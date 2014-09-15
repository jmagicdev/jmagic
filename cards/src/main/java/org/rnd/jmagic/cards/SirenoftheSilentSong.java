package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siren of the Silent Song")
@Types({Type.CREATURE})
@SubTypes({SubType.SIREN, SubType.ZOMBIE})
@ManaCost("1UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SirenoftheSilentSong extends Card
{
	public static final class SirenoftheSilentSongAbility1 extends EventTriggeredAbility
	{
		public SirenoftheSilentSongAbility1(GameState state)
		{
			super(state, "Whenever Siren of the Silent Song becomes untapped, each opponent discards a card, then puts the top card of his or her library into his or her graveyard.");
			this.addPattern(inspired());

			this.addEffect(discardCards(OpponentsOf.instance(You.instance()), 1, "Each opponent discards a card,"));
			this.addEffect(millCards(OpponentsOf.instance(You.instance()), 1, "Then puts the top card of his or her library into his or her graveyard."));
		}
	}

	public SirenoftheSilentSong(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Inspired \u2014 Whenever Siren of the Silent Song becomes untapped,
		// each opponent discards a card, then puts the top card of his or her
		// library into his or her graveyard.
		this.addAbility(new SirenoftheSilentSongAbility1(state));
	}
}

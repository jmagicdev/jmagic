package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience.CreateTokensFactory;
import org.rnd.jmagic.engine.*;

@Name("Hornet Queen")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("4GGG")
@ColorIdentity({Color.GREEN})
public final class HornetQueen extends Card
{
	public static final class HornetQueenAbility2 extends EventTriggeredAbility
	{
		public HornetQueenAbility2(GameState state)
		{
			super(state, "When Hornet Queen enters the battlefield, put four 1/1 green Insect creature tokens with flying and deathtouch onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory insects = new CreateTokensFactory(4, 1, 1, "Put four 1/1 green Insect creature tokens with flying and deathtouch onto the battlefield.");
			insects.setColors(Color.GREEN);
			insects.setSubTypes(SubType.INSECT);
			insects.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			insects.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(insects.getEventFactory());
		}
	}

	public HornetQueen(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// When Hornet Queen enters the battlefield, put four 1/1 green Insect
		// creature tokens with flying and deathtouch onto the battlefield.
		this.addAbility(new HornetQueenAbility2(state));
	}
}

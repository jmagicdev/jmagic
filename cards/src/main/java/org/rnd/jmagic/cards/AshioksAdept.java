package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ashiok's Adept")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class AshioksAdept extends Card
{
	public static final class AshioksAdeptAbility0 extends EventTriggeredAbility
	{
		public AshioksAdeptAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell that targets Ashiok's Adept, each opponent discards a card.");
			this.addPattern(heroic());

			this.addEffect(discardCards(OpponentsOf.instance(You.instance()), 1, "Each opponent discards a card."));
		}
	}

	public AshioksAdept(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Heroic \u2014 Whenever you cast a spell that targets Ashiok's Adept,
		// each opponent discards a card.
		this.addAbility(new AshioksAdeptAbility0(state));
	}
}

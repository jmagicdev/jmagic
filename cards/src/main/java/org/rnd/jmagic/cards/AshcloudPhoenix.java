package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ashcloud Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class AshcloudPhoenix extends Card
{
	public static final class AshcloudPhoenixAbility1 extends EventTriggeredAbility
	{
		public AshcloudPhoenixAbility1(GameState state)
		{
			super(state, "When Ashcloud Phoenix dies, return it to the battlefield face down.");
			this.addPattern(whenThisDies());

			EventFactory comeBack = new EventFactory(EventType.PUT_IN_CONTROLLED_ZONE, "Return it to the battlefield face down.");
			comeBack.parameters.put(EventType.Parameter.CAUSE, This.instance());
			comeBack.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			comeBack.parameters.put(EventType.Parameter.ZONE, Battlefield.instance());
			comeBack.parameters.put(EventType.Parameter.OBJECT, FutureSelf.instance(ABILITY_SOURCE_OF_THIS));
			comeBack.parameters.put(EventType.Parameter.FACE_DOWN, Identity.instance(FaceDownCard.class));
			this.addEffect(comeBack);
		}
	}

	public static final class AshcloudPhoenixAbility3 extends EventTriggeredAbility
	{
		public AshcloudPhoenixAbility3(GameState state)
		{
			super(state, "When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.");
			this.addPattern(whenThisIsTurnedFaceUp());
			this.addEffect(permanentDealDamage(2, Players.instance(), "Ashcloud Phoenix deals 2 damage to each player."));
		}
	}

	public AshcloudPhoenix(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Ashcloud Phoenix dies, return it to the battlefield face down.
		this.addAbility(new AshcloudPhoenixAbility1(state));

		// Morph (4)(R)(R) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(4)(R)(R)"));

		// When Ashcloud Phoenix is turned face up, it deals 2 damage to each
		// player.
		this.addAbility(new AshcloudPhoenixAbility3(state));
	}
}

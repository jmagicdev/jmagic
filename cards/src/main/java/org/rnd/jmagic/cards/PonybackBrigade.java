package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Ponyback Brigade")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("3RWB")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLACK})
public final class PonybackBrigade extends Card
{
	public static final class PonybackBrigadeAbility0 extends EventTriggeredAbility
	{
		public PonybackBrigadeAbility0(GameState state)
		{
			super(state, "When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisIsTurnedFaceUp());

			CreateTokensFactory goblins = new CreateTokensFactory(3, 1, 1, "Put three 1/1 red Goblin creature tokens onto the battlefield.");
			goblins.setColors(Color.RED);
			goblins.setSubTypes(SubType.GOBLIN);
			this.addEffect(goblins.getEventFactory());
		}
	}

	public PonybackBrigade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Ponyback Brigade enters the battlefield or is turned face up,
		// put three 1/1 red Goblin creature tokens onto the battlefield.
		this.addAbility(new PonybackBrigadeAbility0(state));

		// Morph (2)(R)(W)(B) (You may cast this card face down as a 2/2
		// creature for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(R)(W)(B)"));
	}
}

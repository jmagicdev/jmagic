package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necromancer's Assistant")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class NecromancersAssistant extends Card
{
	public static final class NecromancersAssistantAbility0 extends EventTriggeredAbility
	{
		public NecromancersAssistantAbility0(GameState state)
		{
			super(state, "When Necromancer's Assistant enters the battlefield, put the top three cards of your library into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(millCards(You.instance(), 3, "Put the top three cards of your library into your graveyard."));
		}
	}

	public NecromancersAssistant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// When Necromancer's Assistant enters the battlefield, put the top
		// three cards of your library into your graveyard.
		this.addAbility(new NecromancersAssistantAbility0(state));
	}
}

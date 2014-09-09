package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Architects of Will")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class ArchitectsofWill extends Card
{
	public static final class ETBIndex extends EventTriggeredAbility
	{
		public ETBIndex(GameState state)
		{
			super(state, "When Architects of Will enters the battlefield, look at the top three cards of target player's library, then put them back in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			// Look at the top three cards of your library, then put them back
			// in any order.
			Target target = this.addTarget(Players.instance(), "target player");
			EventType.ParameterMap lookParameters = new EventType.ParameterMap();
			lookParameters.put(EventType.Parameter.CAUSE, This.instance());
			lookParameters.put(EventType.Parameter.PLAYER, You.instance());
			lookParameters.put(EventType.Parameter.TARGET, targetedBy(target));
			lookParameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(new EventFactory(EventType.LOOK_AND_PUT_BACK, lookParameters, "Look at the top three cards of target player's library, then put them back in any order."));
		}
	}

	public ArchitectsofWill(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// When Architects of Will enters the battlefield, look at the top three
		// cards of target player's library, then put them back in any order.
		this.addAbility(new ETBIndex(state));

		// Cycling ((u/b)) (((u/b)), Discard this card: Draw a card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(UB)"));
	}
}

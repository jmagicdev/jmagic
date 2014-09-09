package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Academy Researchers")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class AcademyResearchers extends Card
{
	public static final class InstaChant extends EventTriggeredAbility
	{
		public InstaChant(GameState state)
		{
			super(state, "When Academy Researchers enters the battlefield, you may put an Aura card from your hand onto the battlefield attached to Academy Researchers.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator auras = HasSubType.instance(SubType.AURA);
			SetGenerator aurasInHand = Intersect.instance(auras, InZone.instance(HandOf.instance(You.instance())));

			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory attachEvent = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE_ATTACHED_TO, "Put an Aura card from your hand onto the battlefield attached to Academy Researchers");
			attachEvent.parameters.put(EventType.Parameter.CAUSE, This.instance());
			attachEvent.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			attachEvent.parameters.put(EventType.Parameter.OBJECT, aurasInHand);
			attachEvent.parameters.put(EventType.Parameter.TARGET, thisCard);

			this.addEffect(youMay(attachEvent, "You may put an Aura card from your hand onto the battlefield attached to Academy Researchers."));
		}
	}

	public AcademyResearchers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new InstaChant(state));
	}
}

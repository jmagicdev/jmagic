package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Guiding Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.ANGEL})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = Visions.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class GuidingSpirit extends Card
{
	public static final class GuidingSpiritAbility1 extends ActivatedAbility
	{
		public GuidingSpiritAbility1(GameState state)
		{
			super(state, "(T): If the top card of target player's graveyard is a creature card, put that card on top of that player's library.");
			this.costsTap = true;

			Target t = this.addTarget(Players.instance(), "target player");

			SetGenerator topCardOfYard = TopCards.instance(1, GraveyardOf.instance(targetedBy(t)));
			SetGenerator creatureOnYard = Intersect.instance(HasType.instance(Type.CREATURE), topCardOfYard);

			EventFactory putOnTop = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put that card on top of that player's library");
			putOnTop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOnTop.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			putOnTop.parameters.put(EventType.Parameter.OBJECT, topCardOfYard);

			EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If the top card of target player's graveyard is a creature card, put that card on top of that player's library.");
			effect.parameters.put(EventType.Parameter.IF, creatureOnYard);
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(putOnTop));
			this.addEffect(effect);
		}
	}

	public GuidingSpirit(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (T): If the top card of target player's graveyard is a creature card,
		// put that card on top of that player's library.
		this.addAbility(new GuidingSpiritAbility1(state));
	}
}

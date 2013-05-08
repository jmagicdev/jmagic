package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angel of Flight Alabaster")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AngelofFlightAlabaster extends Card
{
	public static final class AngelofFlightAlabasterAbility1 extends EventTriggeredAbility
	{
		public AngelofFlightAlabasterAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, return target Spirit card from your graveyard to your hand.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator you = You.instance();
			SetGenerator yourGraveyard = GraveyardOf.instance(you);
			SetGenerator inYourGraveyard = InZone.instance(yourGraveyard);
			SetGenerator spirits = HasSubType.instance(SubType.SPIRIT);
			SetGenerator spiritCardInYourGraveyard = Intersect.instance(spirits, Cards.instance(), inYourGraveyard);
			SetGenerator target = targetedBy(this.addTarget(spiritCardInYourGraveyard, "target Spirit card from your graveyard"));

			EventFactory returnToHand = new EventFactory(EventType.MOVE_OBJECTS, "Return target Spirit card from your graveyard to your hand.");
			returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToHand.parameters.put(EventType.Parameter.TO, HandOf.instance(you));
			returnToHand.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(returnToHand);
		}
	}

	public AngelofFlightAlabaster(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, return target Spirit card from your
		// graveyard to your hand.
		this.addAbility(new AngelofFlightAlabasterAbility1(state));
	}
}

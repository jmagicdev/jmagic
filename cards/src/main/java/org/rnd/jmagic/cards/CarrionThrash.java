package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Carrion Thrash")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VIASHINO})
@ManaCost("2BRG")
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
public final class CarrionThrash extends Card
{
	public static final class CarrionThrashAbility0 extends EventTriggeredAbility
	{
		public CarrionThrashAbility0(GameState state)
		{
			super(state, "When Carrion Thrash dies, you may pay (2). If you do, return another target creature card from your graveyard to your hand.");
			this.addPattern(whenThisDies());

			EventFactory mayPayTwo = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2)");
			mayPayTwo.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPayTwo.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			mayPayTwo.parameters.put(EventType.Parameter.PLAYER, You.instance());

			SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
			SetGenerator creatureCardFromYourGraveyard = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), InZone.instance(yourGraveyard));
			SetGenerator anotherCreatureCardFromYourGraveyard = RelativeComplement.instance(creatureCardFromYourGraveyard, ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreatureCardFromYourGraveyard, "another target creature card from your graveyard"));

			EventFactory returnToYourHand = new EventFactory(EventType.MOVE_OBJECTS, "Return another target creature card from your graveyard to your hand");
			returnToYourHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToYourHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			returnToYourHand.parameters.put(EventType.Parameter.OBJECT, target);

			EventFactory ifThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2). If you do, return another target creature card from your graveyard to your hand.");
			ifThen.parameters.put(EventType.Parameter.IF, Identity.instance(mayPayTwo));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(returnToYourHand));
			this.addEffect(ifThen);
		}
	}

	public CarrionThrash(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Carrion Thrash is put into a graveyard from the battlefield, you
		// may pay (2). If you do, return another target creature card from your
		// graveyard to your hand.
		this.addAbility(new CarrionThrashAbility0(state));
	}
}

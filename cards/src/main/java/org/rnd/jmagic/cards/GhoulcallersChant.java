package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghoulcaller's Chant")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GhoulcallersChant extends Card
{
	public GhoulcallersChant(GameState state)
	{
		super(state);
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 1)));

		SetGenerator graveyard = GraveyardOf.instance(You.instance());
		SetGenerator hand = HandOf.instance(You.instance());
		SetGenerator inYourYard = InZone.instance(graveyard);

		// Choose one \u2014 Return target creature card from your graveyard to
		// your hand; or return two target Zombie cards from your graveyard to
		// your hand.

		{
			SetGenerator creatureCardsInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), inYourYard);
			SetGenerator target = targetedBy(this.addTarget(1, creatureCardsInYourYard, "target creature card from your graveyard"));
			EventFactory retOne = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand.");
			retOne.parameters.put(EventType.Parameter.CAUSE, This.instance());
			retOne.parameters.put(EventType.Parameter.TO, hand);
			retOne.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(1, retOne);
		}

		{
			SetGenerator zombieCardsInYourYard = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), Cards.instance(), inYourYard);
			Target zombies = this.addTarget(2, zombieCardsInYourYard, "two target Zombie cards from your graveyard");
			zombies.setNumber(2, 2);

			SetGenerator target = targetedBy(zombies);
			EventFactory retTwo = new EventFactory(EventType.MOVE_OBJECTS, "Return two target Zombie cards from your graveyard to your hand.");
			retTwo.parameters.put(EventType.Parameter.CAUSE, This.instance());
			retTwo.parameters.put(EventType.Parameter.TO, hand);
			retTwo.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(2, retTwo);
		}
	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Counterlash")
@Types({Type.INSTANT})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class Counterlash extends Card
{
	public Counterlash(GameState state)
	{
		super(state);

		// Counter target spell. You may cast a nonland card in your hand that
		// shares a card type with that spell without paying its mana cost.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));
		this.addEffect(counter(target, "Counter target spell."));

		SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
		SetGenerator sharesType = HasType.instance(TypesOf.instance(target));
		SetGenerator land = HasType.instance(Type.LAND);
		SetGenerator castable = RelativeComplement.instance(Intersect.instance(inYourHand, sharesType), land);

		EventFactory castLotsOfShit = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "You may cast a nonland card in your hand that shares a card type with that spell without paying its mana cost.");
		castLotsOfShit.parameters.put(EventType.Parameter.CAUSE, This.instance());
		castLotsOfShit.parameters.put(EventType.Parameter.PLAYER, You.instance());
		castLotsOfShit.parameters.put(EventType.Parameter.OBJECT, castable);
		castLotsOfShit.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		this.addEffect(castLotsOfShit);
	}
}

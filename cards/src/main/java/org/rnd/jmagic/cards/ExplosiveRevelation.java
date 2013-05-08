package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Explosive Revelation")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ExplosiveRevelation extends Card
{
	public ExplosiveRevelation(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		// Choose target creature or player. Reveal cards from the top of your
		// library until you reveal a nonland card.
		SetGenerator library = LibraryOf.instance(You.instance());
		SetGenerator nonlands = RelativeComplement.instance(InZone.instance(library), HasType.instance(Type.LAND));
		SetGenerator toReveal = TopMost.instance(library, numberGenerator(1), nonlands);

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Choose target creature or player. Reveal cards from the top of your library until you reveal a nonland card.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, toReveal);
		this.addEffect(reveal);

		// Explosive Revelation deals damage equal to that card's converted mana
		// cost to that creature or player.
		SetGenerator firstNonland = Intersect.instance(toReveal, nonlands);
		SetGenerator amount = ConvertedManaCostOf.instance(firstNonland);
		this.addEffect(spellDealDamage(amount, target, "Explosive Revelation deals damage equal to that card's converted mana cost to that creature or player."));

		// Put the nonland card into your hand and the rest on the bottom of
		// your library in any order.
		EventFactory toHand = new EventFactory(EventType.MOVE_OBJECTS, "Put the nonland card into your hand");
		toHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		toHand.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		toHand.parameters.put(EventType.Parameter.OBJECT, firstNonland);

		EventFactory toBottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "and the rest on the bottom of your library in any order.");
		toBottom.parameters.put(EventType.Parameter.CAUSE, This.instance());
		toBottom.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		toBottom.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(toReveal, firstNonland));

		this.addEffect(simultaneous(toHand, toBottom));
	}
}

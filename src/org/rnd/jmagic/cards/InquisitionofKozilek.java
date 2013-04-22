package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inquisition of Kozilek")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class InquisitionofKozilek extends Card
{
	public InquisitionofKozilek(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator cards = InZone.instance(HandOf.instance(targetedBy(target)));
		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target player reveals his or her hand.");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, cards);
		this.addEffect(reveal);

		SetGenerator cmcThreeOrLess = HasConvertedManaCost.instance(Between.instance(0, 3));
		SetGenerator choices = RelativeComplement.instance(cmcThreeOrLess, HasType.instance(Type.LAND));

		EventFactory discard = new EventFactory(EventType.DISCARD_FORCE, "You choose a nonland card from it with converted mana cost 3 or less. That player discards that card.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		discard.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		discard.parameters.put(EventType.Parameter.CHOICE, Identity.instance(choices));
		this.addEffect(discard);

	}
}

package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Monomania")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Monomania extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Monomania", "Choose a card in your hand.", false);

	public Monomania(GameState state)
	{
		super(state);

		// Target player chooses a card in his or her hand
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator inHand = InZone.instance(HandOf.instance(target));

		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Target player chooses a card in his or her hand");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.CHOICE, inHand);
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));
		this.addEffect(choose);

		// and discards the rest.
		SetGenerator theRest = RelativeComplement.instance(inHand, EffectResult.instance(choose));

		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "and discards the rest.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, theRest);
		this.addEffect(discard);
	}
}

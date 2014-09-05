package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cabal Therapy")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Judgment.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CabalTherapy extends Card
{
	public CabalTherapy(GameState state)
	{
		super(state);

		// Name a nonland card.
		EventFactory nameACard = new EventFactory(EventType.PLAYER_CHOOSE, "Name a nonland card.");
		nameACard.parameters.put(EventType.Parameter.PLAYER, You.instance());
		nameACard.parameters.put(EventType.Parameter.CHOICE, NonLandCardNames.instance());
		nameACard.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_NONLAND_CARD));
		this.addEffect(nameACard);

		// Target player reveals his or her hand and discards all cards with
		// that name.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator inHand = InZone.instance(HandOf.instance(target));

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Target player reveals his or her hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, inHand);
		this.addEffect(reveal);

		SetGenerator withName = Intersect.instance(inHand, HasName.instance(EffectResult.instance(nameACard)));

		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "and discards all cards with that name.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, withName);
		this.addEffect(discard);

		// Flashback\u2014Sacrifice a creature.
		CostCollection fbCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Flashback.COST_TYPE, sacrificeACreature("Sacrifice a creature."));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, fbCost));
	}
}
